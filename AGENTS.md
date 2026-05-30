# Cash Tracker ΓÇö Agent Guide

## Project Overview

Cash Tracker is an Android expense-tracking app built with **Jetpack Compose + Material3**. It helps users track income/expenses, analyze spending habits, set budgets, and manage categories.

| Key | Value |
|---|---|
| Package | `com.naji.cashtracker` |
| Min SDK | 26 |
| Target SDK | 36 |
| Spec sources | `specs/cash-tracker.md`, `specs/Cash Tracker.html` |

---

## Tech Stack

| Layer | Choice |
|---|---|
| UI | Jetpack Compose + Material3 |
| Architecture | MVVM + Clean Architecture + Repository Pattern |
| DI | **Koin** |
| Database | Room |
| State | StateFlow / Flow |
| Charts | **Vico** |
| Navigation | Compose Navigation (type-safe, KotlinX Serialization) |
| Networking | Retrofit (optional, later) |
| Font | **Inter** (variable font at `res/font/inter_variable.ttf`, sourced from `specs/Fonts/`) |
| Image loading | Coil |

---

## Gradle / Dependencies

### Version Catalog (`gradle/libs.versions.toml`)

| Key | Version | For |
|---|---|---|
| `agp` | `8.13.2` | Android Gradle Plugin |
| `kotlin` | `2.0.21` | Kotlin + Compose + Serialization plugins |
| `composeBom` | `2024.09.00` | Compose Bill of Materials |
| `koin` | `4.0.2` | DI |
| `room` | `2.7.0` | Database |
| `navigationCompose` | `2.8.7` | Navigation |
| `kotlinxSerializationJson` | `1.7.3` | Route serialization |
| `vico` | `2.0.0-beta.2` | Charts |
| `coil` | `2.7.0` | Image loading |
| `ksp` | `2.0.21-1.0.28` | Room annotation processor |

### Libraries grouped into bundles

Every library is in a named bundle (even single-entry) so the project's exact tech stack is always visible:

| Bundle | Libraries |
|---|---|
| `core-ktx` | `androidx.core:core-ktx` |
| `lifecycle` | `lifecycle-runtime-ktx`, `lifecycle-viewmodel-compose`, `lifecycle-runtime-compose` |
| `activity-compose` | `activity-compose` |
| `compose-core` | `ui`, `ui-graphics`, `material3` |
| `compose-icons` | `material-icons-extended` |
| `compose-tooling-preview` | `ui-tooling-preview` |
| `compose-tooling-debug` | `ui-tooling` |
| `compose-test-manifest-debug` | `ui-test-manifest` |
| `compose-test` | `ui-test-junit4` |
| `koin` | `koin-android`, `koin-androidx-compose` |
| `room` | `room-runtime`, `room-ktx` |
| `room-compiler` | `room-compiler` |
| `navigation` | `navigation-compose` |
| `serialization` | `kotlinx-serialization-json` |
| `vico` | `compose-m3` |
| `coil` | `coil-compose` |
| `testing-unit` | `junit` |
| `testing-android` | `androidx-junit`, `espresso-core` |

### Plugins

| ID | Declared in |
|---|---|
| `com.android.application` | root `build.gradle.kts` + `:app` |
| `org.jetbrains.kotlin.android` | root `build.gradle.kts` + `:app` |
| `org.jetbrains.kotlin.plugin.compose` | root `build.gradle.kts` + `:app` |
| `org.jetbrains.kotlin.plugin.serialization` | root `build.gradle.kts` + `:app` |
| `com.google.devtools.ksp` | root `build.gradle.kts` + `:app` |

### Root `build.gradle.kts`

```kotlin
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
}
```

