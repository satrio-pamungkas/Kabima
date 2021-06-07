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
import sample.model.Kedai;
import sample.model.Produk;
import sample.config.Connect;

public class EditProdukController {
    Connect dbConnect = new Connect();

    @FXML
    public ButtonBar backButton;

    @FXML
    public Button backButtonText;

    @FXML
    public TextField inputKategoriProduk;

    @FXML
    public TextField inputNamaProduk;

    @FXML
    public TextField inputHargaProduk;

    @FXML
    public ComboBox<Produk> namaProduk;

    @FXML
    public Button editProdukButton;

    private Alert informationMsg = new Alert(Alert.AlertType.INFORMATION);
    private Alert warningMsg = new Alert(Alert.AlertType.WARNING);
    private ObservableList<Produk> listProduk = FXCollections.observableArrayList();

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
    public void editProdukClicked(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        boolean isEmpty = isInputEmpty(inputNamaProduk, inputKategoriProduk, inputHargaProduk, namaProduk);

        if (isEmpty) {
            warningMsg.setTitle("Perhatian !");
            warningMsg.setHeaderText(null);
            warningMsg.setContentText("Input masih ada yang kosong");
            warningMsg.showAndWait();
        } else {
            Produk produk = namaProduk.getSelectionModel().getSelectedItem();

            String kodeProduk = produk.getKodeProduk();
            String namaProduk = inputNamaProduk.getText();
            int hargaProduk = Integer.parseInt(inputHargaProduk.getText());
            String kategoriProduk = inputKategoriProduk.getText();

            updateToDB(kodeProduk, namaProduk, hargaProduk, kategoriProduk);

            informationMsg.setTitle("Informasi");
            informationMsg.setHeaderText(null);
            informationMsg.setContentText("Produk berhasil diperbarui");
            informationMsg.showAndWait();

            inputNamaProduk.clear();
            inputHargaProduk.clear();
            inputKategoriProduk.clear();

            Stage primaryStage = (Stage) editProdukButton.getScene().getWindow();
            Parent newRoot = FXMLLoader.load(getClass().getResource("../resources/view/editProduk.fxml"));
            primaryStage.getScene().setRoot(newRoot);

        }
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        Connection connection = dbConnect.getConnection();
        listProduk = readFromDB(connection);

        namaProduk.setItems(listProduk);
        namaProduk.setConverter(new StringConverter<Produk>() {
            @Override
            public String toString(Produk produk) {
                return produk.getNamaProduk();
            }

            @Override
            public Produk fromString(String s) {
                return namaProduk.getItems().stream().filter(ap ->
                        ap.getNamaProduk().equals(s)).findFirst().orElse(null);
            }
        });
    }

    public void updateToDB(String kodeProduk, String namaProduk, int hargaProduk, String kategoriProduk) {
        String query = "UPDATE produk SET nama_produk = ?, harga = ?, kategori =  ? " +
                "WHERE id_produk = ?";

        try (Connection conn = dbConnect.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, namaProduk);
            preparedStatement.setInt(2, hargaProduk);
            preparedStatement.setString(3, kategoriProduk);
            preparedStatement.setString(4, kodeProduk);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Produk> readFromDB(Connection conn) throws SQLException {
        String query = "SELECT * FROM produk";
        ObservableList<Produk> listData = FXCollections.observableArrayList();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet resultset = preparedStatement.executeQuery(query)) {

            while (resultset.next()) {
                Produk produk = new Produk(
                        resultset.getString("id_produk"),
                        resultset.getString("id_kedai"),
                        resultset.getString("nama_produk"),
                        resultset.getInt("harga"),
                        resultset.getString("kategori")
                );
                listData.add(produk);
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

    private boolean isComboBoxEmpty(ComboBox comboBox) {
        return comboBox.getSelectionModel().isEmpty();
    }

    private boolean isInputEmpty(TextField namaProduk, TextField kategoriProduk, TextField hargaProduk, ComboBox combo) {
        return isTextFieldEmpty(namaProduk) || isTextFieldEmpty(kategoriProduk) || isTextFieldEmpty(hargaProduk)
                || isComboBoxEmpty(combo);
    }
}
