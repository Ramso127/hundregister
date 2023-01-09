import java.util.ArrayList;

// @author Omar Almassri omal7554

public class AssignmentSevenPointFive {

    private InputReader inputReader = new InputReader();
    private ArrayList<Dog> dogList = new ArrayList<>();

    // 7.3 metod
    private Dog findDog(String dogName) {

        dogName = dogName.strip();

        for (Dog dog : dogList) {
            if (dog.getName().equalsIgnoreCase(dogName))
                return dog;
        }

        return null;

    }

    public void removeDog() {
        String dogName = inputReader.stringReader("Enter the name of the dog");
        
        while (dogName.isBlank())
            dogName = inputReader.stringReader("Error: the dogs name can not be empty");

        Dog dog = findDog(dogName);

        if (dog == null) {
            System.out.println("Error: no such dog");
        } else {
            dogList.remove(dog);
            System.out.println(dog.getName() + " is removed from the register");
        }
    }

}