### `:app` `build.gradle.kts`

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.core.ktx)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.activity.compose)
    implementation(libs.bundles.compose.core)
    implementation(libs.bundles.compose.icons)
    implementation(libs.bundles.compose.tooling.preview)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.room)
    implementation(libs.bundles.navigation)
    implementation(libs.bundles.serialization)
    implementation(libs.bundles.vico)
    implementation(libs.bundles.coil)
    debugImplementation(libs.bundles.compose.tooling.debug)
    debugImplementation(libs.bundles.compose.test.manifest.debug)
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.compose.test)
    ksp(libs.bundles.room.compiler)
}
```

---

## Architecture

Single-module project with **feature-based packages** under `app/src/main/java/com/naji/cashtracker/`. Each feature has up to 3 layers (`data/`, `domain/`, `presentation/`), nested inside its feature directory.

### Package layout (on disk)

```
com.naji.cashtracker/
├── CashTrackerApp.kt              ← Application class (Koin startKoin)
├── MainActivity.kt                ← Single-activity host
├── core/
│   ├── domain/                    ← Shared models, Result<T,E>, Error types
│   ├── data/                      ← Shared DB, HttpClient, safe call helpers
│   ├── presentation/              ← UiText, ObserveAsEvents, shared composables
│   └── design-system/             ← Reusable components (NOT theme)
├── ui/
│   └── theme/                     ← Color.kt, Type.kt, Shape.kt, Theme.kt
└── feature/
    ├── splash/
    │   └── presentation/          ← SplashScreen.kt, SplashViewModel.kt
    ├── onboarding/
    │   └── presentation/          ← OnboardingScreen.kt, OnboardingViewModel.kt
    ├── auth/
    │   ├── data/                  ← AuthRepository, AuthDataSource
    │   ├── domain/                ← User, AuthError
    │   └── presentation/
    │       └── register/          ← RegisterScreen.kt, RegisterViewModel.kt
    ├── dashboard/
    │   ├── data/                  ← TransactionDao, TransactionEntity
    │   ├── domain/                ← Transaction, TransactionRepository
    │   └── presentation/
    │       └── home/              ← HomeScreen.kt, HomeViewModel.kt
    ├── transaction/
    │   ├── data/                  ← TransactionDto, TransactionEntity
    │   ├── domain/                ← Transaction, TransactionRepository
    │   └── presentation/
    │       ├── list/              ← TransactionListScreen.kt, TransactionListViewModel.kt
    │       └── addedit/           ← TransactionAddEditScreen.kt, TransactionAddEditViewModel.kt
    ├── analytics/
    │   ├── data/                  ← AnalyticsRepository
    │   ├── domain/                ← AnalyticsSummary, ChartData
    │   └── presentation/          ← AnalyticsScreen.kt, AnalyticsViewModel.kt
    ├── category/
    │   ├── data/                  ← CategoryDao, CategoryEntity
    │   ├── domain/                ← Category, CategoryRepository
    │   └── presentation/
    │       ├── list/              ← CategoryListScreen.kt, CategoryListViewModel.kt
    │       └── addedit/           ← CategoryAddEditScreen.kt, CategoryAddEditViewModel.kt
    ├── budget/
    │   ├── data/                  ← BudgetDao, BudgetEntity
    │   ├── domain/                ← Budget, BudgetRepository
    │   └── presentation/
    │       ├── list/              ← BudgetListScreen.kt, BudgetListViewModel.kt
    │       └── addedit/           ← BudgetAddEditScreen.kt, BudgetAddEditViewModel.kt
    ├── settings/
    │   └── presentation/          ← SettingsScreen.kt, SettingsViewModel.kt
    └── profile/
        ├── data/                  ← ProfileRepository
        ├── domain/                ← UserProfile
        └── presentation/
            ├── view/              ← ProfileScreen.kt, ProfileViewModel.kt
            └── edit/              ← ProfileEditScreen.kt, ProfileEditViewModel.kt
```

### Dependency rules

| Layer | May depend on |
|---|---|
| `presentation` | its own `domain`, `core:domain`, `core:presentation`, `core:design-system` |
| `data` | its own `domain`, `core:domain`, `core:data` |
| `domain` | `core:domain` only — never `data` or `presentation` |

### File naming rules

- One class/interface per file (except sealed hierarchy siblings).
- File name matches the primary type name.
- Mappers live in the `data/` layer alongside their DTO/Entity files.
- Koin modules live in the layer they wire (`presentation/` for view models, `data/` for data sources).

---

## DI ΓÇö Koin

Follow the **android-di-koin** skill for all DI setup.

### Module definitions

```kotlin
// feature:dashboard:data
val dashboardDataModule = module {
    singleOf(::RoomTransactionDataSource) { bind<TransactionLocalDataSource>() }
}

// feature:dashboard:domain
val dashboardDomainModule = module {
    singleOf(::TransactionRepository)
}

