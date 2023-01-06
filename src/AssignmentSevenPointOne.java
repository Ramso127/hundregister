import java.util.ArrayList;

// @author Omar Almassri omal7554

public class AssignmentSevenPointOne {

    private ArrayList<Dog> dogList = new ArrayList<>();

    private InputReader inputReader = new InputReader();

    public void registerNewDog() {

        String name = inputReader.stringReader("Name").strip(); // strip only needed for vpl.
        while (name.isBlank()) {
            name = inputReader.stringReader("Error: the name can not be empty").strip();
        }

        String breed = inputReader.stringReader("Breed").strip();
        while (breed.isBlank()) {
            breed = inputReader.stringReader(" Error: the breed can not be empty").strip();
        }

        int age = inputReader.intReader("Age");
        int weight = inputReader.intReader("Weight");

        Dog dog = new Dog(name, breed, age, weight);

        dogList.add(dog);
        System.out.println(dog.getName() + " added to the register");
    }
}
