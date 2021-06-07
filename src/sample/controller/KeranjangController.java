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
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.config.Connect;
import sample.model.Keranjang;
import sample.model.Produk;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class KeranjangController {
    Connect dbConnect = new Connect();

    @FXML
    public TextField hargaBayaran;

    @FXML
    public Button bayarKeranjang;

    @FXML
    public ComboBox<Keranjang> infoKeranjang;

    private Alert informationMsg = new Alert(Alert.AlertType.INFORMATION);
    private Alert warningMsg = new Alert(Alert.AlertType.WARNING);

    private ObservableList<Keranjang> listKeranjang = FXCollections.observableArrayList();


    @FXML
    public void bayarKeranjangClicked(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        boolean isEmpty = isInputEmpty(hargaBayaran, infoKeranjang);

        if (isEmpty) {
            warningMsg.setTitle("Perhatian !");
            warningMsg.setHeaderText(null);
            warningMsg.setContentText("Input masih ada yang kosong");
            warningMsg.showAndWait();
        } else {
            Keranjang keranjang = infoKeranjang.getSelectionModel().getSelectedItem();

            String kodeKeranjang = keranjang.getKodeKeranjang();
            int totalHarga = keranjang.getTotalHarga();
            int bayaran = Integer.parseInt(hargaBayaran.getText());

            if (bayaran >= totalHarga) {
                removeFromDB(kodeKeranjang);

                int kembalian = bayaran - totalHarga;

                informationMsg.setTitle("Informasi");
                informationMsg.setHeaderText(null);
                informationMsg.setContentText("Keranjang berhasil dibayar" + "\nKembalian = Rp." + kembalian);
                informationMsg.showAndWait();

                hargaBayaran.clear();

                Stage primaryStage = (Stage) bayarKeranjang.getScene().getWindow();
                Parent newRoot = FXMLLoader.load(getClass().getResource("../resources/view/keranjang.fxml"));
                primaryStage.getScene().setRoot(newRoot);

            } else {
                warningMsg.setTitle("Perhatian !");
                warningMsg.setHeaderText(null);
                warningMsg.setContentText("Harga bayaran terlalu kecil");
                warningMsg.showAndWait();
            }
        }
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        Connection connection = dbConnect.getConnection();
        listKeranjang = readFromDB(connection);

        infoKeranjang.setItems(listKeranjang);
        infoKeranjang.setConverter(new StringConverter<Keranjang>() {
            @Override
            public String toString(Keranjang keranjang) {
                return keranjang.getNamaProduk() + " - " + keranjang.getTotalHarga();
            }

            @Override
            public Keranjang fromString(String s) {
                return infoKeranjang.getItems().stream().filter(ap ->
                        ap.getNamaProduk().equals(s)).findFirst().orElse(null);
            }
        });
    }

    public void removeFromDB(String kodeKeranjang) {
        String query = "DELETE FROM itemKeranjang WHERE id_itemKeranjang = ?";

        try (Connection conn = dbConnect.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, kodeKeranjang);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Keranjang> readFromDB(Connection conn) throws SQLException {
        String query = "SELECT * FROM itemKeranjang";
        ObservableList<Keranjang> listData = FXCollections.observableArrayList();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet resultset = preparedStatement.executeQuery(query)) {

            while (resultset.next()) {
                Keranjang keranjang = new Keranjang(
                        resultset.getString("id_itemKeranjang"),
                        resultset.getString("id_produk"),
                        resultset.getString("id_kedai"),
                        resultset.getString("nama_produk"),
                        resultset.getInt("harga"),
                        resultset.getInt("kuantitas"),
                        resultset.getInt("total_harga")
                );
                listData.add(keranjang);
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

    private boolean isInputEmpty(TextField bayaran, ComboBox combo) {
        return isTextFieldEmpty(bayaran) || isComboBoxEmpty(combo);
    }
}
