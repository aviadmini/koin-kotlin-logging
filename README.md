# kotlin-logging integration for koin DI

Integrate [kotlin-logging](https://github.com/MicroUtils/kotlin-logging) into [koin DI](https://github.com/InsertKoinIO/koin)

## KLogger as koin logger

```kotlin
startKoin {
    koinKLogger()
    // ...
}
```

## KLogger factory in koin DI

### Set up

```kotlin
startKoin {
    // ...
    modules(koinKLoggingModule, /* ... */)
}
```

### Inject

- for KoinComponent

 ```kotlin
class MyComponent : KoinComponent {
    private val logger by injectKLogger()
    // ...
}
 ```

- for KoinApplication or Koin instances

 ```kotlin
val logger by koinApp.injectKLogger {} // Log tag is obtained from declaration context
val logger by koinApp.injectKLogger("LOG_TAG")
val logger by koinApp.injectKLogger(clazz) // Log tag is obtained from supplied class
 ```