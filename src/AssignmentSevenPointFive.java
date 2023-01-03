// @author Omar Almassri omal7554

import java.util.ArrayList;

public class AssignmentSevenPointFive {
    
    //private AssignmentSevenPointThree sevenThree = new AssignmentSevenPointThree();
    private InputReader inputReader = new InputReader();
    private ArrayList<Dog> dogList = new ArrayList<>();

    
// 7.3 metod
    private Dog findDog(String dogName) {

        dogName = dogName.strip();

        for(Dog dog: dogList) {
            if(dog.getName().equalsIgnoreCase(dogName))
            return dog;
        }

        return null;

    }



    public void removeDog() {

        String dogName = inputReader.stringReader("Enter the name of the dog");
        
        Dog dog = findDog(dogName);

        if(dog == null) {
            System.out.println("Error: no such dog");
        }else {
            dogList.remove(dog);
            System.out.println(dog.getName() + " is removed from the register");
        }
    }


}