// feature:dashboard:presentation
val dashboardPresentationModule = module {
    viewModelOf(::HomeViewModel)
}

// feature:budget:data
val budgetDataModule = module {
    singleOf(::RoomBudgetDataSource) { bind<BudgetLocalDataSource>() }
}

// feature:budget:domain
val budgetDomainModule = module {
    singleOf(::BudgetRepository)
}

// feature:budget:presentation
val budgetPresentationModule = module {
    viewModelOf(::BudgetListViewModel)
    viewModelOf(::BudgetAddEditViewModel)
}
```

Prefer `singleOf`/`viewModelOf` constructor references. Use lambda `single { }` / `viewModel { }` only when constructor injection isn't enough.

### Assembly in `:app`

```kotlin
class CashTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CashTrackerApp)
            modules(
                // core
                coreDataModule,
                coreDomainModule,
                corePresentationModule,
                // features
                dashboardDataModule,
                dashboardDomainModule,
                dashboardPresentationModule,
                // ... all feature modules
            )
        }
    }
}
```

### Injection in composables

```kotlin
@Composable
fun HomeRoot(
    onNavigateToAddTransaction: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreen(state = state, onAction = viewModel::onAction)
}
```

---

## Presentation ΓÇö MVI

Follow the **android-presentation-mvi** skill for every screen.

### Per-screen structure

Each screen produces exactly 4 types:

```kotlin
// 1. State
data class HomeState(
    val balance: String = "$0.00",
    val recentTransactions: List<TransactionUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null
)

// 2. Action
sealed interface HomeAction {
    data object OnRefresh : HomeAction
    data object OnAddTransaction : HomeAction
    data class OnTransactionClick(val id: String) : HomeAction
}

// 3. Event
sealed interface HomeEvent {
    data class NavigateToAddTransaction(val type: TransactionType) : HomeEvent
    data class ShowSnackbar(val message: UiText) : HomeEvent
}

// 4. ViewModel
class HomeViewModel(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _events = Channel<HomeEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: HomeAction) { ... }
}
```

### Composable structure

Every screen has a **Root** (injects ViewModel, observes events) and a **Screen** (pure state + `onAction`, previewable) ΓÇö both in the same file:

```kotlin
@Composable
fun HomeRoot(
    onNavigateToAddTransaction: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvents(viewModel.events) { event -> ... }
    HomeScreen(state = state, onAction = viewModel::onAction)
}

@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) { ... }

@Preview
@Composable
private fun HomeScreenPreview() {
    CashTrackerTheme {
        HomeScreen(state = HomeState(), onAction = {})
    }
}
```

### UiText

```kotlin
sealed interface UiText {
    data class DynamicString(val value: String) : UiText
    class StringResource(val id: Int, val args: Array<Any> = emptyArray()) : UiText
}
```

Map errors to `UiText` via `.toUiText()` extension functions in the feature's `presentation` layer (or `core:presentation` for shared errors).

### Process death

Use `SavedStateHandle` for form fields that must survive process death:

```kotlin
class AddTransactionViewModel(
    savedStateHandle: SavedStateHandle,
    ...
) : ViewModel() {
    private val _state = MutableStateFlow(
        AddTransactionState(
            amount = savedStateHandle["amount"] ?: "",
            note = savedStateHandle["note"] ?: ""
        )
    )

    fun onAction(action: AddTransactionAction) {
        when (action) {
            is AddTransactionAction.OnAmountChange -> {
                savedStateHandle["amount"] = action.amount
                _state.update { it.copy(amount = action.amount) }
            }
        }
    }
}
```

---

## Navigation

Follow the **android-navigation** skill for all navigation setup.

### Route objects

```kotlin
@Serializable object SplashRoute
@Serializable object OnboardingRoute
@Serializable object RegisterRoute
@Serializable object HomeRoute
@Serializable object AddTransactionRoute
@Serializable data class EditTransactionRoute(val id: String)
@Serializable object TransactionsListRoute
@Serializable object AnalyticsRoute
@Serializable object CategoriesRoute
@Serializable object AddCategoryRoute
@Serializable object BudgetsRoute
@Serializable object AddBudgetRoute
@Serializable object SettingsRoute
@Serializable object ProfileRoute
@Serializable object EditProfileRoute
```

### Navigation flow

```
APP LAUNCH
  Γåô
