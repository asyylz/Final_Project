module com.wgapp.worksheetgenerator.worksheetgenerator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.wgapp.worksheetgenerator to javafx.fxml;
    exports com.wgapp.worksheetgenerator;
}