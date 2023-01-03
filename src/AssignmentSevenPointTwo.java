// @author Omar Almassri omal7554

import java.util.ArrayList;

public class AssignmentSevenPointTwo {
    
    private InputReader inputReader = new InputReader();
   
    private ArrayList<Dog> dogList = new ArrayList<>();

    // AssignmentSevenPointOne sevenOne = new AssignmentSevenPointOne();    //fel enligt vpl men funkar annars
    // private ArrayList<Dog> dogList = sevenOne.getDogList();

    private ArrayList<Dog> findDogsWithTailLength(double smallestTailLength) {

        ArrayList<Dog> dogsWithTailLength = new ArrayList<>();
        for(Dog dog: dogList) {
            if(dog.getTailLength() >= smallestTailLength)
                dogsWithTailLength.add(dog);
        }

        return dogsWithTailLength;

    }



    public void listDogsWithTailLength() {
        
        double smallestTailLength;
        if(dogList.size() < 1)
            System.out.println("Error: no dogs in register");
        else {
            smallestTailLength = inputReader.decimalReader("Smallest tail length to display");

             ArrayList<Dog> dogsWithTailLength = findDogsWithTailLength(smallestTailLength);

            if(dogsWithTailLength.size() < 1)
                System.out.println("Error: no dog have a tail that long");
            else {
                System.out.println("The following dogs has such a large tail:");

                for(Dog dog: dogsWithTailLength)
                System.out.println( "* " + dog.getName() + " (" + dog.getBreed() + ", " + dog.getAge() + " years, " + dog.getWeight() + " kilo, " + dog.getTailLength() + " cm tail)" );
            }
        }

    }


}
