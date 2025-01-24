# JavaFX Ultra-Expert Video Player

This is a video player application built with JavaFX to showcase advanced software engineering practices, architectural patterns, performance optimization, and best practices for maintainability, and scalability.

## Introduction

This project demonstrates a comprehensive approach to software engineering, implementing a fully functional video player application from scratch. It’s more than just a functional tool; it is an illustration of robust architectural principles, clean code practices, advanced testing strategies, resource management and secure coding.

## Key Features

*   **Clean Architecture:** Clear separation of concerns using distinct layers (entities, use cases, interface adapters, frameworks).
*   **Reactive UI:** Leverages RxJava to handle asynchronous operations, ensure smooth UI updates and better overall responsiveness.
*   **Immutable State Management:** Application state managed immutably to avoid unintended side effects and prevent data inconsistencies.
*   **Customizable UI:** Intuitive UI elements, along with custom menus and drag to seek functionality, designed for simplicity, responsiveness and enhanced user interaction.
*  **Keyboard Shortcuts:** Includes keyboard shortcuts for easier video navigation, full screen usage, and proper control handling.
*   **Dynamic File Loading:**  The application allows the user to dynamically load media and subtitle files using `javafx.stage.FileChooser`.
*  **Performance Optimized Video Playback**:  The application makes use of efficient rendering, multithreading and resource pooling for handling intensive video files.
*   **Robust Error Handling:** Comprehensive error handling using custom exceptions and feedback for the UI. The application gracefully recovers from most common problems, including corrupted or invalid media files.
*   **Test Driven Development:** Full testing approach by implementing detailed Unit Tests for most core methods, comprehensive UI testing using TestFX and a proper memory performance testing approach to create robust and stable application.
*   **CI/CD:** GitHub actions configuration that includes testing, code analysis, dependency scanning, automated deployment (to a testing environment) using the power of Git, workflows and continuous integration.
*   **Detailed Documentation:**  Extensive documentation with detailed Javadoc comments in every method, a structured and descriptive `README.md` with an architecture diagram, instructions for build, how to test it, how to extend the project and use its multiple features, along with clear guidance for developers who want to contribute to the project, a changelog for different versions, and a `HOW_TO_RUN.txt` to quickly and effectively start the application.
*   **Secure Implementation:** Dependency analysis and sanitized input implementation using best security standards.

## Project Architecture

This application follows a Clean Architecture, organized in the following way:

*   **`core` (Entities and Use Cases):**
    *   `model`: Contains immutable data structures that define the core entities (`Subtitle`, `MediaMetadata`, `PlayerState`).
    *   `usecase`: Defines the core use case interfaces of the application such as loading, playback, file selection.
    *   `exception`: Custom exception to help properly identify, log, and provide feedback for different scenarios.

*   **`adapter` (Interface Adapters):**
    *   `presenter`: Defines interfaces that abstract the communication with the UI layer with core services, also handles the data processing and conversions to display on the UI.
    *   `impl`: The presenter implementations which inject dependencies.

*   **`framework` (Frameworks & Drivers):**
    *   `impl`:  Frameworks are injected into Use Case classes which then use libraries to deliver required functionality. Includes resource management to fetch MediaPlayer resources and `JavaFXMediaPlayer` to create a media player for a source.
        *  Includes implementation of framework dependencies, such as RxJava subscriptions, handling events from JavaFx framework and proper setup of threads.
    *   `utils`: Utility classes used in the framework to parse data (`SubtitleParser`) and manage resources with multithreading(`ThreadManager`), media players(`MediaPlayerResourcePool`)

*   **`ui` (UI Layer):**
    *   `view`: This layer provides user components such as `MediaViewComponent`, `MediaControls`, `FileMenu`, and `SubtitleComponent`.
    *    `utils`: Includes UI related classes such as helper dialog messages, and constants.

    This structure allows for clear separation of concerns and a high degree of flexibility to swap any particular implementation, by changing a layer to another one, which is in part responsible of high testing coverage with ease.
    The high test coverage provided with the use of TDD methodologies, using various kinds of tests such as UI tests (TestFX), Integration tests (Test interactions) and Unit tests also enables us to quickly change or improve any single component without breaking any contract on the project.

     ```mermaid
      graph LR
         A[UI] --> B(Presenter)
         B --> C(Use Cases)
          C --> D(Entities)
        C --> E(Framework)
        ```

## Core Technologies and Libraries

*   **JavaFX:** For the UI.
*   **RxJava:**  For reactive programming.
*   **JUnit 5:**  For unit testing.
*   **TestFX:** For UI Testing
*  **Mockito**: Mocking framework to ease and enable unit and integration tests.
*  **OWASP Dependency Check Plugin:** For finding any vulnerabilities on the dependencies.
* **Maven Plugins (Checkstyle, SpotBugs, PMD):** For code style, detection of bugs and problems with the source code.

## Build and Installation Instructions

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/Michael-ctrl-eng/-JavaFX-Ultra-Expert-Video-Player.git
    ```
2.  **Install JDK:**
    You will need JDK 17 or higher installed on your computer to build and run the project.
3.  **Use Maven:** Build the application:
    ```bash
        mvn clean install
    ```
     This command compiles all source code and packages them into a `.jar` in `/target`
4. **Run**: Then you can execute the project from terminal:
    ```bash
    java -jar target/javafx-video-player-1.0-SNAPSHOT.jar
    or you can just use `run.sh` on macOS/Linux or `run.bat` in Windows by just executing the script or by double-clicking it.
