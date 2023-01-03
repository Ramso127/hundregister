// @author Omar Almassri omal7554

import java.util.ArrayList;

public class AssignmentSevenPointFour {
    
    private InputReader inputReader = new InputReader();

    private ArrayList<Dog> dogList = new ArrayList<>();


    private Dog findDog(String dogName) {

        dogName = dogName.strip();

        for(Dog dog: dogList) {
            if(dog.getName().equalsIgnoreCase(dogName))
            return dog;
        }

        return null;

    }


    public void increaseAge() {

        String dogName = inputReader.stringReader("Enter the name of the dog");

        Dog dog = findDog(dogName);


        if(dog == null) {
            System.out.println("Error: no such dog");
        }else {
            dog.increaseAge(1);
            System.out.println(dog.getName() + " is now one year older");
        }

    }
}











// // @author Omar Almassri omal7554

// public class AssignmentSevenPointFour {
    
//     private AssignmentSevenPointThree sevenThree = new AssignmentSevenPointThree();
//     private InputReader inputReader = new InputReader();

//     public void increaseAge() {

//         String dogName = inputReader.stringReader("Enter the name of the dog");

//         Dog dog = sevenThree.findDog(dogName);

//         if(dog == null) {
//             System.out.println("Error: no such dog");
//         }else {
//             dog.increaseAge(1);

//             System.out.println(dog.getName() + " is now one year older");
//         }

//     }
// }

   
