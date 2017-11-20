import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pid.PIDController;

import java.io.IOException;
import java.net.URL;

public class PimpMyFridge extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Pimp My Fridge");



        //Chargement du fichier fxml
        try {


            final URL fxmlURL = getClass().getResource("mainWindows.fxml");
            final FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);

            final PIDController controler = fxmlLoader.getController();


            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 1000, 500);

            stage.setScene(scene);

            controler.name();


        }catch (IOException err){
            System.err.println("Erreur chargement du fichier .fxml : " + err.toString());
        }

        //Affichage de la fenetre
        stage.show();
    }
}