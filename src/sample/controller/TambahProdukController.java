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
import javafx.util.StringConverter;
import sample.config.Connect;
import sample.model.Kedai;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public class TambahProdukController {
    Connect dbConnect = new Connect();

    @FXML
    public ButtonBar backButton;

    @FXML
    public Button backButtonText;

    @FXML
    public TextField inputNamaProduk;

    @FXML
    public TextField inputKategoriProduk;

    @FXML
    public TextField inputHargaProduk;

    @FXML
    public ComboBox<Kedai> inputNamaKedai;

    private Alert informationMsg = new Alert(Alert.AlertType.INFORMATION);
    private Alert warningMsg = new Alert(Alert.AlertType.WARNING);

    private static final SimpleDateFormat waktu = new SimpleDateFormat("MMddHHmmss");
    private ObservableList<Kedai> listInformation = FXCollections.observableArrayList();

    @FXML
    public void backClicked(MouseEvent mouseEvent) throws IOException {
        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        Parent newRoot = FXMLLoader.load(getClass().getResource("../resources/view/tambah.fxml"));
        primaryStage.getScene().setRoot(newRoot);
    }

    @FXML
    public void backTextClicked(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage) backButtonText.getScene().getWindow();
        Parent newRoot = FXMLLoader.load(getClass().getResource("../resources/view/tambah.fxml"));
        primaryStage.getScene().setRoot(newRoot);
    }

    @FXML
    public void tambahProdukButton(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        boolean isEmpty = isInputEmpty(inputNamaProduk, inputKategoriProduk, inputHargaProduk, inputNamaKedai);

        if (isEmpty) {
            warningMsg.setTitle("Perhatian !");
            warningMsg.setHeaderText(null);
            warningMsg.setContentText("Input masih ada yang kosong");
            warningMsg.showAndWait();
        } else {
            Kedai kedai = inputNamaKedai.getSelectionModel().getSelectedItem();

            String kodeProduk = "PD1" + getTimestamp();
            String kodeKedai = kedai.getKodeKedai();
            String namaProduk = inputNamaProduk.getText();
            int hargaProduk = Integer.parseInt(inputHargaProduk.getText());
            String kategoriProduk = inputKategoriProduk.getText();

            insertToDB(kodeProduk, kodeKedai, namaProduk, hargaProduk, kategoriProduk);

            informationMsg.setTitle("Informasi");
            informationMsg.setHeaderText(null);
            informationMsg.setContentText("Produk berhasil ditambahkan");
            informationMsg.showAndWait();

            inputNamaProduk.clear();
            inputHargaProduk.clear();
            inputKategoriProduk.clear();

        }
//        Kedai kedai = inputNamaKedai.getSelectionModel().getSelectedItem();
//        System.out.println(kedai.getKodeKedai());

    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        Connection connection = dbConnect.getConnection();
        listInformation = readFromDB(connection);

        inputNamaKedai.setItems(listInformation);
        inputNamaKedai.setConverter(new StringConverter<Kedai>() {
            @Override
            public String toString(Kedai kedai) {
                return kedai.getNamaKedai();
            }

            @Override
            public Kedai fromString(String s) {
                return inputNamaKedai.getItems().stream().filter(ap ->
                        ap.getNamaKedai().equals(s)).findFirst().orElse(null);
            }
        });
    }

    public void insertToDB(String kodeProduk, String kodeKedai, String nama, int harga, String kategori) {
        String query = "INSERT INTO produk(id_produk, id_kedai, nama_produk, harga, kategori) VALUES(?,?,?,?,?)";

        try (Connection conn = dbConnect.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, kodeProduk);
            preparedStatement.setString(2, kodeKedai);
            preparedStatement.setString(3, nama);
            preparedStatement.setInt(4, harga);
            preparedStatement.setString(5, kategori);

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

    private String getTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        return waktu.format(timestamp);
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