SPLASH
  Γåô
  Γö£ΓöÇΓöÇ First Launch ΓöÇΓöÇΓû║ ONBOARDING 1/3 ΓåÆ 2/3 ΓåÆ 3/3
  Γöé                      Γåô
  Γöé                   REGISTER ΓåÆ HOME DASHBOARD
  Γöé
  ΓööΓöÇΓöÇ Returning User ΓöÇΓöÇΓû║ HOME DASHBOARD
                            Γåô
              ΓöîΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓö╝ΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÉ
          HOME DASHBOARD  TX LIST  ANALYTICS  SETTINGS
              ΓööΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓö╝ΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÇΓöÿ
              (4 bottom tabs ┬╖ freely switchable)

  From Home:            From Settings:
    ΓåÆ ADD TX Γå⌐ HOME       ΓåÆ PROFILE / ABOUT
    ΓåÆ CATEGORIES Γå⌐ HOME      ΓåÆ EDIT PROFILE Γå⌐ PROFILE
    ΓåÆ BUDGETS Γå⌐ HOME
    ΓåÆ ADD BUDGET Γå⌐ BUDGETS
```

### Feature nav graph

```kotlin
// feature:dashboard:presentation
fun NavGraphBuilder.dashboardGraph(
    navController: NavController,
    onNavigateToAddTransaction: () -> Unit,
    onNavigateToCategories: () -> Unit,
    onNavigateToBudgets: () -> Unit
) {
    navigation<HomeRoute>(startDestination = HomeRoute) {
        composable<HomeRoute> {
            HomeRoot(
                onNavigateToAddTransaction = onNavigateToAddTransaction,
                onNavigateToCategories = onNavigateToCategories,
                onNavigateToBudgets = onNavigateToBudgets
            )
        }
    }
}

// feature:transaction:presentation
fun NavGraphBuilder.transactionGraph(
    navController: NavController,
    onNavigateToAddEdit: (String?) -> Unit
) {
    navigation<TransactionsListRoute>(startDestination = TransactionsListRoute) {
        composable<TransactionsListRoute> {
            TransactionListRoot(onNavigateToAddEdit = onNavigateToAddEdit)
        }
    }
}

// feature:budget:presentation
fun NavGraphBuilder.budgetGraph(
    navController: NavController,
    onNavigateToAddBudget: () -> Unit
) {
    navigation<BudgetsRoute>(startDestination = BudgetsRoute) {
        composable<BudgetsRoute> {
            BudgetsRoot(onNavigateToAddBudget = onNavigateToAddBudget)
        }
    }
}
```

### Wiring in `:app`

```kotlin
NavHost(navController, startDestination = SplashRoute) {
    splashGraph(navController)
    onboardingGraph(navController, onComplete = { navController.navigate(HomeRoute) })
    registerGraph(navController, onComplete = { navController.navigate(HomeRoute) })
    dashboardGraph(navController, ...)
    transactionGraph(navController, onNavigateToAddEdit = { navController.navigate(AddTransactionRoute) })
    analyticsGraph(navController)
    categoryGraph(navController)
    budgetGraph(navController, onNavigateToAddBudget = { navController.navigate(AddBudgetRoute) })
    settingsGraph(navController, onNavigateToProfile = { navController.navigate(ProfileRoute) })
    profileGraph(navController)
    editProfileGraph(navController)
}
```

---

## Design System

### Colors (from spec)

All values map to Material3 color roles.

**Light mode:**

| Role | Hex | Role | Hex |
|---|---|---|---|
| Primary | `#4F46E5` | On Primary | `#FFFFFF` |
| Primary Container | `#C7D2FE` | On Primary Container | `#1E1B4B` |
| Secondary | `#10B981` | On Secondary | `#FFFFFF` |
| Secondary Container | `#D1FAE5` | On Secondary Container | `#064E3B` |
| Tertiary | `#F59E0B` | On Tertiary | `#1A1A1A` |
| Tertiary Container | `#FEF3C7` | On Tertiary Container | `#451A03` |
| Error | `#EF4444` | On Error | `#FFFFFF` |
| Error Container | `#FEE2E2` | On Error Container | `#7F1D1D` |
| Background | `#F8FAFC` | On Background | `#0F172A` |
| Surface | `#FFFFFF` | On Surface | `#0F172A` |
| Surface Variant | `#E2E8F0` | On Surface Variant | `#475569` |
| Outline | `#E2E8F0` | Outline Variant | `#CBD5E1` |

