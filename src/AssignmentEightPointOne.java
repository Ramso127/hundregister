// @author Omar Almassri omal7554

import java.util.ArrayList;

public class AssignmentEightPointOne {
    
    private ArrayList<Owner> ownerList = new ArrayList<>();
    private InputReader inputReader = new InputReader();

    public void registerNewOwner() {
        String ownerName = inputReader.stringReader("Name").strip();
        // kankse en if sats ifall owner redan finns i listan
        while (ownerName.isBlank()) {
            ownerName = inputReader.stringReader("Error: the name can not be empty");
        }

        Owner newOwner = new Owner(ownerName);
        ownerList.add(newOwner);
    }
}
