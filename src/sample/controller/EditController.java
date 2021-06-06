package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class EditController {
    @FXML
    public Button buttonEditKedai;

    @FXML
    public Button buttonEditProduk;

    @FXML
    public void buttonKedaiClicked(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage) buttonEditKedai.getScene().getWindow();
        Parent newRoot = FXMLLoader.load(getClass().getResource("../resources/view/editKedai.fxml"));
        primaryStage.getScene().setRoot(newRoot);
    }

    @FXML
    public void buttonProdukClicked(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage) buttonEditProduk.getScene().getWindow();
        Parent newRoot = FXMLLoader.load(getClass().getResource("../resources/view/editProduk.fxml"));
        primaryStage.getScene().setRoot(newRoot);
    }
}
