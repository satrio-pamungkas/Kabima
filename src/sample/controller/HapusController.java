package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.config.Connect;
import sample.model.Kedai;
import sample.model.Produk;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HapusController {
    Connect dbConnect = new Connect();

    @FXML
    public ComboBox<Kedai> inputNamaKedai;

    @FXML
    public ComboBox<Produk> inputNamaProduk;

    @FXML
    public Button hapusKedai;

    @FXML
    public Button hapusProduk;

    private Alert informationMsg = new Alert(Alert.AlertType.INFORMATION);
    private Alert warningMsg = new Alert(Alert.AlertType.WARNING);

    private ObservableList<Kedai> listKedaiQuery = FXCollections.observableArrayList();
    private ObservableList<Produk> listProdukQuery = FXCollections.observableArrayList();

    @FXML
    public void hapusKedaiClicked(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        boolean isEmpty = isComboBoxEmpty(inputNamaKedai);

        if (isEmpty) {
            warningMsg.setTitle("Perhatian !");
            warningMsg.setHeaderText(null);
            warningMsg.setContentText("Input masih ada yang kosong");
            warningMsg.showAndWait();
        } else {
            Kedai kedai = inputNamaKedai.getSelectionModel().getSelectedItem();

            String kodeKedai = kedai.getKodeKedai();

            removeKedaiFromDB(kodeKedai);

            informationMsg.setTitle("Informasi");
            informationMsg.setHeaderText(null);
            informationMsg.setContentText("Kedai berhasil dihapus");
            informationMsg.showAndWait();

            Stage primaryStage = (Stage) hapusKedai.getScene().getWindow();
            Parent newRoot = FXMLLoader.load(getClass().getResource("../resources/view/hapus.fxml"));
            primaryStage.getScene().setRoot(newRoot);

        }
    }

    @FXML
    public void hapusProdukClicked(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        boolean isEmpty = isComboBoxEmpty(inputNamaProduk);

        if (isEmpty) {
            warningMsg.setTitle("Perhatian !");
            warningMsg.setHeaderText(null);
            warningMsg.setContentText("Input masih ada yang kosong");
            warningMsg.showAndWait();
        } else {
            Produk produk = inputNamaProduk.getSelectionModel().getSelectedItem();

            String kodeProduk = produk.getKodeProduk();

            removeProdukFromDB(kodeProduk);

            informationMsg.setTitle("Informasi");
            informationMsg.setHeaderText(null);
            informationMsg.setContentText("Produk berhasil dihapus");
            informationMsg.showAndWait();

            Stage primaryStage = (Stage) hapusProduk.getScene().getWindow();
            Parent newRoot = FXMLLoader.load(getClass().getResource("../resources/view/hapus.fxml"));
            primaryStage.getScene().setRoot(newRoot);

        }
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        Connection connection = dbConnect.getConnection();

        listKedaiQuery = readKedai(connection);
        listProdukQuery = readProduk(connection);

        inputNamaKedai.setItems(listKedaiQuery);
        inputNamaKedai.setConverter(new StringConverter<Kedai>() {
            @Override
            public String toString(Kedai kedai) {
                return kedai.getNamaKedai() + " - " + kedai.getKodeKedai();
            }

            @Override
            public Kedai fromString(String s) {
                return inputNamaKedai.getItems().stream().filter(ap ->
                        ap.getNamaKedai().equals(s)).findFirst().orElse(null);
            }
        });

        inputNamaProduk.setItems(listProdukQuery);
        inputNamaProduk.setConverter(new StringConverter<Produk>() {
            @Override
            public String toString(Produk produk) {
                return produk.getNamaProduk() + " - " + produk.getKodeKedai();
            }

            @Override
            public Produk fromString(String s) {
                return inputNamaProduk.getItems().stream().filter(ap ->
                        ap.getNamaProduk().equals(s)).findFirst().orElse(null);
            }
        });

    }

    public void removeKedaiFromDB(String kodeKedai) {
        String query = "DELETE FROM kedai WHERE id_kedai = ?";

        try (Connection conn = dbConnect.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, kodeKedai);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeProdukFromDB(String kodeProduk) {
        String query = "DELETE FROM produk WHERE id_produk = ?";

        try (Connection conn = dbConnect.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, kodeProduk);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Kedai> readKedai(Connection conn) throws SQLException {
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

    public ObservableList<Produk> readProduk(Connection conn) throws SQLException {
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

    private boolean isComboBoxEmpty(ComboBox comboBox) {
        return comboBox.getSelectionModel().isEmpty();
    }
}
