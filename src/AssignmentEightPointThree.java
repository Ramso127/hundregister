// @author Omar Almassri omal7554

import java.util.ArrayList;

public class AssignmentEightPointThree {

    private ArrayList<Dog> dogList = new ArrayList<>();
    private ArrayList<Owner> ownerList = new ArrayList<>();
    private InputReader inputReader = new InputReader();
  
    //7.3 (find dog with name)
    private Dog findDog(String dogName) {
        dogName = dogName.strip();
        for(Dog dog: dogList) {
            if(dog.getName().equalsIgnoreCase(dogName))
            return dog;
        }
        return null;
    }

    //8.2 (find owner with name)
    private Owner findOwner(String name) {
        name = name.strip();
        for (Owner owner: ownerList) {
            if (owner.getName().equalsIgnoreCase(name))
                return owner;
        }
        return null;
    }

    public void giveDog() {
        String dogName = inputReader.stringReader("Enter the name of the dog");
        
        while (dogName.isBlank()){
            dogName = inputReader.stringReader("Error: no dog with that name");
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

        while(ownerName.isBlank()) {
            ownerName = inputReader.stringReader("Error: no such owner");
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
