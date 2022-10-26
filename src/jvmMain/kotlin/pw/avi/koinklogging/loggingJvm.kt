package pw.avi.koinklogging

import mu.KLogger
import mu.KotlinLogging
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.mp.KoinPlatformTools

/**
 * Inject [KLogger] from Koin DI
 *
 * Usage example:
 * ```
 * class MyComponent : KoinComponent {
 *     private val logger by injectKLogger()
 *     ...
 * }
 * ```
 * @see [koinKLoggingModule]
 */
actual fun KoinComponent.injectKLogger(mode: LazyThreadSafetyMode): Lazy<KLogger> = this.injectKLogger(this.javaClass.loggerName, mode)

internal actual fun anyToLoggerSpecial(any: Any): KLogger? = if (any is Class<*>) KotlinLogging.logger(any.loggerName) else null

/**
 * Get logger name from java class
 */
private val <T> Class<T>.loggerName: String
    get() {
        val name = this.name
        val slicedName = when {
            name.contains("Kt$") -> name.substringBefore("Kt$")
            name.contains("$") -> name.substringBefore("$")
            else -> name
        }
        return slicedName
    }

/**
 * Inject [KLogger] from Koin DI. Logger name is obtained from [clazz] name
 *
 * Usage example:
 * ```
 * val logger by koinApp.injectKLogger(clazz) // where koinApp is your KoinApplication instance
 * ```
 * @see [koinKLoggingModule]
 */
fun KoinApplication.injectKLogger(clazz: Class<*>, mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode()): Lazy<KLogger> =
    koin.injectKLogger(mode, clazz as Any)

/**
 * Inject [KLogger] from Koin DI. Logger name is obtained from [clazz] name
 *
 * Usage example:
 * ```
 * val logger by koin.injectKLogger(clazz) // where koin is your Koin instance
 * ```
 * @see [koinKLoggingModule]
 */
fun Koin.injectKLogger(clazz: Class<*>, mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode()): Lazy<KLogger> =
    this.injectKLogger(mode, clazz as Any)