**Dark mode:**

| Role | Hex | Role | Hex |
|---|---|---|---|
| Primary | `#4F46E5` | On Primary | `#FFFFFF` |
| Primary Container | `#3730A3` | On Primary Container | `#C7D2FE` |
| Secondary | `#10B981` | On Secondary | `#FFFFFF` |
| Secondary Container | `#065F46` | On Secondary Container | `#D1FAE5` |
| Tertiary | `#F59E0B` | On Tertiary | `#1A1A1A` |
| Tertiary Container | `#92400E` | On Tertiary Container | `#FEF3C7` |
| Error | `#EF4444` | On Error | `#FFFFFF` |
| Error Container | `#7F1D1D` | On Error Container | `#FEE2E2` |
| Background | `#0F172A` | On Background | `#F8FAFC` |
| Surface | `#1E293B` | On Surface | `#F8FAFC` |
| Surface Variant | `#334155` | On Surface Variant | `#CBD5E1` |
| Outline | `#334155` | Outline Variant | `#475569` |

### Typography

Font: **Inter** (defined as `InterFont` in `Type.kt` using variable font with `FontVariation.Settings` for weights 400, 500, 600, 700)

Apply the Inter font family globally via `CashTrackerTypography` in `MaterialTheme`.

| M3 Style | Size | Weight | Usage |
|---|---|---|---|
| Display Small | 36sp | Bold (700) | Balance display |
| Headline Large | 32sp | Bold (700) | App title |
| Headline Small | 24sp | SemiBold (600) | Section headers |
| Title Large | 20sp | SemiBold (600) | Screen titles |
| Title Medium | 18sp | Medium (500) | Category details |
| Body Large | 16sp | Regular (400) | Body text |
| Body Medium | 14sp | Regular (400) | Body text |
| Body Small | 12sp | Regular (400) | Supporting text |
| Label Medium | 12sp | Medium (500) | Chart labels |
| Label Small | 11sp | Medium (500) | Small captions |

### Radiuses (defined in `Shape.kt`)

| Token | Value | Variable |
|---|---|---|
| Buttons | 16dp | `ButtonShape` |
| Cards | 20dp | `CardShape` |
| Inputs | 16dp | `InputShape` |
| Chips | 999dp | `ChipShape` |
| FAB | 16dp | `FabShape` |

### UI Components

| Component | Spec |
|---|---|
| Primary Button | 52dp h, 16dp radius, Primary bg, 16sp Bold white text |
| Secondary Button | 48dp h, 16dp radius, transparent + 2dp Primary border, 16sp SemiBold Primary text |
| Card | 20dp radius, 16dp padding, shadow `0 4dp 16dp rgba(15,23,42,0.06)` light / `rgba(0,0,0,0.2)` dark |
| Input Field | 56dp h, 16dp radius, 16dp horizontal padding, 1.5dp border, 15sp Regular |
| Chip | 999dp radius, 32dp h, 0ΓÇô14dp padding, 13sp Medium |
| FAB | 52├ù52dp, 16dp radius, Primary bg |
| Progress Bar | 10dp h, 999dp radius |
| Toggle (Income/Expense) | 999dp radius, 3dp padding, 13sp SemiBold labels |
| Toggle Switch | 42├ù24dp, 999dp radius, 20dp knob |
| Transaction Icon | 40├ù40dp, 12dp radius |
| Category Color (picker) | 34├ù34dp circles, 2.5dp border, 8 colors |
| Category Icon (picker) | 4-col grid, 44dp h, 12dp radius |

---

## Data Layer

Follow the **android-data-layer** skill for all data source / repository work.

### Naming conventions

| Thing | Convention | Example |
|---|---|---|
| Data source interface | `<Entity>LocalDataSource` / `<Entity>RemoteDataSource` | `TransactionLocalDataSource` |
| Data source impl | describe what makes it unique | `RoomTransactionDataSource` |
| Repository interface | `<Entity>Repository` (multi-source only) | `TransactionRepository` |
| Repository impl | describe what makes it unique | `OfflineFirstTransactionRepository` |
| DTO | `<Model>Dto` | `TransactionDto` |
| Room entity | `<Model>Entity` | `TransactionEntity` |
| Mapper | extension fun on source type | `fun TransactionDto.toTransaction()` |

