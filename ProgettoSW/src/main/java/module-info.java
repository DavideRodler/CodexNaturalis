module com.example.progettosw {
  //  requires javafx.controls;
  //  requires javafx.fxml;
    requires json.simple;
    requires java.rmi;
    exports Network.Server to java.rmi;
    exports Network.Client to java.rmi;
    exports controller to java.rmi;
    exports model to java.rmi;
    exports model.cards to java.rmi;
}