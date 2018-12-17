# Desafios IDwall - iddog

<br />This is only a sample project and can be modified every time it needs. 

# Implementation Details

MVVM pattern with Repository Pattern and inspired by the Clean Architecture.

- Model View ViewModel pattern (MVVM)
- Repository Pattern
- Reactive Programming with RxJava2
- Responsive UI
- Retrofit + GSON
- Material Design 2.0 pattern
- Glide for image loading
- Dagger 2 for Dependency injection 
- Mockito for Unit Testing
- Rotation Support
- Focus in performance

**Three main packages: Commons, Data and Presentation.**

- **Common:** Responsible for all Commons files in project.
- **Data:** Responsible for all  API requests, database access, connections, persistence things, mapped entities and Repository pattern.
- **Presentation - Ui (View, viewModel and Adapters):** Responsible views and extencions. It is also responsible for the viewModels and list adapters.

### Libraries

1. **RxJava2 + RxAndroid2:** This is the best way to work asynchronously and maintain the application scalable.
2. **Retrofit2 + OkHttp:** For Network Request and Rx integration.
3. **Kotlin Extensions:** For view binding.
4. **Glide:** For image loading.
5. **Gson:** Retrofit integration for deserialize.
6. **Dagger2:** For dependency injection.

### Installing
Just have the project and clean and build in your Android Studio
