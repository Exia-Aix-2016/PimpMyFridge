import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pid.PIDController;

import java.io.IOException;
import java.net.URL;

public class PimpMyFridge extends Application {

    final String appName = "Pimp My Fridge";
    URL urlFxml;
    FXMLLoader loaderFXML;
    PIDController controller;
    Parent root;
    Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle(this.appName);

        //Chargement du fichier fxml
        try {
            this.urlFxml = getClass().getResource("mainWindows.fxml");

            this.loaderFXML = new FXMLLoader(this.urlFxml);

            this.controller = loaderFXML.getController();


            this.root = loaderFXML.load();
            this.scene = new Scene(this.root, 1000, 500);
            stage.setScene(this.scene);

        }catch (IOException err){
            System.err.println("Erreur chargement du fichier .fxml : " + err.toString());
        }

        //Affichage de la fenetre
        stage.show();
    }
}