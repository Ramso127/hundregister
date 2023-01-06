import java.util.ArrayList;

// @author Omar Almassri omal7554

public class AssignmentEightPointEight {

    private InputReader inputReader = new InputReader();
    private ArrayList<Dog> dogList = new ArrayList<>();
    private ArrayList<Owner> ownerList = new ArrayList<>();

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
            return;
        }
        dog.removeOwnerOfDog();
        dogList.remove(dog);
        System.out.println(dog.getName() + " is removed from the register");
    }

    // 8.2 (find owner with name)
    private Owner findOwner(String name) {
        name = name.strip();
        for (Owner owner : ownerList) {
            if (owner.getName().equalsIgnoreCase(name))
                return owner;
        }
        return null;
    }

    public void giveDog() {
        String dogName = inputReader.stringReader("Enter the name of the dog");

        while (dogName.isBlank()) {
            dogName = inputReader.stringReader("Error: the dogs name can not be empty");
        }

        Dog dog = findDog(dogName);
        if (dog == null) {
            System.out.println("Error: no dog with that name");
            return;
        }

        if (dog.getOwner() != null || ownerList.contains(dog.getOwner())) {
            System.out.println("Error: the dog already has an owner");
            return;
        }

        String ownerName = inputReader.stringReader("Enter the name of the new owner");

        while (ownerName.isBlank()) {
            ownerName = inputReader.stringReader("Error: the owners name can not be empty");
        }

        Owner owner = findOwner(ownerName);
        if (owner == null) {
            System.out.println("Error: no such owner");
            return;
        }

        owner.recieveDog(dog);

        System.out.println(ownerName + " now owns " + dogName);
    }
}
