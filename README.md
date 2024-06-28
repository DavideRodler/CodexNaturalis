# Progetto Di Ingegneria del Software 2024 - Codex Naturalis

![alt text](/src/main/resources/README/Slider-Codex-2-1920x1080.jpg)
Il progetto consiste nell'implementazione del gioco da tavolo codex naturalis.

- La comunicazione e' stata gestita con Socket ed RMI.
- Interazione e gameplay: linea di comando (CLI) e grafica (GUI).

## Documentazione
Javadoc è disponibile al seguente link: [Javadoc](

### UML
I seguenti diagrammi delle classi rappresentano rispettivamente il modello iniziale sviluppato durante la fase di progettazione e i diagrammi del prodotto finale nelle parti critiche riscontrate.


### Coverage report
Al seguente link è possibile consultare il report della coverage dei test effettuati con Junit

### Librerie e Plugins
| Libreria/Plugin | Descrizione                                                                              |
| --------------- | ---------------------------------------------------------------------------------------- |
| __Maven__       | Strumento di automazione della compilazione utilizzato principalmente per progetti Java. |
| __JavaFx__      | Libreria grafica per realizzare interfacce utente.                                       |
| __JUnit__       | Framework di unit testing.                                                               |

## Funzionalità
### Funzionalità Sviluppate
- Regole Complete
- CLI
- GUI
- Socket
- RMI
- 2 FA (Funzionalità Avanzate):
    - __Persistenza:__ lo stato di una partita deve essere salvato su disco, in modo che la partita possa riprendere anche a seguito dell’interruzione dell’esecuzione del server.
    - **Chat**: Possibilita' di scrivere su una chat globale o privata sia nella GUI che nella CLI


## Esecuzione
Questo progetto richiede una versione di Java 22 o superiore per essere eseguito correttamente.

###  Client
Le seguenti istruzioni descrivono come eseguire il client con interfaccia CLI o GUI.

```
java -jar CodexNaturalis-client.jar
```

### Server
Per lanciare Santorini Server digitare da terminale il comando:
```
java -jar CodexNaturalis-server.jar 