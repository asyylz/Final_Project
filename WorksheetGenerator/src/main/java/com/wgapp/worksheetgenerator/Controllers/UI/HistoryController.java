package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Controllers.WorksheetController;
import com.wgapp.worksheetgenerator.ModelsUI.Model;
import com.wgapp.worksheetgenerator.ModelsUI.WorksheetProperty;
import javafx.beans.property.ListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;


public class HistoryController implements Initializable, WorksheetController.WorksheetObserver {

    private final WorksheetController worksheetController = new WorksheetController();

    public TextField searchTextField;
    public ImageView searchIconBtn;
    public Circle backgroundCircle;
    public TableView worksheetTable;
    public TableColumn noColumn;
    public TableColumn<WorksheetProperty, Integer> idColumn;
    public TableColumn<WorksheetProperty, String> mainSubColumn;
    public TableColumn<WorksheetProperty, String> subSubColumn;
    public TableColumn<WorksheetProperty, String> diffLevelColumn;
    private static final int ROWS_PER_PAGE = 10; // Number of rows per page
    private Pagination pagination;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Observer pattern
        worksheetController.addObserver(this);

        // For list
        worksheetController.listWorksheets(Model.getInstance().getUserProperty());


        // Set up the columns of the TableView to bind to the properties of WorksheetProperty
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        mainSubColumn.setCellValueFactory(cellData -> cellData.getValue().mainSubjectProperty().asString());
        subSubColumn.setCellValueFactory(cellData -> cellData.getValue().subSubjectProperty().asString());
        diffLevelColumn.setCellValueFactory(cellData -> cellData.getValue().diffLevelProperty().asString());


        DropShadow dropShadow = new DropShadow();
        dropShadow.setBlurType(BlurType.GAUSSIAN);
        dropShadow.setColor(Color.valueOf("#FFF5FA"));
        dropShadow.setRadius(50);

        searchIconBtn.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                backgroundCircle.setEffect(dropShadow);
            } else {
                backgroundCircle.setEffect(null);
            }
        });

        searchIconBtn.setOnMouseClicked(event -> {
            worksheetController.findWorksheet(searchTextField.getText());
            searchTextField.clear();
            // To be able to load second  next search
            Model.getInstance().setWorksheetProperty(null);

        });


    }

    @Override
    public void onWorksheetsListed(ListProperty<WorksheetProperty> worksheetPropertyList) {
        Model.getInstance().setWorksheetPropertyList(worksheetPropertyList);

        // ✅ Update the TableView with the new data
        worksheetTable.setItems(FXCollections.observableArrayList(worksheetPropertyList));

    }

    @Override
    public void onWorksheetGenerated(WorksheetProperty worksheetProperty) {

    }


    @Override
    public void onWorksheetDeleted() {

    }

    @Override
    public void onWorksheetUpdated(WorksheetProperty worksheetProperty) {

    }

}
