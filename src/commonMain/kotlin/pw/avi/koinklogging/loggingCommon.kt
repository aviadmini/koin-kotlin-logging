package pw.avi.koinklogging

import mu.KLogger
import mu.KotlinLogging
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.mp.KoinPlatformTools
import kotlin.reflect.KClass

/**
 * Adds kotlin-logging [KLogger] factory to Koin DI
 *
 * Set up example:
 * ```
 * startKoin {
 *     ...
 *     modules(koinKLoggingModule, ...)
 * }
 * ```
 * Inject examples:
 * - for KoinComponent
 * ```
 * class MyComponent : KoinComponent {
 *     private val logger by injectKLogger()
 *     ...
 * }
 * ```
 * - for KoinApplication or Koin instances
 * ```
 * val logger by koinApp.injectKLogger {} // Logger name is obtained from declaration context
 * val logger by koinApp.injectKLogger("LOGGER_NAME")
 * val logger by koinApp.injectKLogger(clazz) // Logger name is obtained from supplied class
 * ```
 */
val koinKLoggingModule = module {
    factory { (any: Any) ->
        when (any) {
            is String -> KotlinLogging.logger(any)
            is KClass<*> -> KotlinLogging.logger(any.loggerName)
            else -> anyToLoggerSpecial(any) ?: try {
                @Suppress("UNCHECKED_CAST") KotlinLogging.logger(any as () -> Unit)
            } catch (e: Exception) {
                throw IllegalArgumentException("Cannot inherit logger name from '${any::class.simpleName ?: "[unknown]"}' parameter")
            }
        }
    }
}

/**
 * Handle platform-specific cases (e.g. jvm Class<*>)
 */
internal expect fun anyToLoggerSpecial(any: Any): KLogger?

/**
 * Get logger name from class
 */
internal val <T : Any> KClass<T>.loggerName: String
    get() = this.simpleName ?: throw Exception("Supplied class doesn't have a name and cannot be used to get the logger name")

/**
 * Inject [KLogger] from Koin DI into a KoinComponent with logger name inherited from component class name
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
expect fun KoinComponent.injectKLogger(mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode()): Lazy<KLogger>

/**
 * Inject [KLogger] from Koin DI into a KoinComponent with a custom logger name
 *
 * Usage example:
 * ```
 * class MyComponent : KoinComponent {
 *     private val logger by injectKLogger("LOGGER_NAME")
 *     ...
 * }
 * ```
 * @see [koinKLoggingModule]
 */
fun KoinComponent.injectKLogger(loggerName: String, mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode()): Lazy<KLogger> =
    this.inject(null, mode) { parametersOf(loggerName) }

/**
 * Inject [KLogger] from Koin DI. Logger name is obtained from [lambda] declaration context
 *
 * Usage example:
 * ```
 * val logger by koinApp.injectKLogger {} // where koinApp is your KoinApplication instance
 * ```
 * @see [koinKLoggingModule]
 */
fun KoinApplication.injectKLogger(mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode(), lambda: () -> Unit): Lazy<KLogger> =
    koin.injectKLogger(mode, lambda as Any)

/**
 * Inject [KLogger] from Koin DI
 *
 * Usage example:
 * ```
 * val logger by koinApp.injectKLogger("LOGGER_NAME") // where koinApp is your KoinApplication instance
 * ```
 * @see [koinKLoggingModule]
 */
fun KoinApplication.injectKLogger(loggerName: String, mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode()): Lazy<KLogger> =
    koin.injectKLogger(mode, loggerName as Any)

/**
 * Inject [KLogger] from Koin DI. Logger name is obtained from [clazz] name
 *
 * Usage example:
 * ```
 * val logger by koinApp.injectKLogger(clazz) // where koinApp is your KoinApplication instance
 * ```
 * @see [koinKLoggingModule]
 */
fun KoinApplication.injectKLogger(clazz: KClass<*>, mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode()): Lazy<KLogger> =
    koin.injectKLogger(mode, clazz as Any)

/**
 * Inject [KLogger] from Koin DI. Logger name is obtained from [lambda] declaration context
 *
 * Usage example:
 * ```
 * val logger by koin.injectKLogger {} // where koin is your Koin instance
 * ```
 * @see [koinKLoggingModule]
 */
fun Koin.injectKLogger(mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode(), lambda: () -> Unit): Lazy<KLogger> =
    this.injectKLogger(mode, lambda as Any)

/**
 * Inject [KLogger] from Koin DI
 *
 * Usage example:
 * ```
 * val logger by koin.injectKLogger("LOGGER_NAME") // where koin is your Koin instance
 * ```
 * @see [koinKLoggingModule]
 */
fun Koin.injectKLogger(loggerName: String, mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode()): Lazy<KLogger> =
    this.injectKLogger(mode, loggerName as Any)

/**
 * Inject [KLogger] from Koin DI. Logger name is obtained from [clazz] name
 *
 * Usage example:
 * ```
 * val logger by koin.injectKLogger(clazz) // where koin is your Koin instance
 * ```
 * @see [koinKLoggingModule]
 */
fun Koin.injectKLogger(clazz: KClass<*>, mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode()): Lazy<KLogger> =
    this.injectKLogger(mode, clazz as Any)

//
internal fun Koin.injectKLogger(mode: LazyThreadSafetyMode, any: Any): Lazy<KLogger> = this.inject(mode = mode) { parametersOf(any) }