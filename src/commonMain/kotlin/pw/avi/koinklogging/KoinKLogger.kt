package pw.avi.koinklogging

import mu.KLogger
import mu.KotlinLogging
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE

private object KoinKLogger : Logger() {

    private val logger = KotlinLogging.logger("koin")

    override fun log(level: Level, msg: MESSAGE) = when (level) {
        Level.DEBUG -> logger.debug { msg }
        Level.INFO -> logger.info { msg }
        Level.ERROR -> logger.error { msg }
        Level.NONE -> logger.debug { msg }
    }

}

/**
 * Sets up kotlin-logging [KLogger] as koin [Logger]
 *
 * Usage example:
 * ```
 * startKoin {
 *     koinKLogger()
 *     ...
 * }
 * ```
 *
 * See [koinKLoggingModule] for setup of kotlin-logging for [KoinComponent]s
 */
fun KoinApplication.koinKLogger() = logger(KoinKLogger)