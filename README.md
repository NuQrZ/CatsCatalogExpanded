# Cats Catalog Expanded

An Android application for browsing cat breeds, taking quizzes, and
tracking user scores. The project is built in Kotlin using the modern
Android stack (Jetpack Compose, MVVM, Room, Retrofit).

------------------------------------------------------------------------

## Features

-  Browse cat breeds (fetched from API)\
-  Breed details screen\
-  Cat knowledge quiz\
-  Leaderboard with user scores\
-  User registration and login\
-  Local database with Room\
-  Navigation with Compose Navigation

------------------------------------------------------------------------

## Architecture

The project follows the **MVVM (Model--View--ViewModel)** architecture
with clear separation of concerns:

    UI (Jetpack Compose)
       ↓
    ViewModel
       ↓
    Repository
       ↓
    Data sources (Retrofit / Room)

### Core Components

-   **UI** --- Jetpack Compose screens\
-   **ViewModel** --- state management\
-   **Repository** --- data abstraction layer\
-   **Room** --- local persistence\
-   **Retrofit** --- network layer\
-   **Navigation** --- app routing

------------------------------------------------------------------------

## Tech Stack

-   Kotlin\
-   Jetpack Compose\
-   MVVM\
-   Room Database\
-   Retrofit\
-   Coroutines / Flow\
-   Material Design 3

------------------------------------------------------------------------

## Project Structure

    app/
     └── src/main/java/myapp/catscatalogexpanded/
         ├── db/                # Room database
         ├── navigation/        # Navigation setup
         ├── ui/                # Compose screens
         │   ├── breedsScreen/
         │   ├── quizScreen/
         │   ├── leaderBoardScreen/
         │   ├── loginScreen/
         │   └── registrationScreen/
         └── users/             # User management

------------------------------------------------------------------------

## Getting Started

### Prerequisites

-   Android Studio Hedgehog or newer\
-   JDK 17\
-   Android SDK

### Steps

1.  Clone the repository:

``` bash
git clone <repo-url>
```

2.  Open the project in Android Studio.

3.  Sync Gradle.

4.  Run the app on an emulator or physical device.

------------------------------------------------------------------------

## APIs

The application uses:

-   **Cats API** for cat breeds\
-   **Leaderboard API** for quiz results

(API endpoints are configured inside Retrofit services.)

------------------------------------------------------------------------

## Testing

-   Unit tests: `app/src/test`\
-   Instrumentation tests: `app/src/androidTest`

Run tests with:

``` bash
./gradlew test
```

------------------------------------------------------------------------

## Possible Improvements

-   Proper authentication (JWT/Firebase)\
-   Dark mode toggle\
-   Advanced offline caching\
-   Higher test coverage\
-   Pagination for large lists

------------------------------------------------------------------------

## Author

**NuQrZ (Luka Gojkovic)**
