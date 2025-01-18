module com.wgapp.worksheetgenerator {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires org.json;
    requires java.dotenv;

    opens com.wgapp.worksheetgenerator to javafx.fxml;
    exports com.wgapp.worksheetgenerator;
    exports com.wgapp.worksheetgenerator.Views;
    exports com.wgapp.worksheetgenerator.Controllers;
    exports com.wgapp.worksheetgenerator.Models;
    exports com.wgapp.worksheetgenerator.Controllers.UI;

}