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
	private TreeView taskTree;
	@FXML
	private ListView listList;
	@FXML
	private MenuItem menuFileNew;
	@FXML
	private MenuItem menuFileSave;
	@FXML
	private MenuItem menuFileClose;
	@FXML
	private MenuItem menuHelpAbout;
	@FXML
	private TextField propTxtUser;
	@FXML
	private TextField propTxtName;
	@FXML
	private TextArea propTxaDescription;
	@FXML
	private CheckBox propCheckPrioritized;
	@FXML
	private CheckBox propCheckDone;

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
