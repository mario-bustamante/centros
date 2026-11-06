# Centros App + Laravel

App Android (Kotlin, Jetpack Compose, Hilt, Retrofit) que consume la API Laravel (`/api/...`).
`Config.BASE_URL` ya incluye el prefijo `/api/`, por lo que los paths de Retrofit se escriben sin él.

## Arquitectura

Ruta en Laravel -> Service -> (DataSource) -> Repository -> UseCase -> ViewModel -> Screen -> Navegación.

```
app/src/main/java/com/altoservicios/centros/
├── core/Config.kt                      URLs base
├── data/
│   ├── dataSource/remote/service/      Interfaces Retrofit (*Service)
│   ├── dataSource/{remote,local}/      DataSources (solo donde se usan, p. ej. Auth)
│   ├── datastore/                      Sesión (AuthDataStore)
│   ├── network/                        AuthInterceptor, TokenAuthenticator
│   └── repository/                     *RepositoryImpl
├── domain/
│   ├── model/                          Modelos (Gson @SerializedName)
│   ├── repository/                     Interfaces de repositorio
│   ├── useCase/<modulo>/               Casos de uso agrupados (p. ej. UserUseCase)
│   └── util/                           Resource, ResponseToRequest, map
├── di/                                 Módulos Hilt
└── presentation/
    ├── screens/<modulo>/               Screen + ViewModel
    └── navigation/{graph,screen}/      Grafos y rutas
```

## Cómo implementar un flujo completo

Ejemplo: `PUT /api/user` (actualizar perfil). Para un endpoint nuevo se siguen los mismos pasos.

### 0. Endpoint en Laravel

`routes/api.php`. Las rutas protegidas van dentro de `Route::group(['middleware' => ['jwt.verify']], ...)`.

```php
Route::put('user', [UserController::class, 'updateProfile']);
```

Anota método HTTP, path, parámetros (query, form o JSON) y forma de la respuesta: de eso dependen el Service y el modelo.

### 1. Modelo (`domain/model`)

Mapea los campos JSON con `@SerializedName`. Los campos opcionales deben ser nulos con valor por defecto.

```kotlin
data class User(
    @SerializedName("id") val id: String? = null,
    @SerializedName("username") val name: String,
    @SerializedName("phone") val phone: String? = null,
)
```

### 2. Service Retrofit (`data/dataSource/remote/service`)

Un método `suspend` por endpoint que devuelve `Response<T>`. Path sin `/api/` ni `/` inicial.

```kotlin
interface UserService {
    @GET("user")
    suspend fun getUser(): Response<UserResponse>

    @PUT("user")
    suspend fun updateUser(
        @Query("username") userName: String?,
        @Query("phone") phone: String?
    ): Response<UserResponse>
}
```

| Tipo de dato en Laravel | Anotación Retrofit |
|---|---|
| Query string | `@Query` |
| Form (`application/x-www-form-urlencoded`) | `@FormUrlEncoded` + `@Field` |
| JSON | `@Body` |
| Archivos | `@Multipart` + `@Part` |
| Parámetro en la URL (`{id}`) | `@Path` |

El token JWT se añade solo: `AuthInterceptor` envía `Authorization: Bearer <token>` y `TokenAuthenticator` renueva el token con `POST auth/refresh` ante un 401 y reintenta una vez.

### 3. Registrar el Service en Hilt (`di/NetworkModule.kt`)

Solo si el Service es nuevo.

```kotlin
@Provides
@Singleton
fun provideUserService(retrofit: Retrofit): UserService = retrofit.create(UserService::class.java)
```

### 4. Interfaz de repositorio (`domain/repository`)

Devuelve `Resource<T>` (`Loading`, `Success(data)`, `Failure(message)`), nunca `Response`.

```kotlin
interface UserRepository {
    suspend fun getUser(): Resource<User>
    suspend fun updateUser(user: User): Resource<User>
}
```

### 5. Implementación (`data/repository`)

`ResponseToRequest.call { ... }` ejecuta la llamada, convierte errores HTTP y de red en `Resource.Failure`, y `.map { }` adapta el tipo.

```kotlin
class UserRepositoryImpl(private val service: UserService) : UserRepository {

    override suspend fun updateUser(user: User): Resource<User> =
        ResponseToRequest.call { service.updateUser(user.name, user.phone) }
            .map { it.data ?: user }
}
```

Regístrala en `di/RepositoryModule.kt`:

```kotlin
@Provides
fun provideUserRepository(service: UserService): UserRepository = UserRepositoryImpl(service)
```

### 6. Casos de uso (`domain/useCase/<modulo>`)

