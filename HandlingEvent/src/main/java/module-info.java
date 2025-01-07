module com.asiye.eventhandler {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.asiye.eventhandler to javafx.fxml;
    exports com.asiye.eventhandler;
}