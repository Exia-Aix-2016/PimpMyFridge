import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PimpMyFridge extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("mainWindows.fxml"));

        Scene scene = new Scene(root, 300, 275);

        stage.setScene(scene);
        stage.setTitle("Pimp My Fridge");
        stage.show();
    }
}