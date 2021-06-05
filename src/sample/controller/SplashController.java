package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class SplashController {
    @FXML
    private Button buttonMasuk;

    @FXML
    public void masukClicked(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage) buttonMasuk.getScene().getWindow();
        Parent newRoot = FXMLLoader.load(getClass().getResource("../resources/view/main.fxml"));
        primaryStage.getScene().setRoot(newRoot);
    }

}
