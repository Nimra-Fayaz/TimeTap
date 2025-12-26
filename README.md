# Time Calculator with Todo Management

An Android application demonstrating functional programming concepts with a time-based calculator and in-memory todo management system. This project showcases custom type implementation, operator overloading in Kotlin, and modern Android development practices with a professional mustard and black gradient UI theme.

## Overview

This application combines two main features:

1. **Time Calculator** - A calculator interface for adding durations expressed in days, hours, and minutes
2. **Todo Management** - A task management system where each todo is associated with a time duration

The project demonstrates the implementation of an immutable Duration type with operator overloading, a monad-like Timed wrapper for functional composition, and clean architecture using MVVM pattern with Jetpack Compose.

## Features

### Time Calculator

- Custom immutable Duration data class representing time in days, hours, and minutes
- Operator overloading for natural addition of durations using the `+` operator
- Automatic normalization (e.g., 90 minutes converts to 1 hour 30 minutes)
- Calculator-style user interface with:
  - Digit buttons (0-9)
  - Unit selectors (d, h, m for days, hours, minutes)
  - Operations (+, =, C for clear)
  - Gradient display showing current input and accumulated results
- Real-time display of accumulated calculations
- Professional mustard and black gradient color scheme

### Todo Management

- Create, read, update, and delete todos in memory
- Each todo contains a description and associated duration
- Multi-select capability for batch operations
- Finish feature that combines multiple selected todos using functional composition
- Displays total accumulated time and aggregated descriptions when completing tasks
- All data is stored in-memory during the app session
- Modern card-based UI with gradient highlights for selected items

### Technical Implementation

- **Functional Programming**: Implementation of map, flatMap, and combine operations on the Timed type
- **Reactive UI**: Jetpack Compose with StateFlow for state management
- **MVVM Architecture**: Clear separation between UI and business logic
- **Immutable Data Structures**: All models are immutable with proper copy semantics
- **Custom Theme**: Professional mustard and black gradient design with bold typography
- **Material Design 3**: Modern UI components with custom styling

## Core Concepts

### Duration Type

The Duration class is an immutable data type that represents time periods:

```kotlin
data class Duration(
    val days: Int = 0,
    val hours: Int = 0,
    val minutes: Int = 0
)
```

Key features:
- Operator overloading for addition: `duration1 + duration2`
- Automatic normalization of time units
- Conversion to and from total minutes
- String parsing and formatting

Example usage:
```kotlin
val d1 = Duration(days = 2, hours = 3, minutes = 30)
val d2 = Duration(hours = 1, minutes = 45)
val total = d1 + d2  // Result: 2d 5h 15m
```

### Timed Monad

The Timed type wraps a value with an associated duration, providing functional composition operations:

```kotlin
data class Timed<T>(
    val duration: Duration,
    val value: T
)
```

Supported operations:
- `map` - Transform the wrapped value while preserving duration
- `flatMap` - Chain operations with duration accumulation
- `combine` - Aggregate multiple Timed instances

Example usage:
```kotlin
val todos = listOf(
    Timed(Duration(hours = 2), "Write code"),
    Timed(Duration(hours = 1, minutes = 30), "Review PR")
)
val combined = Timed.combine(todos)
// Result: Timed(3h 30m, ["Write code", "Review PR"])
```

The combine function uses fold and flatMap to accumulate both durations and values, demonstrating functional programming principles in a practical context.

## Project Structure

```
Mobile_lab3/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/timecalculator/
│   │   │   ├── model/                      # Core data models
│   │   │   │   ├── Duration.kt             # Duration with + operator
│   │   │   │   ├── Timed.kt                # Monad with map/flatMap/combine
│   │   │   │   └── TodoItem.kt             # Todo data model
│   │   │   ├── ui/                         # User interface
│   │   │   │   ├── calculator/
│   │   │   │   │   ├── TimeCalculatorScreen.kt
│   │   │   │   │   └── TimeCalculatorViewModel.kt
│   │   │   │   ├── todos/
│   │   │   │   │   ├── TodosListScreen.kt
│   │   │   │   │   ├── TodoDetailScreen.kt
│   │   │   │   │   └── TodosViewModel.kt
│   │   │   │   └── theme/
│   │   │   │       └── Theme.kt            # Mustard & black theme
│   │   │   ├── navigation/
│   │   │   │   └── NavGraph.kt
│   │   │   └── MainActivity.kt
│   │   ├── res/                            # Android resources
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── gradle/                                 # Gradle configuration
└── build.gradle.kts
```

## Technical Requirements

- Android SDK 24+ (Android 7.0 Nougat or higher)
- JDK 11 or later
- Gradle 8.9
- Kotlin 2.0.21

## Installation and Setup

### Prerequisites

- Android Studio (latest version recommended)
- Java Development Kit (JDK) 11 or later

