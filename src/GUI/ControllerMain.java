package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable {
	//Main
	@FXML
	TreeView taskTree;
	@FXML
	ListView listList;
	@FXML
	MenuItem menuFileNew;
	@FXML
	MenuItem menuFileSave;
	@FXML
	MenuItem menuFileClose;
	@FXML
	MenuItem menuHelpAbout;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		menuFileClose.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				Main.getPrimaryStage().close();
			}
		});
	}
}
