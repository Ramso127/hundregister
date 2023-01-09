import java.util.ArrayList;
import java.util.Arrays;

// @author Omar Almassri omal7554

public class Hundregister {

    private ArrayList<Dog> dogList = new ArrayList<>();
    private ArrayList<Owner> ownerList = new ArrayList<>();
    private InputReader inputReader = new InputReader();
    private ArrayList<Dog> dogsToRemove = new ArrayList<>();

    private static final String REGISTER_NEW_DOG_COMMAND = "register new dog";
    private static final String LIST_DOGS_COMMAND = "list dogs";
    private static final String INCREASE_AGE_COMMAND = "increase age";
    private static final String REMOVE_DOG_COMMAND = "remove dog";
    private static final String REGISTER_NEW_OWNER_COMMAND = "register new owner";
    private static final String GIVE_DOG_COMMAND = "give dog";
    private static final String LIST_OWNERS_COMMAND = "list owners";
    private static final String REMOVE_OWNED_DOG_COMMAND = "remove owned dog";
    private static final String REMOVE_OWNER_COMMAND = "remove owner";
    private static final String EXIT_COMMAND = "exit";

    public static void main(String[] args) {
        new Hundregister().run();
    }

    private void run() {
        setup();
        runCommandLoop();
    }

    private void printCommandMenu() {
        System.out.println("The following commands are available: ");
        System.out.println("register new dog");
        System.out.println("list dogs");
        System.out.println("increase age");
        System.out.println("remove dog");
        System.out.println("register new owner");
        System.out.println("give dog");
        System.out.println("list owners");
        System.out.println("remove owned dog");
        System.out.println("remove owner");
        System.out.println("exit");
    }

    private void setup() {
        System.out.println("Welcome to the dog registry program!");
        System.out.println();
        printCommandMenu();
    }

    private void runCommandLoop() {
        String command;
        do {
            command = readCommand();
            handleCommand(command);
        } while (!command.equals(EXIT_COMMAND));
    }

    private String readCommand() {
        return inputReader.stringReader("Command").strip();
    }

    private void handleCommand(String command) {
        switch (command) {
            case REGISTER_NEW_DOG_COMMAND:
                registerNewDog();
                break;
            case LIST_DOGS_COMMAND:
                sortDogs();
                listDogsWithTailLength();
                break;
            case INCREASE_AGE_COMMAND:
                increaseAge();
                break;
            case REMOVE_DOG_COMMAND:
                removeDog();
                break;
            case REGISTER_NEW_OWNER_COMMAND:
                registerNewOwner();
                break;
            case GIVE_DOG_COMMAND:
                giveDog();
                break;
            case LIST_OWNERS_COMMAND:
                listOwners();
                break;
            case REMOVE_OWNED_DOG_COMMAND:
                removeOwnedDog();
                break;
            case REMOVE_OWNER_COMMAND:
                removeOwner();
                break;
            case EXIT_COMMAND:
                System.out.println("Goodbye!");
                break;
            default:
                System.out.println("Error: invalid command");
                printCommandMenu();
        }
    }

    private void registerNewDog() {
        String name = inputReader.stringReader("Name").strip(); // strip only needed for vpl.
        while (name.isBlank()) {
            name = inputReader.stringReader("Error: the name can't be empty").strip();
        }

        String breed = inputReader.stringReader("Breed").strip();
        while (breed.isBlank()) {
            breed = inputReader.stringReader(" Error: the breed can't be empty").strip();
        }

        int age = inputReader.intReader("Age");
        int weight = inputReader.intReader("Weight");

        Dog dog = new Dog(name, breed, age, weight);

        dogList.add(dog);
        System.out.println(dog.getName() + " added to the register");
        System.out.println();
    }

    private void swapDogs(int index1, int index2) {

        Dog temp = dogList.get(index1); // intermediate storage

        dogList.set(index1, dogList.get(index2));
        dogList.set(index2, temp);
    }

    // compare 2 dogs then return the one with shorter tail length, if same tail
    // length sort after name
    private Dog compareDogs(Dog dog1, Dog dog2) {
        if (dog1 instanceof Dog && dog2 instanceof Dog) {
            if (dog1.getTailLength() < dog2.getTailLength())
                return dog1;
            else if (dog1.getTailLength() > dog2.getTailLength())
                return dog2;
            else { // Sort after dogNames
                String[] dogNames = { dog2.getName(), dog1.getName() };
                Arrays.sort(dogNames);
                if (dog1.getName().equals(dogNames[0]))
                    return dog1;
                else
                    return dog2;
            }
        }
        return null;
    }

