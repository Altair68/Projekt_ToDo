package GUI;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLogin implements Initializable {
	//Login + Register
	@FXML
	Button loginButton;
	@FXML
	Button registerButton;
	@FXML
	TextField logUsername;
	@FXML
	PasswordField logPassword;
	@FXML
	TextField regUsername;
	@FXML
	PasswordField regPassword;
	@FXML
	PasswordField regPasswordRe;

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
		loginButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				try {
					Main.switchScene("main.fxml");
				} catch (IOException e) {
					e.printStackTrace();
				}

				System.out.println("Test");
			}
		});


	}
}
