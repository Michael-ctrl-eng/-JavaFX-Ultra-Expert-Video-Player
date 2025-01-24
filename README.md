# JavaFX Ultra-Expert Video Player

This is a video player application built with JavaFX to showcase advanced software engineering practices.

## Features

*   Clean Architecture
*   Immutability
*   Reactive UI
*   Custom UI Controls
*   Subtitles
*   Playback Controls
*    ....

## Getting Started

1.  Clone the repository
2.  Install dependencies with Maven.
3.  Run the main class.

## Project Structure

  *   `core`: Contains the core business logic, entities, and use cases.
     *    `model`: Immutable data structures representing the core state.
    *    `usecase`: Defines the use case interfaces.
 *    `adapter`: Contains the implementations of interfaces between the use cases and the UI and frameworks.
      * `presenter`: Contains the presenters of the application
      * `impl`: Implementation of the presenter.
 *    `framework`: Contains the implementations of external frameworks and libraries.
      * `impl`: Implementation of the framework to handle media.
       *   `utils`: Utility classes.
 *    `ui`: Contains the UI components.
      *    `view`: Contains all UI components.
      *    `utils`: Utility classes.

## Architecture Diagram

  ```mermaid
    graph LR
        A[UI] --> B(Presenter)
        B --> C(Use Cases)
        C --> D(Entities)
       C --> E(Framework)