    private int findSmallestDog(int startIndex) {
        Dog smallestDog = dogList.get(startIndex);

        for (int i = startIndex + 1; i < dogList.size(); i++) {
            smallestDog = compareDogs(dogList.get(i), smallestDog);
        }
        return dogList.indexOf(smallestDog);
    }

    private void sortDogs() {
        int startIndex = 0;

        for (int i = 0; i < dogList.size() - 1; i++) {
            int smallestDogIndex = findSmallestDog(startIndex);

            if (smallestDogIndex != startIndex) {
                swapDogs(startIndex, smallestDogIndex);
            }
            startIndex++;
        }
    }

    private ArrayList<Dog> findDogsWithTailLength(double smallestTailLength) {

        ArrayList<Dog> dogsWithTailLength = new ArrayList<>();

        for (Dog dog : dogList) {
            if (dog.getTailLength() >= smallestTailLength)
                dogsWithTailLength.add(dog);
        }
        return dogsWithTailLength;
    }

    private void listDogsWithTailLength() {
        double smallestTailLength;

        if (dogList.size() == 0)
            System.out.println("Error: no dogs in register");
        else {
            smallestTailLength = inputReader.decimalReader("Smallest tail length to display");

            ArrayList<Dog> dogsWithTailLength = findDogsWithTailLength(smallestTailLength);

            if (dogsWithTailLength.size() == 0)
                System.out.println("Error: no dog has a tail that long");
            else {
                System.out.println("The following dogs has such a large tail:");

                for (Dog dog : dogsWithTailLength)
                    System.out.println(dog);
            }
        }
    }

    private Dog findDog(String dogName) {
        dogName = dogName.strip();

        for (Dog dog : dogList) {
            if (dog.getName().equalsIgnoreCase(dogName))
                return dog;
        }
        return null;
    }

    private void increaseAge() {
        String dogName = inputReader.stringReader("Enter the name of the dog");
        while (dogName.isBlank())
            dogName = inputReader.stringReader("Error: the dogs name can't be empty");

        Dog dog = findDog(dogName);
        if (dog == null) {
            System.out.println("Error: no such dog");
        } else {
            dog.increaseAge(1);
            System.out.println(dog.getName() + " is now one year older");
        }
    }

    private void removeDog() {
        String dogName = inputReader.stringReader("Enter the name of the dog");

        while (dogName.isBlank())
            dogName = inputReader.stringReader("Error: the dogs name can't be empty");

        Dog dog = findDog(dogName);
        if (dog == null) {
            System.out.println("Error: no such dog");
        } else {
            dogList.remove(dog);
            System.out.println(dog.getName() + " is removed from the register");
        }
    }

    private void registerNewOwner() {
        String ownerName = inputReader.stringReader("Name").strip();
        while (ownerName.isBlank())
            ownerName = inputReader.stringReader("Error: the name can't be empty");

        Owner newOwner = new Owner(ownerName);
        ownerList.add(newOwner);
    }

    private Owner findOwner(String name) {
        name = name.strip();
        for (Owner owner : ownerList) {
            if (owner.getName().equalsIgnoreCase(name))
                return owner;
        }
        return null;
    }

    private void giveDog() {
        String dogName = inputReader.stringReader("Enter the name of the dog");
        while (dogName.isBlank()) {
            dogName = inputReader.stringReader("Error: the dogs name can't be empty");
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
            ownerName = inputReader.stringReader("Error: the owners name can't be empty");
        }

        Owner owner = findOwner(ownerName);
        if (owner == null) {
            System.out.println("Error: no such owner");
            return;
        }

        owner.recieveDog(dog);
        System.out.println(ownerName + " now owns " + dogName);
    }

    private void listOwners() {
        if (ownerList.size() == 0) {
            System.out.println("Error: no owners in register");
            return;
        }

        for (Owner owner : ownerList)
            System.out.println(owner);
    }

    private void removeOwnedDog() {
        String dogName = inputReader.stringReader("Enter the name of the dog");
        while (dogName.isBlank())
            dogName = inputReader.stringReader("Error: the dogs name can't be empty");

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

    private void removeOwner() {
        String ownerName = inputReader.stringReader("Enter the name of the owner");
        while (ownerName.isBlank())
            ownerName = inputReader.stringReader("Error: the owners name can't be empty");

        Owner owner = findOwner(ownerName);
        if (owner == null) {
            System.out.println("Error: no such owner.");
            return;
        }

        for (Dog dog : dogList) {
            if (owner.getOwnedDogs().containsDog(dog))
                dogsToRemove.add(dog);
        }
        ownerList.remove(owner);
        dogList.removeAll(dogsToRemove);

        System.out.println(ownerName + " is removed from the register");
    }
}