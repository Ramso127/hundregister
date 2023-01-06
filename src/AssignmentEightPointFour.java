import java.util.ArrayList;

// @author Omar Almassri omal7554

public class AssignmentEightPointFour {

    private ArrayList<Owner> ownerList = new ArrayList<>();
    private ArrayList<Dog> dogList = new ArrayList<>();
    private InputReader inputReader = new InputReader();

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
        if (dogList.size() == 0)
            System.out.println("Error: no dogs in register");
        else {
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
}
// lade in 8.3 i denna klass i vpl för att det skulle funka