### Safe call pattern (Ktor / Retrofit)

```kotlin
// Wrap network calls with Result<T, DataError.Network>
suspend fun getTransactions(): Result<List<TransactionDto>, DataError.Network> {
    return safeCall { httpClient.get(route = "/transactions") }
}
```

### DTO ΓåÆ Domain mapping

```kotlin
// In feature:data
fun TransactionDto.toTransaction(): Transaction = Transaction(
    id = id,
    amount = amount,
    description = description,
    category = category.toCategory(),
    date = LocalDate.parse(date)
)

fun Transaction.toTransactionDto(): TransactionDto = TransactionDto(...)
fun TransactionEntity.toTransaction(): Transaction = ...
fun Transaction.toTransactionEntity(): TransactionEntity = ...
```

---

## Error Handling

Follow the **android-error-handling** skill for all error types and the `Result` wrapper.

### Core types

```kotlin
interface Error

sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : Error>(val error: E) : Result<Nothing, E>
}

typealias EmptyResult<E> = Result<Unit, E>
```

### DataError

```kotlin
sealed interface DataError : Error {
    enum class Network : DataError { BAD_REQUEST, NO_INTERNET, UNAUTHORIZED, SERVER_ERROR, ... }
    enum class Local : DataError { DISK_FULL, NOT_FOUND, UNKNOWN }
}
```

### Usage rules

- Never throw for expected failures ΓÇö always return `Result.Error`
- Catch exceptions at the layer that owns them (network in data, validation in domain, etc.)
- Map errors to `UiText` via `.toUiText()` extension functions
- Chain with `.onSuccess{}` / `.onFailure{}` / `.map{}` / `.asEmptyResult()`

---

## Compose UI Conventions

Follow the **android-compose-ui** skill for all Compose work.

### Key rules

- **No business logic in composables** ΓÇö render state, forward actions
- **No `remember` for app state** ΓÇö use ViewModel StateFlow + `collectAsStateWithLifecycle()`
- **No side effects in Screen composables** ΓÇö use ViewModel Actions instead
- **`LaunchedEffect`** only for genuinely Compose-owned concerns
- **`graphicsLayer`** for animations (avoid recomposition)
- **`key`** on `LazyColumn` items when there's a clear unique ID
- **Previews** on every Screen composable with realistic sample data
- **`contentDescription`** on all interactive/informational elements

---

## Testing

Follow the **android-testing** skill for all test work.

### Stack

| Concern | Library |
|---|---|
| Test framework | JUnit5 |
| Assertions | AssertK |
| Flow testing | Turbine |
| Coroutines | `kotlinx-coroutines-test` + `UnconfinedTestDispatcher` |
| UI testing | `ComposeTestRule` |

### What to test

- Unit-test every ViewModel
- Unit-test domain/data logic
- Fakes over mocks for repositories
- `SavedStateHandle` instantiated directly in tests (no mocking)
- Compose UI tests for critical flows
- Robot pattern for screens with 3+ UI test cases

### ViewModel test example

```kotlin
class HomeViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setUp() { Dispatchers.setMain(testDispatcher) }
    @AfterEach
    fun tearDown() { Dispatchers.resetMain() }

    @Test
    fun `loading transactions emits state`() = runTest {
        val repo = FakeTransactionRepository()
        val viewModel = HomeViewModel(repo)

        viewModel.state.test {
            viewModel.onAction(HomeAction.OnRefresh)
            assertThat(awaitItem().isLoading).isTrue()
            assertThat(awaitItem().transactions).isNotEmpty()
        }
    }
}
```

---

## Git Workflow

### Branch strategy

```
main  ΓöÇΓöÇΓöÇΓöÇ M0 ΓöÇΓöÇΓùÅΓöÇΓöÇΓùÅΓöÇΓöÇΓùÅΓöÇΓöÇΓöÇΓöÇΓùÅΓöÇΓöÇΓöÇΓöÇΓùÅΓöÇΓöÇΓöÇΓöÇΓùÅΓöÇΓöÇΓöÇΓöÇΓùÅ
                \         /   /
feature/onboarding   features/   fix/
```

