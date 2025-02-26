package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Controllers.WorksheetController;
import com.wgapp.worksheetgenerator.ModelsUI.ChoiceProperty;
import com.wgapp.worksheetgenerator.ModelsUI.Model;
import com.wgapp.worksheetgenerator.ModelsUI.QuestionProperty;
import com.wgapp.worksheetgenerator.ModelsUI.WorksheetProperty;
import com.wgapp.worksheetgenerator.Utils.Utils;
import com.wgapp.worksheetgenerator.ViewFactory.UserMenuOptions;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HistoryController implements Initializable, WorksheetController.WorksheetObserver {


    TableColumn<WorksheetProperty, Integer> idColumn = new TableColumn<>("ID");
    TableColumn<WorksheetProperty, String> mainSubColumn = new TableColumn<>("MAIN SUBJECT");
    TableColumn<WorksheetProperty, String> subSubColumn = new TableColumn<>("SUB SUBJECT");
    TableColumn<WorksheetProperty, String> diffLevelColumn = new TableColumn<>("LEVEL");
    TableColumn<WorksheetProperty, String> title = new TableColumn<>("PASSAGE TITLE");
    TableColumn<WorksheetProperty, Void> delete = new TableColumn<>("");
    TableColumn<WorksheetProperty, Void> open = new TableColumn<>("");

    private final WorksheetController worksheetController = new WorksheetController();

    private static final int ROWS_PER_PAGE = 12; // Number of rows per page
    public Pagination pagination;
    private final TableView<WorksheetProperty> worksheetTable = createTable();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Observer pattern
        worksheetController.addObserver(this);

        // For list
        worksheetController.listWorksheets(Model.getInstance().getUserProperty());

        Model.getInstance().getWorksheetPropertyList().addListener((ListChangeListener<WorksheetProperty>) change -> {
            pagination.setPageFactory(this::createPage);
            pagination.setPageCount((int) Math.ceil((double) Model.getInstance().getWorksheetPropertyList().size() / ROWS_PER_PAGE));
        });


        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            delete.setCellFactory(column -> new TableCell<WorksheetProperty, Void>() {
                private final Button deleteButton = new Button();

                {
                    // Load delete icon
                    Image deleteImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/delete.png")));
                    ImageView deleteIcon = new ImageView(deleteImage);
                    deleteIcon.setFitWidth(25);
                    deleteIcon.setFitHeight(25);

                    deleteButton.setGraphic(deleteIcon);
                    deleteButton.setStyle("-fx-background-color: transparent;");
                    setGraphic(deleteButton);

                    // Handle Delete Action
                    deleteButton.setOnAction(event -> {
                        BooleanProperty hasConfirmed = Utils.notifyUser("Are you sure to delete this worksheet?", "Delete", "Warning", Alert.AlertType.CONFIRMATION);
                        WorksheetProperty worksheet = getTableView().getItems().get(getIndex());
                        worksheet.setUserProperty(Model.getInstance().getUserProperty());
                        if (hasConfirmed.get()) {
                            worksheetController.deleteWorksheet(worksheet);
                            Model.getInstance().getWorksheetPropertyList().remove(worksheet); // Updating UI
                        }

                    });
                }


                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                    }
                }
            });


        });
        // ✅ Set Open Button inside the "open" column
        open.setCellFactory(column -> new TableCell<WorksheetProperty, Void>() {
            private final Button openButton = new Button();

            {
                // Load delete icon
                Image worksheetImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/worksheet.png")));
                ImageView openIcon = new ImageView(worksheetImage);
                openIcon.setFitWidth(25);
                openIcon.setFitHeight(25);

                openButton.setGraphic(openIcon);
                openButton.setStyle("-fx-background-color: transparent;");
                setGraphic(openButton);

                // Handle Delete Action
                openButton.setOnAction(event -> {
                    WorksheetProperty worksheet = getTableView().getItems().get(getIndex());

                    worksheet.setUserProperty(Model.getInstance().getUserProperty());

                    worksheetController.findWorksheet(worksheet.getId());
                    Model.getInstance().getViewFactory().getUserSelectMenuView().set(UserMenuOptions.WORKSHEET);

                });
            }


            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(openButton);
                }
            }
        });
    }


    private TableView<WorksheetProperty> createTable() {

        TableView<WorksheetProperty> table = new TableView<>();
        table.setMinWidth(860);
        table.setMaxWidth(950);


        // Add a column to display row index
        TableColumn<WorksheetProperty, String> indexColumn = new TableColumn<>("NO");
        indexColumn.setCellValueFactory((TableColumn.CellDataFeatures<WorksheetProperty, String> param) -> {
            int index = param.getTableView().getItems().indexOf(param.getValue());
            return new SimpleStringProperty(String.valueOf(index + 1)); // Display 1-based index
        });

        // Set up the columns of the TableView to bind to the properties of WorksheetProperty;
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        mainSubColumn.setCellValueFactory(cellData -> cellData.getValue().mainSubjectProperty().asString());
        subSubColumn.setCellValueFactory(cellData -> cellData.getValue().subSubjectProperty().asString());
        diffLevelColumn.setCellValueFactory(cellData -> cellData.getValue().diffLevelProperty().asString());
        title.setCellValueFactory(cellData -> {
            if (cellData.getValue() == null || cellData.getValue().passageProperty().passageTitleProperty().getValue() == null) {
                cellData.getValue().passageProperty().setPassageTitle("---");
                return cellData.getValue().passageProperty().passageTitleProperty();
            }
            return cellData.getValue().passageProperty().passageTitleProperty();
        });


        // ✅ Set Delete Button inside the "delete" column
        delete.setCellFactory(column -> new TableCell<WorksheetProperty, Void>() {
            private final Button deleteButton = new Button();

            {
                // Load delete icon
                Image deleteImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/delete.png")));
                ImageView deleteIcon = new ImageView(deleteImage);
                deleteIcon.setFitWidth(25);
                deleteIcon.setFitHeight(25);

                deleteButton.setGraphic(deleteIcon);
                deleteButton.setStyle("-fx-background-color: transparent;");
                setGraphic(deleteButton);

                // Handle Delete Action
                deleteButton.setOnAction(event -> {
                    BooleanProperty hasConfirmed = Utils.notifyUser("Are you sure to delete this worksheet?", "Delete", "Warning", Alert.AlertType.CONFIRMATION);
                    WorksheetProperty worksheet = getTableView().getItems().get(getIndex());
                    worksheet.setUserProperty(Model.getInstance().getUserProperty());
                    if (hasConfirmed.get()) {
                        worksheetController.deleteWorksheet(worksheet);
                        Model.getInstance().getWorksheetPropertyList().remove(worksheet); // Updating UI
                    }

                });
            }


            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        // ✅ Set Open Button inside the "open" column
        open.setCellFactory(column -> new TableCell<WorksheetProperty, Void>() {
            private final Button openButton = new Button();

            {
                // Load delete icon
                Image worksheetImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/worksheet.png")));
                ImageView openIcon = new ImageView(worksheetImage);
                openIcon.setFitWidth(25);
                openIcon.setFitHeight(25);

                openButton.setGraphic(openIcon);
                openButton.setStyle("-fx-background-color: transparent;");
                setGraphic(openButton);

                // Handle Delete Action
                openButton.setOnAction(event -> {
                    WorksheetProperty worksheet = getTableView().getItems().get(getIndex());

                    worksheet.setUserProperty(Model.getInstance().getUserProperty());

                    worksheetController.findWorksheet(worksheet.getId());
                    Model.getInstance().getViewFactory().getUserSelectMenuView().set(UserMenuOptions.WORKSHEET);

                });
            }


            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(openButton);
                }
            }
        });


        indexColumn.setPrefWidth(70);
        idColumn.setPrefWidth(70);
        mainSubColumn.setPrefWidth(170);
        subSubColumn.setPrefWidth(170);
        diffLevelColumn.setPrefWidth(170);
        title.setPrefWidth(200);
        delete.setPrefWidth(47);
        open.setPrefWidth(47);

        indexColumn.setStyle("-fx-alignment: CENTER;");
        idColumn.setStyle("-fx-alignment: CENTER;");
        mainSubColumn.setStyle("-fx-alignment: CENTER;");
        subSubColumn.setStyle("-fx-alignment: CENTER;");
        diffLevelColumn.setStyle("-fx-alignment: CENTER;");
        title.setStyle("-fx-alignment: CENTER;");
        table.getColumns().addAll(indexColumn, idColumn, mainSubColumn, subSubColumn, diffLevelColumn, title, delete, open);

        return table;
    }


    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, Model.getInstance().getWorksheetPropertyList().size());


        worksheetTable.setItems(FXCollections.observableArrayList(
                Model.getInstance().getWorksheetPropertyList().subList(fromIndex, toIndex)
        ));
        return worksheetTable;
    }


    @Override
    public void onWorksheetsListed(ListProperty<WorksheetProperty> worksheetPropertyList) {
        for (WorksheetProperty worksheetProperty : worksheetPropertyList) {
            Model.getInstance().getWorksheetPropertyList().add(worksheetProperty);
        }
    }


    @Override
    public void onWorksheetGenerated(WorksheetProperty worksheetProperty) {

    }


    @Override
    public void onWorksheetDeleted() {

    }

    @Override
    public void onWorksheetFound(WorksheetProperty worksheetProperty) {
        Model.getInstance().setWorksheetProperty(worksheetProperty);

    }

}
