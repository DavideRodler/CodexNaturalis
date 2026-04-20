# Software Engineering Project 2024 - Codex Naturalis
![Codex Naturalis](/src/main/resources/README/Slider-Codex-2-1920x1080.jpg)

This project is a full software implementation of the board game **Codex Naturalis**, developed as part of the Software Engineering course at Politecnico di Milano (2024).

- **Network communication** is handled via both Socket and RMI protocols.
- **User interaction** is supported through a Command Line Interface (CLI) and a Graphical User Interface (GUI).

---

## Documentation

### UML Diagrams
The following class diagrams represent the initial model designed during the planning phase and the final architecture of the most critical components.
- [Final UML](https://github.com/DavideRodler/CodexNaturalis/tree/main/SeqDiagram/UML_finale.pdf)
- [Initial UML](https://github.com/DavideRodler/CodexNaturalis/tree/main/SeqDiagram/UML_iniziale.pdf)

### JavaDoc
The full API documentation was generated with JavaDoc and is available at the following link:
- [JavaDoc](https://github.com/DavideRodler/CodexNaturalis/tree/main/JavaDocs/allclasses-index.html)

### Sequence Diagrams
The following sequence diagrams illustrate the interactions between the client and the server.
- [Connection Sequence Diagram](https://github.com/DavideRodler/CodexNaturalis/tree/main/SeqDiagram/ActionDiagram.png)
- [Action from Client Sequence Diagram](https://github.com/DavideRodler/CodexNaturalis/tree/main/SeqDiagram/NetworkDiagram.png)

### Test Coverage Report
The following reports detail the test coverage achieved using JUnit.
- [Model Coverage](https://github.com/DavideRodler/CodexNaturalis/tree/main/SeqDiagram/coverageModel.jpeg)
- [Controller Coverage](https://github.com/DavideRodler/CodexNaturalis/tree/main/SeqDiagram/coverageController.jpeg)

### Libraries & Plugins
| Library / Plugin | Description |
| ---------------- | ----------- |
| **Maven**        | Build automation tool used for dependency management and project compilation. |
| **JavaFX**       | UI toolkit for building rich graphical interfaces. |
| **JUnit**        | Framework for unit testing. |

---

## Features

### Implemented Features
- Complete game rules
- Command Line Interface (CLI)
- Graphical User Interface (GUI)
- Socket communication
- RMI communication
- 2 Advanced Features (FA):
  - **Persistence:** the game state is saved to disk, allowing a match to resume after a server interruption.
  - **Chat:** players can communicate via a global or private chat, available in both GUI and CLI.

---

## Running the Application

This project requires **Java 22 or higher**.

### Client

To run the client in either CLI or GUI mode:

1. Download the JAR file [here](https://github.com/DavideRodler/CodexNaturalis/tree/main/jar/).
2. Launch the client with the following command:
```bash
java -jar CodexNaturalis-client.jar
```
3. Enter the server's IP address when prompted.
4. Select the desired interface (CLI or GUI).

> **Note — GUI mode:** select the communication type from the command line and wait for the client to connect to the server.

> **Note — waiting for players:** if another player is currently selecting the number of players, press `Enter` to retry the connection.

> **Note — reconnection:** in case of disconnection, press `Enter` twice to reconnect. This must be done from the terminal even when using the GUI.

### Server

To run the server:

1. Download the JAR file [here](https://github.com/DavideRodler/CodexNaturalis/tree/main/jar).
2. Launch the server with the following command:
```bash
java -jar CodexNaturalis-server.jar
```
