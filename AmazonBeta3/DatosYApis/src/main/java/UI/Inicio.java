package UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Inicio extends Application {

	@Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/resources/MainPage.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Amazon't");
            Image icon = new Image(getClass().getResourceAsStream("/resources/logo.png"));
            primaryStage.getIcons().add(icon);
            primaryStage.setScene(scene);
            primaryStage.show(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	public static void main(String[] args) {
		launch(args);
	}


}
