import mu.KotlinLogging
import org.koin.core.KoinApplication
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE

private object KoinKLogger : Logger() {

    private val logger = KotlinLogging.logger("koin")

    override fun log(level: Level, msg: MESSAGE) = when (level) {
        Level.DEBUG -> logger.debug(msg)
        Level.INFO -> logger.info(msg)
        Level.ERROR -> logger.error(msg)
        Level.NONE -> logger.debug(msg)
    }

}

/**
 * Sets up Kotlin-logging [mu.KLogger] as Koin [Logger]
 *
 * Usage example:
 * ```
 * startKoin {
 *     koinKLogger()
 *     ...
 * }
 * ```
 */
fun KoinApplication.koinKLogger() = logger(KoinKLogger)