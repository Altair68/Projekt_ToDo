package TODO;

import javax.xml.bind.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
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

	private List<User> Users;

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
			context = JAXBContext.newInstance(ArrayList.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			m.marshal(getUsers(), new FileOutputStream(theFile));
			m.marshal(getUsers(), System.out);
		} catch (JAXBException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch (FileNotFoundException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	public void unmarshallData() {
		File dir = new File(SAVE_DIR_NAME);
		if (dir.exists()) {
			File[] files = dir.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					if("xml".equals(pathname.getName().substring(pathname.getName().length()-4))) {
						return true;
					}
					return false;
				}
			});
			for (File eachFile : files) {
				try {
					JAXBContext jc = JAXBContext.newInstance ("generated");
					Unmarshaller u = jc.createUnmarshaller ();
					JAXBElement element = (JAXBElement) u.unmarshal (eachFile);

					if (element.getValue() instanceof ToDoList) {
						ToDoList list = (ToDoList) element.getValue ();
						getToDoListList().add(list);
					} else if (element.getValue() instanceof ArrayList) {
						getUsers().addAll((List)element.getValue());
					}
				} catch (JAXBException e) {
					e.printStackTrace ();
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

	public List<User> getUsers() {
		if (Users == null) {
			Users = new ArrayList<User>();
		}
		return Users;
	}

	public boolean addUser(User aNewUser) {
		if (!checkUserExists(aNewUser)) {
			getUsers().add(aNewUser);
			return true;
		} else {
			return false;
		}
	}

	public boolean checkUserExists(User aUser) {
		if (getUsers() == null) {
			return false;
		} else {
			String theUsername = aUser.getUsername();
			for (User theUser : getUsers()) {
				if (theUsername.equals(theUser.getUsername())) {
					return true;
				}
			}
			return false;
		}
	}

	public User findUserByUsername(String aUsername) {
		for (User theUser : getUsers()) {
			if (aUsername.equals(theUser.getUsername())) {
				return theUser;
			}
		}
		return new User("-1", "-1");
	}

	public boolean removeUser(User aUserToDelete) {
		if (checkUserExists(aUserToDelete)) {
			getUsers().remove(findUserByUsername(aUserToDelete.getUsername()));
			return true;
		} else {
			return false;
		}
	}

	public boolean checkPassword(User aUserToCheck) {
		if (checkUserExists(aUserToCheck)) {
			User theDbUser = findUserByUsername(aUserToCheck.getUsername());
			if (theDbUser.getPassword().equals(aUserToCheck.getPassword())) {
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
}
