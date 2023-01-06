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
