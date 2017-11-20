import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PimpMyFridge extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Pimp My Fridge");


        //Chargement du fichier fxml
        try {
            Parent root = FXMLLoader.load(getClass().getResource("mainWindows.fxml"));

            Scene scene = new Scene(root, 1000, 500);

            stage.setScene(scene);


        }catch (IOException err){
            System.err.println("Erreur chargement du fichier .fxml : " + err.toString());
        }

        //Affichage de la fenetre
        stage.show();
    }
}