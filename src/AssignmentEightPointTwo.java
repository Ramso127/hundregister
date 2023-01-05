// @author Omar Almassri omal7554

import java.util.ArrayList;

public class AssignmentEightPointTwo {
    
    private ArrayList<Owner> ownerList = new ArrayList<>();

    public Owner findOwner(String name) {
        for (Owner owner: ownerList) {
            if (owner.getName().equalsIgnoreCase(name))
                return owner;
        }
        return null;
    }
}
