package com.wgapp.worksheetgenerator.Components;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class CustomDropdownMenu<T> extends VBox {
    private final Button mainButton;
    private final VBox dropdownContent;
    private boolean isExpanded = false;
    private EventHandler<ActionEvent> onSelectionChanged;

    public CustomDropdownMenu(String mainButtonText, T[] menuItems) {
        dropdownContent = new VBox();
        dropdownContent.getStyleClass().add("dropdown-content");
        dropdownContent.setVisible(false);


        mainButton = new Button(mainButtonText + " ▼");
        mainButton.getStyleClass().add("dropdown-button");

        if (menuItems != null) {
            populateDropdown(menuItems);
        }

        mainButton.setOnAction(e -> toggleDropdown());

        this.getChildren().addAll(mainButton, dropdownContent);
        this.getStyleClass().add("dropdown-container");
    }

    public CustomDropdownMenu(String mainButtonText) {
        this(mainButtonText, null);
    }

    public void setDropdownContent(T[] menuItems) {
        dropdownContent.getChildren().clear();  // Clear existing items first
        populateDropdown(menuItems);
    }

    private void populateDropdown(T[] menuItems) {
        for (T item : menuItems) {
            Button menuItem = createMenuItem(item.toString());
            menuItem.setOnAction(e -> {
                mainButton.setText(item.toString());
                toggleDropdown();
                if (onSelectionChanged != null) {
                    onSelectionChanged.handle(new ActionEvent(item, null));
                }
            });
            dropdownContent.getChildren().add(menuItem);
        }
    }

    private Button createMenuItem(String text) {
        Button item = new Button(text);
        item.getStyleClass().add("menu-item");
        return item;
    }

    private void toggleDropdown() {
        isExpanded = !isExpanded;
        dropdownContent.setVisible(isExpanded);

    }

    public void closeDropdown() {
        isExpanded = false;
        dropdownContent.setVisible(false);
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public Button getMainButton() {
        return mainButton;
    }

    public void setOnSelectionChanged(EventHandler<ActionEvent> handler) {
        this.onSelectionChanged = handler;
    }

    public String getSelectedValue() {
        return mainButton.getText();
    }

    public void setMainButtonText(String text) {
        mainButton.setText(text);
    }
}