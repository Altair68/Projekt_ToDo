package GUI;

import TODO.ListController;
import TODO.User;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
	private Button loginButton;
	@FXML
	private Button registerButton;
	@FXML
	private TextField logUsername;
	@FXML
	private PasswordField logPassword;
	@FXML
	private Label logLabelInfo;
	@FXML
	private TextField regUsername;
	@FXML
	private PasswordField regPassword;
	@FXML
	private PasswordField regPasswordRe;
	@FXML
	private Label regLabelInfo;
	@FXML
	private AnchorPane logAnchor;
	@FXML
	private AnchorPane regAnchor;

	//Variablen
	private ListController listController = ListController.getListController();


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
