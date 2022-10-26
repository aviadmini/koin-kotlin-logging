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
    private val logger by injectKLogger(/* optional logger name string here */)
    // ...
}
 ```

- for KoinApplication or Koin instances

 ```kotlin
val logger by koinApp.injectKLogger {} // Log tag is obtained from declaration context
val logger by koinApp.injectKLogger("LOGGER_NAME")
val logger by koinApp.injectKLogger(clazz) // Log tag is obtained from supplied class
 ```

## License

```
Copyright 2022 aviadmini

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```