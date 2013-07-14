package TODO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BlackLaptop
 * Date: 24.06.13
 * Time: 10:41
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement
public class Task {

	private String name;
	private boolean done;
	private boolean prioritized;
	private String description;
	@XmlElement
	private User user;
	@XmlElement
	private List<Task> subTask;

	private Task() {

	}

	public Task(String aDescription, boolean isPrioritized, String aName, User aUser) {
		this.description = aDescription;
		this.prioritized = isPrioritized;
		this.name = aName;
		this.user = aUser;
	}

	public Task(String aDescription) {
		this(aDescription, false, "New Task", ListController.getListController().getLoggedUser());
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public boolean isPrioritized() {
		return prioritized;
	}

	public void setPrioritized(boolean prioritized) {
		this.prioritized = prioritized;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Task> getSubTask() {
		if (subTask == null) {
			subTask = new ArrayList<Task>();
		}
		return subTask;
	}

	public void addSubTask(Task aTaks) {
		getSubTask().add(aTaks);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public Task getTask(Task aTask) {
		if (getSubTask().contains(aTask)) {
			return getSubTask().get(getSubTask().indexOf(aTask));
		} else {
			for (Task eachTask : getSubTask()) {
				Task returnTask = eachTask.getTask(aTask);
				if (returnTask != null) {
					return returnTask;
				}
			}
		}
		return null;
	}
}
