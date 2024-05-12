module com.example.progettosw {
  //  requires javafx.controls;
  //  requires javafx.fxml;
    requires json.simple;
    requires java.rmi;
    requires java.desktop;
    exports Network.Server to java.rmi;
    exports Network.Client to java.rmi;
    exports controller to java.rmi;
    exports model to java.rmi;
    exports model.cards to java.rmi;
    exports model.client to java.rmi;
    exports observers to java.rmi;
    exports Network.Client.RMI to java.rmi;
}