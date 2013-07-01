package TODO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BlackLaptop
 * Date: 01.07.13
 * Time: 11:31
 */
@XmlRootElement
public class UserList {

	@XmlElement
	private List<User> users;

	public UserList() {

	}

	public UserList(List<User> users) {
		this.users = users;
	}

	public List<User> getUsers() {
		if (users == null) {
			users = new ArrayList<User>();
		}
		return users;
	}

	public void add(User aUser) {
		getUsers().add(aUser);
	}

	public void addAll(Collection<User> aCollection) {
		getUsers().addAll(aCollection);
	}

	public void remove(User aUser) {
		getUsers().remove(aUser);
	}
}
