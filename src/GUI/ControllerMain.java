package GUI;

import TODO.ListController;
import TODO.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

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

		menuFileNew.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				ListController.getListController().createNewList();
				fillTodoList();
			}
		});

		listList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() > 1) {
					ListController.getListController().createNewList();
					fillTodoList();
				}
			}
		});
		/*listList.setCellFactory(new Callback<ListView, ListCell>() {
			@Override
			public ListCell call(ListView listView) {
				return new ListCell<ToDoList>() {
					@Override
					protected void updateItem(ToDoList toDoList, boolean b) {
						super.updateItem(toDoList, b);
						if (b && toDoList != null) {
							setText(toDoList.getName());
						} else {
							System.out.println(toDoList);
						}
					}
				};
			}
		});*/


		taskTree.setCellFactory(new Callback<TreeView, TreeCell>() {
			@Override
			public TreeCell call(TreeView treeView) {
				return new CustomTreeCell();
			}
		});

		fillTodoList();
	}

	private void fillTodoList() {
		ObservableList items = FXCollections.observableArrayList(ListController.getListController().getToDoListList());
		listList.setItems(items);
	}

	class CustomTreeCell extends TreeCell<Task> {
		@Override
		protected void updateItem(Task task, boolean b) {
			super.updateItem(task, b);
			if (b && task != null) {
				setText(task.getName());
			} else {
				setText(null);
			}
		}
	}
}
