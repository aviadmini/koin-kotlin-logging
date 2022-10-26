package pw.avi.koinklogging

import mu.KLogger
import org.koin.core.component.KoinComponent

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
actual fun KoinComponent.injectKLogger(mode: LazyThreadSafetyMode): Lazy<KLogger> = this.injectKLogger(this::class.loggerName, mode)

internal actual fun anyToLoggerSpecial(any: Any): KLogger? = null

