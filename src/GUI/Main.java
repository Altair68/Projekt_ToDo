package GUI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("ToDo Login");
        primaryStage.setScene(new Scene(root, 300, 275));
	    primaryStage.setMinHeight(275);
	    primaryStage.setMinWidth(300);
        primaryStage.show();

	    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent windowEvent) {
			    //Wenn das Fenster geschlossen wird.
			    System.out.println("Speichern");
		    }
	    });
    }

	public void hideLoginShowMain() {

	}

    public static void main(String[] args) {
        launch(args);
    }
}
