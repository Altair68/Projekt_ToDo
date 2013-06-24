package TODO;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: BlackLaptop
 * Date: 24.06.13
 * Time: 10:42
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement
public class User {

	private String username;
	private String password;

	private User() {

	}

	public User(String aUsername, String aPassword) {
		this.username = aUsername;
		this.password = aPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
