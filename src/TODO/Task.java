package TODO;

import javax.xml.bind.annotation.XmlRootElement;

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

	private Task() {

	}

	public Task(String aDescription, boolean isPrioritized, String aName) {
		this.description = aDescription;
		this.prioritized = isPrioritized;
		this.name = aName;
	}

	public Task(String aDescription) {
		this(aDescription, false, "New Task");
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
