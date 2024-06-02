 module com.example.progettosw {
    //  requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires java.rmi;
    requires javafx.graphics;
    requires javafx.controls;
    requires java.desktop;
    opens View.GUI to javafx.fxml;
    exports View.GUI to javafx.graphics;
    //exports View.GUI to javafx.fxml;
    exports Network.Server to java.rmi;
    exports Network.Client to java.rmi;
    exports Network.Client.RMI to java.rmi;
    exports observers to java.rmi;
    exports controller to java.rmi;
    exports model to java.rmi;
    exports model.cards to java.rmi;
 }
