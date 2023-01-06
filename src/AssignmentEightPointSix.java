import java.util.ArrayList;

// @author Omar Almassri omal7554

public class AssignmentEightPointSix {

    private InputReader inputReader = new InputReader();
    private ArrayList<Dog> dogList = new ArrayList<>();
    private ArrayList<Owner> ownerList = new ArrayList<>();

    // 7.3 (find dog with name)
    private Dog findDog(String dogName) {
        dogName = dogName.strip();
        for (Dog dog : dogList) {
            if (dog.getName().equalsIgnoreCase(dogName))
                return dog;
        }
        return null;
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

    public void removeOwnedDog() {

        String dogName = inputReader.stringReader("Enter the name of the dog");
        while (dogName.isBlank())
            dogName = inputReader.stringReader("Error: the dogs name can not be empty");

        Dog dog = findDog(dogName);

        if (dog == null) {
            System.out.println("Error: no such dog.");
            return;
        }

        if (dog.getOwner() == null || findOwner(dog.getOwner().getName()) == null) {
            System.out.println("Error: " + dogName + " is not owned by anyone");
            return;
        }
        String ownerName = dog.getOwner().getName();

        dog.getOwner().removeDogFromOwner(dog);
        System.out.println(dogName + " has been removed from " + ownerName);
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

    // 8.4
    public void listOwners() {
        if (ownerList.size() == 0) {
            System.out.println("Error: no owners in register");
            return;
        }

        for (Owner owner : ownerList)
            System.out.println(owner);
    }

    private ArrayList<Dog> findDogsWithTailLength(double smallestTailLength) {

        ArrayList<Dog> dogsWithTailLength = new ArrayList<>();
        for (Dog dog : dogList) {
            if (dog.getTailLength() >= smallestTailLength)
                dogsWithTailLength.add(dog);
        }

        return dogsWithTailLength;

    }

    public void listDogsWithTailLength() {

        double smallestTailLength;
        if (dogList.size() == 0) {
            System.out.println("Error: no dogs in register");
            return;
        }

        smallestTailLength = inputReader.decimalReader("Smallest tail length to display");

        ArrayList<Dog> dogsWithTailLength = findDogsWithTailLength(smallestTailLength);

        if (dogsWithTailLength.size() < 1)
            System.out.println("Error: no dog have a tail that long");
        else {
            System.out.println("The following dogs has such a large tail:");

            for (Dog dog : dogsWithTailLength)
                System.out.println(dog);

        }
    }
}
