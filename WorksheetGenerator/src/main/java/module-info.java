module com.wgapp.worksheetgenerator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.json;
    requires java.dotenv;
    requires java.sql;
    requires jdk.security.jgss;
    requires java.xml.crypto;
    requires kernel;
    requires layout;
    requires io;
    requires jbcrypt;
    requires jdk.jshell;
    requires jdk.compiler;

    opens com.wgapp.worksheetgenerator to javafx.fxml;

    exports com.wgapp.worksheetgenerator;
    exports com.wgapp.worksheetgenerator.Components;
    exports com.wgapp.worksheetgenerator.ViewFactory;
    exports com.wgapp.worksheetgenerator.Controllers;
    exports com.wgapp.worksheetgenerator.ModelsUI;
    exports com.wgapp.worksheetgenerator.Controllers.UI;
    exports com.wgapp.worksheetgenerator.ModelsUI.Enums;
    exports com.wgapp.worksheetgenerator.DAO.Entities;


}