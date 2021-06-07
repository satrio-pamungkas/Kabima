package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.util.StringConverter;
import sample.config.Connect;

import sample.model.Kedai;

public class EditKedaiController {
    Connect dbConnect = new Connect();

    @FXML
    public ButtonBar backButton;

    @FXML
    public Button backButtonText;

    @FXML
    public TextField inputNamaKedai;

    @FXML
    public TextField inputKategoriKedai;

    @FXML
    public TextArea inputDeskripsiKedai;

    @FXML
    public Button editKedaiButton;

    @FXML
    public ComboBox<Kedai> namaKedai;

    private Alert informationMsg = new Alert(Alert.AlertType.INFORMATION);
    private Alert warningMsg = new Alert(Alert.AlertType.WARNING);
    private ObservableList<Kedai> listKedai = FXCollections.observableArrayList();

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

    @FXML
    public void editKedaiClicked(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        boolean isEmpty = isInputEmpty(inputNamaKedai, inputKategoriKedai, inputDeskripsiKedai, namaKedai);

        if (isEmpty) {
            warningMsg.setTitle("Perhatian !");
            warningMsg.setHeaderText(null);
            warningMsg.setContentText("Input masih ada yang kosong");
            warningMsg.showAndWait();
        } else {
            Kedai kedai = namaKedai.getSelectionModel().getSelectedItem();

            String kodeKedai = kedai.getKodeKedai();
            String namaKedai = inputNamaKedai.getText();
            String kategoriKedai = inputKategoriKedai.getText();
            String deskripsiKedai = inputDeskripsiKedai.getText();

            updateToDB(kodeKedai, namaKedai, kategoriKedai, deskripsiKedai);

            informationMsg.setTitle("Informasi");
            informationMsg.setHeaderText(null);
            informationMsg.setContentText("Kedai berhasil diperbarui");
            informationMsg.showAndWait();

            inputNamaKedai.clear();
            inputKategoriKedai.clear();
            inputDeskripsiKedai.clear();

            Stage primaryStage = (Stage) editKedaiButton.getScene().getWindow();
            Parent newRoot = FXMLLoader.load(getClass().getResource("../resources/view/editKedai.fxml"));
            primaryStage.getScene().setRoot(newRoot);

        }
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        Connection connection = dbConnect.getConnection();
        listKedai = readFromDB(connection);

        namaKedai.setItems(listKedai);
        namaKedai.setConverter(new StringConverter<Kedai>() {
            @Override
            public String toString(Kedai kedai) {
                return kedai.getNamaKedai();
            }

            @Override
            public Kedai fromString(String s) {
                return namaKedai.getItems().stream().filter(ap ->
                        ap.getNamaKedai().equals(s)).findFirst().orElse(null);
            }
        });
    }

    public void updateToDB(String kodeKedai, String namaKedai, String kategoriKedai, String deskripsiKedai) {
        String query = "UPDATE kedai SET nama_kedai = ?, kategori = ?, deskripsi =  ? " +
                "WHERE id_kedai = ?";

        try (Connection conn = dbConnect.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, namaKedai);
            preparedStatement.setString(2, kategoriKedai);
            preparedStatement.setString(3, deskripsiKedai);
            preparedStatement.setString(4, kodeKedai);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Kedai> readFromDB(Connection conn) throws SQLException {
        String query = "SELECT * FROM kedai";
        ObservableList<Kedai> listData = FXCollections.observableArrayList();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet resultset = preparedStatement.executeQuery(query)) {

            while (resultset.next()) {
                Kedai kedai = new Kedai(
                        resultset.getString("id_kedai"),
                        resultset.getString("nama_kedai"),
                        resultset.getString("kategori"),
                        resultset.getString("deskripsi")
                );
                listData.add(kedai);
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    private boolean isTextFieldEmpty(TextField textField) {
        return textField.getText().equals("");
    }

    private boolean isTextAreaEmpty(TextArea textArea) {
        return textArea.getText().equals("");
    }

    private boolean isComboBoxEmpty(ComboBox comboBox) {
        return comboBox.getSelectionModel().isEmpty();
    }

    private boolean isInputEmpty(TextField namaKedai, TextField kategoriKedai, TextArea deskripsiKedai, ComboBox combo) {
        return isTextFieldEmpty(namaKedai) || isTextFieldEmpty(kategoriKedai) || isTextAreaEmpty(deskripsiKedai)
                || isComboBoxEmpty(combo);
    }
}
