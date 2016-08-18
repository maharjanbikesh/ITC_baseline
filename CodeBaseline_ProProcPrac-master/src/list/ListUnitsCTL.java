package list;

import student_interface.IUnitLister;
import manager.UnitManager;
import map.UnitMap;

public class ListUnitsCTL {

    private UnitManager um;

    public ListUnitsCTL() {
        um = UnitManager.UM();
    }

    // Dislay all Unit (subject code) in the xml file
    public void listUnits(IUnitLister lister) {
        
        // make the lister empty
        lister.clearUnits();
        
        // Call UnitMap class to get all the unit key
        UnitMap units = um.getUnits();
        for (String s : units.keySet()) {
            lister.addUnit(units.get(s));
        }
    }
}
