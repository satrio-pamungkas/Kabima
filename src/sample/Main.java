package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("resources/view/splash.fxml"));
        primaryStage.setTitle("Aplikasi KaBiMa");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("resources/img/logo.png"))) ;
        primaryStage.setScene(new Scene(root, 810, 530));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
