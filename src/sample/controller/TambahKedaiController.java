package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.config.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TambahKedaiController {
    @FXML
    private TextField inputNamaKedai;

    @FXML
    private TextField inputKategoriKedai;

    @FXML
    private TextArea inputDeskripsiKedai;

    @FXML
    public void tambahKedaiButton(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        boolean isEmpty = isInputEmpty(inputNamaKedai, inputKategoriKedai, inputDeskripsiKedai);


    }

    private boolean isTextFieldEmpty(TextField textField) {
        return textField.getText().equals("");
    }

    private boolean isTextAreaEmpty(TextArea textArea) {
        return textArea.getText().equals("");
    }

    private boolean isInputEmpty(TextField namaKedai, TextField kategoriKedai, TextArea deskripsi) {
        return isTextFieldEmpty(namaKedai) || isTextFieldEmpty(kategoriKedai) || isTextAreaEmpty(deskripsi);
    }
}
