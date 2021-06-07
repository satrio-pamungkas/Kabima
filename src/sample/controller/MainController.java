package sample.controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.util.StringConverter;
import sample.config.Connect;
import sample.model.Kedai;
import sample.model.Produk;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;

public class MainController {
    Connect dbConnect = new Connect();

    @FXML
    public TextField kuantitasProduk;

    @FXML
    public Button tambahKeranjang;

    @FXML
    public ComboBox<Produk> namaProduk;

    @FXML
    private SidebarController sidebarTemplateController;

    private Alert informationMsg = new Alert(Alert.AlertType.INFORMATION);
    private Alert warningMsg = new Alert(Alert.AlertType.WARNING);

    private static final SimpleDateFormat waktu = new SimpleDateFormat("MMddHHmmss");
    private ObservableList<Produk> listProduk = FXCollections.observableArrayList();

    @FXML
    public void tambahKeranjangClicked(ActionEvent actionEvent) {
        boolean isEmpty = isInputEmpty(kuantitasProduk, namaProduk);

        if (isEmpty) {
            warningMsg.setTitle("Perhatian !");
            warningMsg.setHeaderText(null);
            warningMsg.setContentText("Input masih ada yang kosong");
            warningMsg.showAndWait();
        } else {
            Produk produk = namaProduk.getSelectionModel().getSelectedItem();

            String kodeKeranjang = "KJ1" + getTimestamp();
            String kodeProduk = produk.getKodeProduk();
            String kodeKedai = produk.getKodeKedai();
            String namaProduk = produk.getNamaProduk();
            int hargaProduk = produk.getHargaProduk();
            int kuantitas = Integer.parseInt(kuantitasProduk.getText());
            int totalHarga = hargaProduk * kuantitas;

            insertToDB(kodeKeranjang, kodeProduk, kodeKedai, namaProduk, hargaProduk, kuantitas, totalHarga);

            informationMsg.setTitle("Informasi");
            informationMsg.setHeaderText(null);
            informationMsg.setContentText("Keranjang berhasil ditambahkan");
            informationMsg.showAndWait();

            kuantitasProduk.clear();

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

    public void insertToDB(String kodeKeranjang, String kodeProduk, String kodeKedai, String namaProduk,
                           int harga, int kuantitas, int total_harga) {
        String query = "INSERT INTO itemKeranjang(id_itemKeranjang, id_produk, id_kedai, nama_produk, harga, kuantitas, total_harga) " +
                "VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = dbConnect.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, kodeKeranjang);
            preparedStatement.setString(2, kodeProduk);
            preparedStatement.setString(3, kodeKedai);
            preparedStatement.setString(4, namaProduk);
            preparedStatement.setInt(5, harga);
            preparedStatement.setInt(6, kuantitas);
            preparedStatement.setInt(7, total_harga);

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

    private boolean isInputEmpty(TextField kuantitas, ComboBox combo) {
        return isTextFieldEmpty(kuantitas) || isComboBoxEmpty(combo);
    }
}
