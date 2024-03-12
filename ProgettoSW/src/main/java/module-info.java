module com.example.progettosw {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.progettosw to javafx.fxml;
    exports com.example.progettosw;
}