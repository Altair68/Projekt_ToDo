package GUI;

import TODO.ListController;
import TODO.User;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
	Label logLabelInfo;
	@FXML
	TextField regUsername;
	@FXML
	PasswordField regPassword;
	@FXML
	PasswordField regPasswordRe;
	@FXML
	Label regLabelInfo;
	@FXML
	AnchorPane logAnchor;
	@FXML
	AnchorPane regAnchor;

	//Variablen
	ListController listController = ListController.getListController();


	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		listController.unmarshallData();

		loginButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				loginButtonAction();
			}
		});

		registerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				registerButtonAction();
			}
		});

		logAnchor.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.ENTER) {
					loginButtonAction();
				}
			}
		});

		regAnchor.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.ENTER) {
					registerButtonAction();
				}
			}
		});
	}

	private void registerButtonAction() {
		String theUsername = regUsername.getText();
		String thePassword = regPassword.getText();
		String thePasswordRe = regPasswordRe.getText();

		if (thePassword.equals(thePasswordRe)) {
			if (listController.addUser(new User(theUsername, thePassword))) {
				regLabelInfo.setVisible(true);
				regLabelInfo.setText("Die Registrierung war erfolgreich!");
			} else {
				regLabelInfo.setVisible(true);
				regLabelInfo.setText("Die Registrierung war nicht erfolgreich!");
			}
		} else {
			regLabelInfo.setVisible(true);
			regLabelInfo.setText("Die Passwörter stimmen nicht überein!");
		}
	}

	private void loginButtonAction() {
		String theUsername = logUsername.getText();
		String thePassword = logPassword.getText();
		User loginUser = new User(theUsername, thePassword);
		if (listController.checkPassword(loginUser)) {
			try {
				Main.switchScene("main.fxml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			logLabelInfo.setVisible(true);
			logLabelInfo.setText("Der Login war nicht erfolgreich.");
		}
	}
}
