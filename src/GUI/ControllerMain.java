package GUI;

import TODO.ListController;
import TODO.Task;
import TODO.ToDoList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
	@FXML
	private Label propLabelType;
	@FXML
	private Button propBtnSave;

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

		propBtnSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if ("ToDoList".equals(propLabelType.getText())) {
					saveList();
				} else {
					saveTask();
				}
				fillTodoList();
				buildTreeFromList((ToDoList)listList.getSelectionModel().getSelectedItem());
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
				propLabelType.setText("ToDoList");
				if (toDoList2 != null) {
					propTxtUser.setText(toDoList2.getCreator().getUsername());
					propTxtName.setText(toDoList2.getName());
					propTxaDescription.setText("");
					propCheckPrioritized.setSelected(false);
					propCheckDone.setSelected(false);
					buildTreeFromList(toDoList2);
				}
			}
		});

		listList.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) {
				propLabelType.setText("ToDoList");
			}
		});

		taskTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Task>>() {
			@Override
			public void changed(ObservableValue<? extends TreeItem<Task>> observableValue, TreeItem<Task> taskTreeItem, TreeItem<Task> taskTreeItem2) {
				if (taskTreeItem2 != null) {
					Task task2 = taskTreeItem2.getValue();
					propLabelType.setText("Task");
					propTxtUser.setText(task2.getUser().getUsername());
					propTxtName.setText(task2.getName());
					propTxaDescription.setText(task2.getDescription());
					propCheckPrioritized.setSelected(task2.isPrioritized());
					propCheckDone.setSelected(task2.isDone());
				}
			}
		});

		taskTree.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() > 1) {
					ToDoList list = (ToDoList)listList.getSelectionModel().getSelectedItem();
					ToDoList theList = ListController.getListController().getToDoListList().get(ListController.getListController().getToDoListList().indexOf(list));
					theList.addTask(new Task(""));
					fillTodoList();
					buildTreeFromList((ToDoList)listList.getSelectionModel().getSelectedItem());
				}
			}
		});

		taskTree.setCellFactory(new Callback<TreeView, TreeCell>() {
			@Override
			public TreeCell call(TreeView treeView) {
				return new CustomTreeCell();
			}
		});

		taskTree.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) {
				propLabelType.setText("Task");
			}
		});

		fillTodoList();
	}

	private void fillTodoList() {
		int index = listList.getSelectionModel().getSelectedIndex();
		listList.setItems(null);
		ObservableList<ToDoList> listItems = FXCollections.observableArrayList(ListController.getListController().getToDoListList());
		listList.setItems(listItems);
		listList.getSelectionModel().select(index);
	}

	private void saveList() {
		ToDoList list = (ToDoList)listList.getSelectionModel().getSelectedItem();
		if (list != null) {
			ToDoList theList = ListController.getListController().getToDoListList().get(ListController.getListController().getToDoListList().indexOf(list));
			if (theList != null) {
				theList.setName(propTxtName.getText());
			}
		}
	}

	private void saveTask() {
		ToDoList list = (ToDoList)listList.getSelectionModel().getSelectedItem();
		ToDoList theList = ListController.getListController().getToDoListList().get(ListController.getListController().getToDoListList().indexOf(list));
		Task task = theList.getTask((Task)((TreeItem)taskTree.getSelectionModel().getSelectedItem()).getValue());
		task.setName(propTxtName.getText());
		task.setDescription(propTxaDescription.getText());
		task.setDone(propCheckDone.isSelected());
		task.setPrioritized(propCheckPrioritized.isSelected());
	}

	public void buildTreeFromList(ToDoList aList) {
		TreeItem<Task> rootItem = new TreeItem<Task>(new Task("Dummy", false, aList.getName(), aList.getCreator()));
		rootItem.setExpanded(true);
		for (Task eachTask : aList.getTaskList()) {
			rootItem.getChildren().add(buildItem(eachTask));
		}
		taskTree.setRoot(rootItem);
	}

	public TreeItem<Task> buildItem(Task aTask) {
		TreeItem<Task> theItem = new TreeItem<Task>(aTask);
		for (Task eachTask : aTask.getSubTask()) {
			theItem.getChildren().add(buildItem(eachTask));
		}
		return theItem;
	}

	class CustomTreeCell extends TreeCell<Task> {

		@Override
		protected void updateItem(Task task, boolean b) {
			super.updateItem(task, b);
			if (task != null) {
				setText(task.getName() + "\t Done: " + task.isDone() + "\t Prioritized: " + task.isPrioritized());
			} else {
				setText(null);
			}
		}
	}
}
