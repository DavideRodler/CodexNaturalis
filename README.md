# Progetto Di Ingegneria del Software 2024 - Codex Naturalis

![alt text](/src/main/resources/README/Slider-Codex-2-1920x1080.jpg)
Il progetto consiste nell'implementazione del gioco da tavolo codex naturalis.

- La comunicazione e' stata gestita con Socket ed RMI.
- Interazione e gameplay: linea di comando (CLI) e grafica (GUI).

## Documentazione
Javadoc è disponibile al seguente link: [Javadoc](

### UML
I seguenti diagrammi delle classi rappresentano rispettivamente il modello iniziale sviluppato durante la fase di progettazione e i diagrammi del prodotto finale nelle parti critiche riscontrate.
- [UML finale](https://github.com/tommypic/ing-sw-2024-piccoli-ponzani-piotti-rodler/tree/main/SeqDiagram/UML_finale.pdf)
- [UML iniziale](https://github.com/tommypic/ing-sw-2024-piccoli-ponzani-piotti-rodler/tree/main/SeqDiagram/UML_iniziale.pdf)

### Sequence Diagram
I seguenti diagrammi di sequenza rappresentano le interazioni tra le il client e il server.

- [Seqence Diagram Connection](https://github.com/tommypic/ing-sw-2024-piccoli-ponzani-piotti-rodler/tree/main/SeqDiagram/ActionDiagram.png)
- [Seqence Diagram_Action_From_Client](https://github.com/tommypic/ing-sw-2024-piccoli-ponzani-piotti-rodler/tree/main/SeqDiagram/NetworkDiagram.png)

### Coverage report
Al seguente link è possibile consultare il report della coverage dei test effettuati con Junit

- [Coverage model](https://github.com/tommypic/ing-sw-2024-piccoli-ponzani-piotti-rodler/tree/main/SeqDiagram/coveraageModel.png)
- [Coverage controller](https://github.com/tommypic/ing-sw-2024-piccoli-ponzani-piotti-rodler/tree/main/SeqDiagram/coverageController.png)

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

- Scaricare il file jar al seguente [qui](https://github.com/tommypic/ing-sw-2024-piccoli-ponzani-piotti-rodler/jar/CodexNaturalis-client.jar)
- digitare il seguente comando per avviare il client con interfaccia grafica
```
java -jar CodexNaturalis-client.jar
```
- inserire l'indirizzo ip del server
- selezionare l'interfaccia grafica
- Nel caso si scelga la **GUI** bisogna selezionare la comunicazione da riga di comando e aspettare che il client si connetta al server
- nel caso in cui un altro giocatore sta selezionando il numero di giocatori premere invio per ritentare la connessione
- nel caso di disconnessione premere invio due volte per riconnettersi, il comando va fatto da terminale anche se si usa la GUI

### Server

Le seguenti istruzioni descrivono come eseguire il server.

- Scaricare il file jar al seguente [qui](https://github.com/tommypic/ing-sw-2024-piccoli-ponzani-piotti-rodler/jar/CodexNaturalis-client.jar)
  - digitare il seguente comando per avviare il server
```
java -jar CodexNaturalis-server.jar 

```
- inserire il proprio indirizzo ip
- inserire se si vuole avviare una nuova partita o riprendere una partita salvata

