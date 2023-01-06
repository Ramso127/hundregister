import java.util.ArrayList;

// @author Omar Almassri omal7554

public class AssignmentSevenPointFour {

    private InputReader inputReader = new InputReader();

    private ArrayList<Dog> dogList = new ArrayList<>();

    private Dog findDog(String dogName) {

        dogName = dogName.strip();

        for (Dog dog : dogList) {
            if (dog.getName().equalsIgnoreCase(dogName))
                return dog;
        }

        return null;

    }

    public void increaseAge() {

        String dogName = inputReader.stringReader("Enter the name of the dog");
        while (dogName.isBlank())
            dogName = inputReader.stringReader("Error: the dogs name can not be empty");

        Dog dog = findDog(dogName);

        if (dog == null) {
            System.out.println("Error: no such dog");
        } else {
            dog.increaseAge(1);
            System.out.println(dog.getName() + " is now one year older");
        }

    }
}
