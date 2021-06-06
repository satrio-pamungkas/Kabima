package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class EditProdukCOntroller {
    @FXML
    public ButtonBar backButton;

    @FXML
    public Button backButtonText;

    @FXML
    public void backClicked(MouseEvent mouseEvent) throws IOException {
        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        Parent newRoot = FXMLLoader.load(getClass().getResource("../resources/view/edit.fxml"));
        primaryStage.getScene().setRoot(newRoot);
    }

    @FXML
    public void backTextClicked(MouseEvent mouseEvent) throws IOException {
        Stage primaryStage = (Stage) backButtonText.getScene().getWindow();
        Parent newRoot = FXMLLoader.load(getClass().getResource("../resources/view/edit.fxml"));
        primaryStage.getScene().setRoot(newRoot);
    }
}
