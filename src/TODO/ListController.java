package TODO;

import GUI.ControllerMain;

import javax.xml.bind.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BlackLaptop
 * Date: 24.06.13
 * Time: 10:43
 */
public class ListController {

	private static ListController listController;

	public static final String SAVE_DIR_NAME = "ToDoList";

	private List<ToDoList> toDoListList;
	private ControllerMain guiController;

	private UserList users;
	private User loggedUser;

	private ListController() {
		checkDirs();
	}

	public void marshallData() {
		System.out.printf("asd");
		for (ToDoList eachList : getToDoListList()) {
			JAXBContext context;
			try {
				File theFile = new File(SAVE_DIR_NAME + "/" + eachList.getName() + ".xml");
				if (!theFile.exists()) {
					theFile.createNewFile();
				}
				context = JAXBContext.newInstance(ToDoList.class);
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

				m.marshal(eachList, new FileOutputStream(theFile));
				m.marshal(eachList, System.out);
			} catch (JAXBException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			} catch (FileNotFoundException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			} catch (IOException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}
		}
		JAXBContext context;
		try {
			File theFile = new File(SAVE_DIR_NAME + "/" + "users.xml");
			if (!theFile.exists()) {
				theFile.createNewFile();
			}
			context = JAXBContext.newInstance(UserList.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			m.marshal(getUserList(), new FileOutputStream(theFile));
			m.marshal(getUserList(), System.out);
		} catch (JAXBException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch (FileNotFoundException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	public void unmarshallData() {
		System.out.println("Unmarshall");
		File dir = new File(SAVE_DIR_NAME);
		if (dir.exists()) {
			File[] files = dir.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					if("xml".equals(pathname.getName().substring(pathname.getName().length()-3))) {
						return true;
					}
					return false;
				}
			});
			for (File eachFile : files) {
				if ("users.xml".equals(eachFile.getName())) {
					try {
						JAXBContext jc = JAXBContext.newInstance (UserList.class);
						Unmarshaller u = jc.createUnmarshaller ();
						UserList list = (UserList) u.unmarshal(eachFile);
						users = list;
					} catch (JAXBException e) {
						e.printStackTrace ();
					}
				} else {
					try {
						JAXBContext jc = JAXBContext.newInstance (ToDoList.class);
						Unmarshaller u = jc.createUnmarshaller ();
						ToDoList list = (ToDoList) u.unmarshal (eachFile);
						getToDoListList().add(list);
					} catch (JAXBException e) {
						e.printStackTrace ();
					}
				}
			}
		}
	}

	public void checkDirs() {
		File theFile = new File(SAVE_DIR_NAME);
		if (!theFile.exists()) {
			theFile.mkdir();
		}
	}

	public List<ToDoList> getToDoListList() {
		if (toDoListList == null) {
			toDoListList = new ArrayList<ToDoList>();
		}
		return toDoListList;
	}

	public UserList getUserList() {
		if (users == null) {
			users = new UserList();
		}
		return users;
	}

	public boolean addUser(User aNewUser) {
		if (!checkUserExists(aNewUser)) {
			getUserList().add(aNewUser);
			return true;
		} else {
			return false;
		}
	}

	public boolean checkUserExists(User aUser) {
		if (getUserList() == null) {
			return false;
		} else {
			String theUsername = aUser.getUsername();
			for (User theUser : getUserList().getUsers()) {
				if (theUsername.equals(theUser.getUsername())) {
					return true;
				}
			}
			return false;
		}
	}

	public User findUserByUsername(String aUsername) {
		for (User theUser : getUserList().getUsers()) {
			if (aUsername.equals(theUser.getUsername())) {
				return theUser;
			}
		}
		return new User("-1", "-1");
	}

	public boolean removeUser(User aUserToDelete) {
		if (checkUserExists(aUserToDelete)) {
			getUserList().remove(findUserByUsername(aUserToDelete.getUsername()));
			return true;
		} else {
			return false;
		}
	}

	public boolean checkPassword(User aUserToCheck) {
		if (checkUserExists(aUserToCheck)) {
			User theDbUser = findUserByUsername(aUserToCheck.getUsername());
			if (theDbUser.getPassword().equals(aUserToCheck.getPassword())) {
				loggedUser = aUserToCheck;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static ListController getListController() {
		if (listController == null) {
			listController = new ListController();
		}
		return listController;
	}

	public void createNewList() {
		ToDoList theList;
		int i = 0;
		do {
			theList = new ToDoList(loggedUser, "New List" + i++);
		} while (getToDoListList().contains(theList));
		getToDoListList().add(theList);
	}

	public User getLoggedUser() {
		return loggedUser;
	}
}