### Setup

1. Open the project in Android Studio

2. Configure Gradle JDK (if needed):
   - Go to `File` → `Settings` → `Build, Execution, Deployment` → `Build Tools` → `Gradle`
   - Set Gradle JDK to "Android Studio's Embedded JDK"

3. Wait for Gradle sync to complete

4. Run the application on an emulator or physical device

## Usage

### Time Calculator

1. Launch the application to access the Time Calculator screen
2. Enter a duration by:
   - Tapping digit buttons (0-9)
   - Selecting unit type (d for days, h for hours, m for minutes)
   - Example: tap 2, d, 3, h, 3, 0, m to enter "2d3h30m"
3. Press + to add the current duration to the running total
4. Press = to display the final calculated result
5. Press C to clear and start over

### Todo Management

1. Tap the List icon in the calculator header to navigate to todos
2. View existing todos with their associated durations (3 sample todos are preloaded)
3. Create a new todo:
   - Tap the floating + button
   - Enter a description and duration (days, hours, minutes)
   - Tap "Create Task"
4. Edit a todo:
   - Tap the edit icon on any todo card
   - Modify description or duration
   - Tap "Save Changes"
5. Select multiple todos by tapping on them (they will highlight with gradient)
6. Tap the checkmark floating button to finish selected todos
7. A dialog displays the total combined duration and all descriptions using the Timed.combine function
8. Finished todos are automatically removed from the list

## UI Design

### Color Scheme

The app features a professional **mustard and black gradient theme**:

- **Primary**: Mustard Yellow (#E5B80B)
- **Secondary**: Dark Mustard (#C49B0B)
- **Accent**: Light Mustard (#FFC107)
- **Background**: Soft gradients from off-white to pale mustard
- **Text**: Deep Black (#0A0A0A) for maximum contrast
- **Cards**: White with gradient overlays on selection

### Typography

- **Headings**: Bold, professional fonts (24-36sp)
- **Titles**: SemiBold to Bold (16-22sp)
- **Body**: Clean and readable (14-16sp)
- All text optimized for readability with proper letter spacing

### Visual Elements

- Smooth gradient backgrounds throughout
- Elevated shadows on interactive elements
- Rounded corners (16-20dp) for modern look
- Gradient buttons with appropriate icons
- Professional spacing and alignment

## Dependencies

- Jetpack Compose (UI framework)
- Compose Navigation (screen navigation)
- Lifecycle ViewModel Compose (state management)
- Kotlin Coroutines (asynchronous operations)
- Material Design 3 (UI components with custom theming)

## Implementation Highlights

### Operator Overloading

The Duration class implements the plus operator for intuitive duration addition:

```kotlin
operator fun plus(other: Duration): Duration {
    return fromMinutes(this.toMinutes() + other.toMinutes())
}
```

### Functional Composition

The Timed.combine function demonstrates functional programming with fold and flatMap:

```kotlin
fun <T> combine(items: List<Timed<T>>): Timed<List<T>> =
    items.fold(pure(emptyList())) { acc, timed ->
        acc.flatMap { list ->
            timed.map { value -> list + value }
        }
    }
```

This allows accumulating both durations and values from multiple Timed instances in a single operation.

### State Management

- StateFlow for reactive state updates
- viewModelScope for coroutine lifecycle management
- Immutable data classes prevent unintended mutations
- In-memory storage for simplicity

## Architecture Patterns

- **MVVM**: ViewModels manage UI state and business logic
- **Unidirectional Data Flow**: State flows from ViewModels to UI
- **Functional Programming**: Immutable data structures and function composition
- **Single Source of Truth**: ViewModels hold the authoritative state

## Sample Data

The application includes three sample todos for demonstration:
1. "Study for exam" - Duration: 5 hours 30 minutes
2. "Grocery shopping" - Duration: 1 hour 15 minutes
3. "Complete Android Lab 3" - Duration: 3 days 2 hours

## Known Limitations

- Data is stored in memory only; todos are lost when the app is closed
- No data persistence or database integration
- Single user environment
- No undo functionality

## Future Enhancements

- Database integration for persistent storage (Room)
- Todo categories and priority levels
- Deadline tracking and notifications
- Historical data and statistics
- Export functionality for completed tasks
- Settings for theme customization

## Testing

The application can be tested by:

1. Running the app on an emulator or physical device
2. Creating todos with various durations
3. Using the calculator to add durations manually
4. Selecting and finishing multiple todos to verify duration combination
5. Verifying the Timed.combine function displays correct results

## License

This project is created for educational purposes.

## Acknowledgments

Built using modern Android development practices and functional programming concepts. The implementation demonstrates practical applications of operator overloading, functional composition with monadic structures, and reactive state management in mobile development. The UI design showcases a professional gradient-based theme with Material Design 3 components.

## Author

Nimra Fayaz
