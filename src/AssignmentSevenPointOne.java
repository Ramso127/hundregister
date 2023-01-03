// @author Omar Almassri omal7554

import java.util.ArrayList;

public class AssignmentSevenPointOne {



    private ArrayList<Dog> dogList = new ArrayList<>();
    public ArrayList<Dog> getDogList() {
        return dogList;
    }

    private InputReader inputReader = new InputReader();

    public void registerNewDog() {

        

        String name = inputReader.stringReader("Name").strip(); //strip behövs bara för vpl annars inte.
        while (name.strip().equals("")) {
            name = inputReader.stringReader("Error: the name can not be empty").strip();
        }

        String breed = inputReader.stringReader("Breed").strip();
        while (breed.strip().equals("")) {
            breed = inputReader.stringReader(" Error: the breed can not be empty").strip();
        }
      
        int age = inputReader.intReader("Age");
        int weight = inputReader.intReader("Weight");

        Dog dog = new Dog(name, breed, age, weight);

        dogList.add(dog);
        System.out.println(dog.getName() + " added to the register");
    }
}
