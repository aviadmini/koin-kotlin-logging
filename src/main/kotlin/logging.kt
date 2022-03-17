import mu.KLogger
import mu.KotlinLogging
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.mp.KoinPlatformTools

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
 * val logger by koinApp.injectKLogger {}
 * val logger by koinApp.injectKLogger("LOG_TAG")
 * val logger by koinApp.injectKLogger(clazz)
 * ```
 */
val koinKLoggingModule = module {
    factory { (any: Any) ->
        when (any) {
            is String -> KotlinLogging.logger(any)
            is Class<*> -> KotlinLogging.logger(any.loggerName)
            else -> {
                try {
                    @Suppress("UNCHECKED_CAST") KotlinLogging.logger(any as () -> Unit)
                } catch (e: Exception) {
                    throw IllegalArgumentException("Cannot inherit log tag from ${any.javaClass.name} parameter")
                }
            }
        }
    }
}

/**
 * Get logger name from class
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
fun KoinComponent.injectKLogger(mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode()): Lazy<KLogger> =
    this.inject(null, mode) { parametersOf(this.javaClass.loggerName) }

/**
 * Inject [KLogger] from Koin DI. Log tag is obtained from [lambda] declaration context
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
 * val logger by koinApp.injectKLogger("LOG_TAG") // where koinApp is your KoinApplication instance
 * ```
 * @see [koinKLoggingModule]
 */
fun KoinApplication.injectKLogger(tag: String, mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode()): Lazy<KLogger> =
    koin.injectKLogger(mode, tag as Any)

/**
 * Inject [KLogger] from Koin DI. Log tag is obtained from [clazz] name
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
 * Inject [KLogger] from Koin DI. Log tag is obtained from [lambda] declaration context
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
 * val logger by koin.injectKLogger("LOG_TAG") // where koin is your Koin instance
 * ```
 * @see [koinKLoggingModule]
 */
fun Koin.injectKLogger(tag: String, mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode()): Lazy<KLogger> =
    this.injectKLogger(mode, tag as Any)

/**
 * Inject [KLogger] from Koin DI. Log tag is obtained from [clazz] name
 *
 * Usage example:
 * ```
 * val logger by koin.injectKLogger(clazz) // where koin is your Koin instance
 * ```
 * @see [koinKLoggingModule]
 */
fun Koin.injectKLogger(clazz: Class<*>, mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode()): Lazy<KLogger> =
    this.injectKLogger(mode, clazz as Any)

//
private fun Koin.injectKLogger(mode: LazyThreadSafetyMode, any: Any): Lazy<KLogger> =
    this.inject(mode = mode) { parametersOf(any) }