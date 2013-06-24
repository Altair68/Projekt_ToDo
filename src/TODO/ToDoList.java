package TODO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BlackLaptop
 * Date: 24.06.13
 * Time: 10:42
 */

@XmlRootElement
public class ToDoList {

	@XmlElement
	private List<Task> taskList;
	@XmlElement
	private User creator;
	@XmlElement
	private List<User> userList;
	private String name;

	public ToDoList() {

	}

	public ToDoList (User aCreator, String aName) {
		this.creator = aCreator;
		this.name = aName;
	}

	public void addTask(Task aTask) {
		if (aTask != null) {
			getTaskList().add(aTask);
		}
	}

	public void removeTask(Task aTask) {
		getTaskList().remove(aTask);
	}

	public List<Task> getTaskList() {
		if (taskList == null) {
			taskList = new ArrayList<Task>();
		}
		return taskList;
	}

	public User getCreator() {
		return creator;
	}

	public List<User> getUserList() {
		if (userList == null) {
			userList = new ArrayList<User>();
		}
		return userList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
