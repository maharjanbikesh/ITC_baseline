package list;

import manager.StudentManager;
import student_interface.IStudentLister;
import map.StudentMap;

public class ListStudentsCTL {

    private StudentManager sm;

    public ListStudentsCTL() {
        sm = StudentManager.get();
    }

    // List all student based on the chosen subject
    public void listStudents(IStudentLister lister, String unitCode) {
        // clear all the listed student from the first scan
        lister.clearStudents();
        
        // Create studentMap and call the method search by unit
        StudentMap students = sm.getStudentsByUnit(unitCode);
        
        // use for each to add found student to lister for the next display
        for (Integer id : students.keySet()) {
            lister.addStudent(students.get(id));
        }
    }
}
