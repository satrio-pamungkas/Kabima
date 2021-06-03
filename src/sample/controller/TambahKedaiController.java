package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.config.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.text.SimpleDateFormat;

public class TambahKedaiController {
    Connect dbConnect = new Connect();

    @FXML
    private TextField inputNamaKedai;

    @FXML
    private TextField inputKategoriKedai;

    @FXML
    private TextArea inputDeskripsiKedai;

    private Alert informationMsg = new Alert(Alert.AlertType.INFORMATION);
    private Alert warningMsg = new Alert(Alert.AlertType.WARNING);

    private static final SimpleDateFormat waktu = new SimpleDateFormat("MMddHHmmss");

    @FXML
    public void tambahKedaiButton(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        boolean isEmpty = isInputEmpty(inputNamaKedai, inputKategoriKedai, inputDeskripsiKedai);

        if (isEmpty) {
            warningMsg.setTitle("Perhatian !");
            warningMsg.setHeaderText(null);
            warningMsg.setContentText("Input masih ada yang kosong");
            warningMsg.showAndWait();
        } else {
            String kodeKedai = "KD1" + getTimestamp();
            String namaKedai = inputNamaKedai.getText();
            String kategoriKedai = inputKategoriKedai.getText();
            String deskripsiKedai = inputDeskripsiKedai.getText();

            insertToDB(kodeKedai, namaKedai, kategoriKedai, deskripsiKedai);

            informationMsg.setTitle("Informasi");
            informationMsg.setHeaderText(null);
            informationMsg.setContentText("Kedai berhasil ditambahkan");
            informationMsg.showAndWait();

            inputNamaKedai.clear();
            inputKategoriKedai.clear();
            inputDeskripsiKedai.clear();

        }

    }

    private void insertToDB(String kode, String nama, String kategori, String deskripsi) {
        String query = "INSERT INTO Kedai(id_kedai, nama_kedai, kategori, deskripsi) VALUES(?,?,?,?)";

        try (Connection conn = dbConnect.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, kode);
            preparedStatement.setString(2, nama);
            preparedStatement.setString(3, kategori);
            preparedStatement.setString(4, deskripsi);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private String getTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        return waktu.format(timestamp);
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
