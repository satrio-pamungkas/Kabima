package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SidebarController {
    @FXML
    public ButtonBar buttonBeranda;
    
    @FXML
    public ButtonBar buttonTambah;

    @FXML
    public ButtonBar buttonHapus;

    @FXML
    public ButtonBar buttonEdit;

    @FXML
    public void buttonBerandaClicked(MouseEvent mouseEvent) throws IOException {
        Stage primaryStage = (Stage) buttonBeranda.getScene().getWindow();
        Parent newRoot = FXMLLoader.load(getClass().getResource("../resources/view/main.fxml"));
        primaryStage.getScene().setRoot(newRoot);
    }

    @FXML
    public void buttonTambahClicked(MouseEvent mouseEvent) throws IOException {
        Stage primaryStage = (Stage) buttonTambah.getScene().getWindow();
        Parent newRoot = FXMLLoader.load(getClass().getResource("../resources/view/tambah.fxml"));
        primaryStage.getScene().setRoot(newRoot);
    }

    @FXML
    public void buttonHapusClicked(MouseEvent mouseEvent) throws IOException {
        Stage primaryStage = (Stage) buttonHapus.getScene().getWindow();
        Parent newRoot = FXMLLoader.load(getClass().getResource("../resources/view/hapus.fxml"));
        primaryStage.getScene().setRoot(newRoot);
    }

    @FXML
    public void buttonEditClicked(MouseEvent mouseEvent) throws IOException {
        Stage primaryStage = (Stage) buttonEdit.getScene().getWindow();
        Parent newRoot = FXMLLoader.load(getClass().getResource("../resources/view/edit.fxml"));
        primaryStage.getScene().setRoot(newRoot);
    }
}
