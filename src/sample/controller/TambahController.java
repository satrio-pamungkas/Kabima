package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class TambahController {
    @FXML
    public Button buttonTambahKedai;

    @FXML
    public Button buttonTambahProduk;

    @FXML
    public void buttonKedaiClicked(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage) buttonTambahKedai.getScene().getWindow();
        Parent newRoot = FXMLLoader.load(getClass().getResource("../resources/view/tambahKedai.fxml"));
        primaryStage.getScene().setRoot(newRoot);
    }

    @FXML
    public void buttonProdukClicked(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage) buttonTambahProduk.getScene().getWindow();
        Parent newRoot = FXMLLoader.load(getClass().getResource("../resources/view/tambahProduk.fxml"));
        primaryStage.getScene().setRoot(newRoot);
    }
}
