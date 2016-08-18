package manager;

import student_interface.IStudentUnitRecord;
import student_interface.IStudent;
import manager.XMLManager;
import map.StudentMap;
import bean.Student;
import bean.StudentProxy;
import list.StudentUnitRecordList;
import org.jdom.*;
import java.util.List;

public class StudentManager {

    private static StudentManager self = null;

    private StudentMap sm;
    private java.util.HashMap<String, StudentMap> um;

    // Constructor
    public static StudentManager get() {
        if (self == null) {
            self = new StudentManager();
        }
        return self;
    }

    // Constructor
    private StudentManager() {

        sm = new StudentMap();
        um = new java.util.HashMap<>();
    }

    public IStudent getStudent(Integer id) {
        IStudent is = sm.get(id);
        return is != null ? is : createStudent(id);
    }

    // Get student by its element
    /* This method will directly contact to the XML file via XMLManagaer.getXML
    RootElement means <root> tab in XML file
    getChild means the first tab in <root> ... here is <studentTable>
    getChildren means the second tab inside the first tab ... here is <student>
    */
    private Element getStudentElement(Integer id) {
        for (Element el : (List<Element>) XMLManager.getXML().getDocument().getRootElement().getChild("studentTable").getChildren("student")) {
            if (id.toString().equals(el.getAttributeValue("sid"))) {
                return el;
            }
        }
        return null;
    }

    // Create new student
    private IStudent createStudent(Integer id) {
        IStudent is;
        Element el = getStudentElement(id);
        if (el != null) {
            StudentUnitRecordList rlist = StudentUnitRecordManager.instance().getRecordsByStudent(id);
            is = new Student(new Integer(el.getAttributeValue("sid")), el.getAttributeValue("fname"), el.getAttributeValue("lname"), rlist);

            sm.put(is.getID(), is);
            return is;
        }
        throw new RuntimeException("DBMD: createStudent : student not in file");
    }

    // Get student's details
    private IStudent createStudentProxy(Integer id) {
        Element el = getStudentElement(id);

        if (el != null) {
            return new StudentProxy(id, el.getAttributeValue("fname"), el.getAttributeValue("lname"));
        }
        throw new RuntimeException("DBMD: createStudent : student not in file");
    }

    // Search Student by subject
    public StudentMap getStudentsByUnit(String uc) {
        StudentMap s = um.get(uc);
        if (s != null) {

            return s;
        }

        s = new StudentMap();
        IStudent is;
        StudentUnitRecordList ur = StudentUnitRecordManager.instance().getRecordsByUnit(uc);
        for (IStudentUnitRecord S : ur) {

            is = createStudentProxy(new Integer(S.getStudentID()));
            s.put(is.getID(), is);
        }
        um.put(uc, s);
        return s;
    }
}
