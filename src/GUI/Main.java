package GUI;

import TODO.ListController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Main extends Application {

	private static Stage primaryStage;
	private ListController listController = ListController.getListController();

	@Override
    public void start(Stage primaryStage) throws Exception{
		this.primaryStage = primaryStage;
		switchScene("login.fxml");
        primaryStage.show();

	    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent windowEvent) {
			    //Wenn das Fenster geschlossen wird.
			    listController.marshallData();
			    System.out.printf("test");
		    }
	    });
    }

	public static void switchScene(String viewName) throws IOException {
		Parent root = FXMLLoader.load(Main.class.getResource(viewName));
		if ("login.fxml".equals(viewName)) {
			primaryStage.setTitle("ToDo Login");
			primaryStage.setScene(new Scene(root, 300, 275));
			primaryStage.setMinHeight(275);
			primaryStage.setMinWidth(300);
		} else {
			primaryStage.setTitle("ToDo-List");
			primaryStage.setScene(new Scene(root, 1000, 700));
			primaryStage.setMinHeight(350);
			primaryStage.setMinWidth(500);
			primaryStage.setFullScreen(true);
		}
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
        launch(args);
    }
}
