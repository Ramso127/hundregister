import java.util.ArrayList;

// @author Omar Almassri omal7554

public class AssignmentSevenPointThree {

    private ArrayList<Dog> dogList = new ArrayList<>();

    public Dog findDog(String dogName) {

        dogName = dogName.strip();

        for (Dog dog : dogList) {
            if (dog.getName().equalsIgnoreCase(dogName))
                return dog;
        }

        return null;

    }

}
