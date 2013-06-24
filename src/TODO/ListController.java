package TODO;

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

	public static final String SAVE_DIR_NAME = "ToDoList";

	private List<ToDoList> toDoListList;

	public ListController() {
		checkDirs();
	}

	public void marshallData() {
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

					ToDoList list = (ToDoList) element.getValue ();
					getToDoListList().add(list);
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
}
