package GUI;

import TODO.ListController;
import TODO.Task;
import TODO.ToDoList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
	@FXML
	private Label propLabelType;

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
		listList.setCellFactory(new Callback<ListView, ListCell>() {
			@Override
			public ListCell call(ListView listView) {
				return new ListCell() {
					@Override
					protected void updateItem(Object o, boolean b) {
						super.updateItem(o, b);
						if (o instanceof ToDoList && o != null) {
							setText(((ToDoList)o).getName());
						} else {
							setText(null);
						}
					}
				};
			}
		});

		listList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ToDoList>() {
			@Override
			public void changed(ObservableValue<? extends ToDoList> observableValue, ToDoList toDoList, ToDoList toDoList2) {
				saveProps(toDoList);
				propLabelType.setText("ToDoList");
				if (toDoList2 != null) {
					propTxtUser.setText(toDoList2.getCreator().getUsername());
					propTxtName.setText(toDoList2.getName());
					propTxaDescription.setText("");
					propCheckPrioritized.setSelected(false);
					propCheckDone.setSelected(false);
				}
				fillTodoList();
			}
		});

		taskTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
			@Override
			public void changed(ObservableValue<? extends Task> observableValue, Task task, Task task2) {
				saveProps(task);
				propLabelType.setText("Task");
				propTxtUser.setText(task2.getUser().getUsername());
				propTxtName.setText(task2.getName());
				propTxaDescription.setText(task2.getDescription());
				propCheckPrioritized.setSelected(task2.isPrioritized());
				propCheckDone.setSelected(task2.isDone());
			}
		});

		taskTree.setCellFactory(new Callback<TreeView, TreeCell>() {
			@Override
			public TreeCell call(TreeView treeView) {
				return new CustomTreeCell();
			}
		});

		fillTodoList();
	}

	private void fillTodoList() {
		ObservableList<ToDoList> theItems = FXCollections.observableArrayList(new ArrayList<ToDoList>());
		listList.setItems(theItems);
		ObservableList<ToDoList> items = FXCollections.observableArrayList(ListController.getListController().getToDoListList());
		listList.setItems(items);
	}

	private void saveProps(Object o) {
		if("ToDoList".equals(propLabelType.getText()) && o instanceof ToDoList) {
			ToDoList list = (ToDoList)o;
			ToDoList theList = ListController.getListController().getToDoListList().get(ListController.getListController().getToDoListList().indexOf(list));
			theList.setName(propTxtName.getText());
		} else if ("Task".equals(propLabelType.getText()) && o instanceof ToDoList) {
			Task task = (Task)taskTree.getSelectionModel().getSelectedItem();
			task.setName(propTxtName.getText());
			task.setDescription(propTxaDescription.getText());
			task.setDone(propCheckDone.isSelected());
			task.setPrioritized(propCheckPrioritized.isSelected());
		} else if ("Task".equals(propLabelType.getText()) && o instanceof Task) {
			Task task = (Task)o;
			task.setName(propTxtName.getText());
			task.setDescription(propTxaDescription.getText());
			task.setDone(propCheckDone.isSelected());
			task.setPrioritized(propCheckPrioritized.isSelected());
		} else if ("ToDoList".equals(propLabelType.getText()) && o instanceof Task) {
			ToDoList list = (ToDoList)listList.getSelectionModel().getSelectedItem();
			if (list != null) {
				ToDoList theList = ListController.getListController().getToDoListList().get(ListController.getListController().getToDoListList().indexOf(list));
				if (theList != null) {
					theList.setName(propTxtName.getText());
				}
			}
		}
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