Un caso de uso por acción, agrupados en un `data class` por módulo.

```kotlin
class UpdateUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(user: User) = repository.updateUser(user)
}

data class UserUseCase(
    val getUser: GetUserUseCase,
    val updateUser: UpdateUserUseCase,
)
```

Regístralos en `di/UseCaseModule.kt`:

```kotlin
@Provides
fun provideUserUseCase(repository: UserRepository) = UserUseCase(
    getUser = GetUserUseCase(repository),
    updateUser = UpdateUserUseCase(repository)
)
```

### 7. ViewModel (`presentation/screens/<modulo>`)

`@HiltViewModel`, estado con `mutableStateOf` y llamadas dentro de `viewModelScope.launch`. Se maneja cada rama de `Resource`.

```kotlin
@HiltViewModel
class ProfileUpdateViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set
    var message by mutableStateOf("")
    var finished by mutableStateOf(false)
        private set

    fun save(user: User) {
        isLoading = true
        viewModelScope.launch {
            when (val result = userUseCase.updateUser(user)) {
                is Resource.Success -> finished = true
                is Resource.Failure -> message = result.message
                Resource.Loading -> Unit
            }
            isLoading = false
        }
    }
}
```

### 8. Pantalla Compose y navegación

La pantalla obtiene el ViewModel con `hiltViewModel()` y reacciona al estado.

```kotlin
@Composable
fun ProfileUpdateScreen(navController: NavHostController, vm: ProfileUpdateViewModel = hiltViewModel()) {
    MessageEffect(message = vm.message, onConsumed = { vm.message = "" })

    LaunchedEffect(vm.finished) {
        if (vm.finished) navController.popBackStack()
    }
    // Scaffold, formulario, indicador de carga con vm.isLoading...
}
```

Define la ruta en `presentation/navigation/screen/ProfileScreen.kt`:

```kotlin
const val PROFILE_UPDATE = "profile/update"
```

Añádela al grafo (`presentation/navigation/graph/ProfileNavGraph.kt`) y navega con `navController.navigate(ProfileScreen.PROFILE_UPDATE)`:

```kotlin
composable(route = ProfileScreen.PROFILE_UPDATE) {
    ProfileUpdateScreen(navController)
}
```

## Checklist

- [ ] Ruta Laravel creada y probada
- [ ] Modelo con `@SerializedName`
- [ ] Método en el `Service` (path sin `/api/`)
- [ ] Service provisto en `NetworkModule`
- [ ] Método en la interfaz y la implementación del repositorio
- [ ] Repositorio provisto en `RepositoryModule`
- [ ] Caso de uso creado y registrado en `UseCaseModule`
- [ ] ViewModel con manejo de `Success` / `Failure` / `Loading`
- [ ] Pantalla y ruta en el `NavGraph`

## Autenticación y sesión

| Endpoint | Método | Uso |
|---|---|---|
| `auth/login` | POST | Devuelve `token`, `token_type`, `expires_in` |
| `auth/register` | POST | Crea la cuenta (responde 202, requiere confirmar el correo) |
| `auth/login/google` | POST | Login con `id_token` de Google |
| `auth/refresh` | POST | Renueva el token (usa el token actual en `Authorization`) |

- La sesión (`AuthResponse`) se guarda en DataStore (`AuthDataStore`). `AuthResponse.token` acepta las claves `access_token` y `token`.
- `refresh` solo devuelve el token: `AuthResponse.withRefreshedToken` lo fusiona con el usuario ya guardado.
- `TokenAuthenticator` no reintenta rutas `/auth/`; si el refresh responde 401 se borra la sesión y el usuario debe iniciar sesión de nuevo.
### Login con Google (`auth/login/google`)

Flujo: botón "Continuar con Google" (`LoginContent`) -> `GoogleSignIn` obtiene el `idToken` -> `LoginViewModel.loginWithGoogle` -> `AuthUseCase.loginGoogle` -> `AuthRepository.loginGoogle` -> `AuthService.loginGoogle` (`POST auth/login/google`, campo `id_token`). La respuesta usa el mismo `Resource<AuthResponse>` que el login normal, así que `Login` guarda la sesión y navega igual.

Configuración necesaria:

1. En Google Cloud crea un ID de cliente OAuth tipo **Web** y otro tipo **Android** (paquete `com.altoservicios.centros` + SHA-1 del keystore).
2. Pon el ID **Web** en `app/src/main/res/values/google_auth.xml` (`google_web_client_id`).
3. Pon ese mismo ID en Laravel como `services.google.client_id`; el servidor lo compara con el `aud` del token.
