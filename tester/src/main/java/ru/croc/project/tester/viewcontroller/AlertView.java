package ru.croc.project.tester.viewcontroller;

import javafx.scene.control.Alert;

public class AlertView {
    public void showAlertWithHeaderText(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Попытка входа");
        alert.setHeaderText("Не удалось!");
        alert.setContentText(text);
        alert.showAndWait();
    }
}
