import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

// @author Omar Almassri omal7554

public class AssignmentSevenPointSeven {

    private ArrayList<Dog> dogList = new ArrayList<>();

    private void swapDogs(int index1, int index2) {

        Dog temp = dogList.get(index1); // intermediate storage

        dogList.set(index1, dogList.get(index2));
        dogList.set(index2, temp);
    }

    private void swapDogsUsingClassLibrary(int index1, int index2) {
        Collections.swap(dogList, index1, index2);
    }

    // compare 2 dogs then return the one with shorter tail length, if same tail
    // length compare names
    private Dog compareDogs(Dog firstDog, Dog secondDog) {
            //compare tailLength
        if (firstDog.getTailLength() < secondDog.getTailLength())
            return firstDog;
        else if (firstDog.getTailLength() > secondDog.getTailLength())
            return secondDog;
        else { // compare dogNames
            if (firstDog.getName().compareToIgnoreCase(secondDog.getName()) < 0)
                return firstDog;
            else
                return secondDog;
        }
    }

    private int findSmallestDog(int startIndex) {
        Dog smallestDog = dogList.get(startIndex);

        for (int i = startIndex + 1; i < dogList.size(); i++) {
            smallestDog = compareDogs(dogList.get(i), smallestDog);
        }
        return dogList.indexOf(smallestDog);
    }

    public int sortDogs() {
        int amountOfSwaps = 0;
        int startIndex = 0;

        for (int i = 0; i < dogList.size() - 1; i++) {
            int smallestDogIndex = findSmallestDog(startIndex);

            if (smallestDogIndex != startIndex) {
                swapDogs(startIndex, smallestDogIndex);
                amountOfSwaps++;
            }
            startIndex++;
        }
        return amountOfSwaps;
    }
}