| Branch | Purpose |
|---|---|
| `main` | Production-ready ΓÇö linear history via squash merges only, never commit directly |
| `feature/<name>` | One per spec item (e.g. `feature/splash`, `feature/home`, `feature/budgets`) |
| `fix/<name>` | Bug fixes |

### Commit convention: Conventional Commits

```
<type>(<scope>): <short description>
```

| Type | When |
|---|---|
| `feat` | New feature screen or capability |
| `fix` | Bug fix |
| `refactor` | Code change with no behavior change |
| `style` | Formatting, theming, design tokens |
| `docs` | AGENTS.md, spec notes |
| `chore` | Gradle, deps, build config |
| `test` | Adding/fixing tests |

**Examples:** `feat(splash): add splash screen with auto-nav`, `style(theme): apply Material3 color palette per spec`, `chore(deps): add Room and Koin dependencies`

### Standard workflow

```bash
# 1. Start from latest main
git checkout main && git pull

# 2. Create feature branch
git checkout -b feature/<name>

# 3. Work and commit frequently
git add -A && git commit -m "<type>(<scope>): <message>"

# 4. Before PR ΓÇö rebase onto latest main
git fetch origin && git rebase origin/main

# 5. Push and open PR
git push -u origin feature/<name>

# 6. Squash-merge to main via GitHub
# 7. Delete the feature branch
```

### Rules

- **Never commit directly to `main`** ΓÇö always use feature branches + PRs
- **Rebase, don't merge** ΓÇö rebase feature branches onto latest `main` before PR to keep history linear
- **Squash-merge PRs** ΓÇö one atomic commit per feature on `main`
- **No force-push** to `main` or shared branches (OK on feature branches before PR)
- **Auto-staging:** `git add -A` before each commit unless selective staging is needed
- **Inspect before PR:** always check `git status`, `git diff`, `git log --oneline -10` before pushing

---

## Implementation Order

Build features in this order (per spec):

1. **Splash** ΓÇö logo, auto-navigate based on first-launch flag
2. **Onboarding** ΓÇö 3 paginated steps (Track Expenses / Analyze / Set Budgets)
3. **Auth** ΓÇö Register (name, email, password, "Get Started" button)
4. **Dashboard** ΓÇö balance card, quick stats, recent transactions, nav cards (Categories, Budgets)
5. **Transaction** ΓÇö List screen, then Add/Edit screen (income & expense variants, amount, category, note, date, delete)
6. **Analytics** ΓÇö pie chart (by category), bar chart (monthly), insights
7. **Category** ΓÇö List screen, then Add/Edit screen (name, color picker, icon picker)
8. **Budget** ΓÇö List screen, then Add/Edit screen (amount input, category picker, month picker, optional note)
9. **Settings** ΓÇö dark mode toggle, currency, language, logout
10. **Profile** ΓÇö View screen, then Edit screen (name, email, phone, bio, photo)

---

## Code Conventions

| Thing | Convention | Example |
|---|---|---|
| ViewModel | `<Screen>ViewModel` | `HomeViewModel` |
| State | `<Screen>State` | `HomeState` |
| Action | `<Screen>Action` | `HomeAction` |
| Event | `<Screen>Event` | `HomeEvent` |
| Root composable | `<Screen>Root` | `HomeRoot` |
| Screen composable | `<Screen>Screen` | `HomeScreen` |
| UI model | `<Model>Ui` | `TransactionUi` |
| Route | `<Screen>Route` | `HomeRoute` |
| DTO | `<Model>Dto` | `TransactionDto` |
| Room entity | `<Model>Entity` | `TransactionEntity` |
| Data source interface | `<Entity>[Local\|Remote]DataSource` | `TransactionLocalDataSource` |
| Data source impl | what makes it unique | `RoomTransactionDataSource` |
| Repository interface | `<Entity>Repository` | `TransactionRepository` |
| Repository impl | what makes it unique | `OfflineFirstTransactionRepository` |
| Koin module | `<feature><Layer>Module` | `dashboardDataModule` |
| Nav graph | `<feature>Graph` | `dashboardGraph` |
