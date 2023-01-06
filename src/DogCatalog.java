// @author Omar Almassri omal7554

public class DogCatalog {

    private Dog[] dogs = new Dog[10];
    private int amountOfDogs;

    public String allDogNames() {
        String dogNames = "[";
        for (int i = 0; i < amountOfDogs; i++) {
            if (i == amountOfDogs - 1)
                dogNames += dogs[i].getName() + "]";
            else
                dogNames += dogs[i].getName() + ", ";
        }
        return dogNames;
    }

    public void addDog(Dog dog) {

        if (dog == null || containsDog(dog))
            return;

        if (amountOfDogs == dogs.length) {
            // The Array is full, increase length with 1
            Dog[] newDogs = new Dog[dogs.length + 1];
            System.arraycopy(dogs, 0, newDogs, 0, amountOfDogs);

            dogs = newDogs;
        }
        dogs[amountOfDogs] = dog;
        amountOfDogs++;
    }

    public void removeDog(Dog dog) {

        if (dog == null || !(containsDog(dog))) {
            return;
        }

        for (int i = 0; i < amountOfDogs; i++) {
            if (dogs[i].equals(dog)) {
                // Remove the dog that has been found
                dogs[i] = null;
                amountOfDogs--;

                // Move all dogs to the right of the removed dog one step to the left
                for (int j = i; j < amountOfDogs; j++) {
                    dogs[j] = dogs[j + 1];
                }

                // End the loop once the dog has been removed
                break;
            }
        }

        // Decrease the size of the array if it is more than twice as large as the
        // number of dogs
        if (amountOfDogs * 2 < dogs.length) {
            Dog[] newDogs = new Dog[amountOfDogs];
            System.arraycopy(dogs, 0, newDogs, 0, amountOfDogs);
            dogs = newDogs;
        }
    }

    public boolean containsDog(Dog dog) {
        if (dog != null) {
            for (int i = 0; i < dogs.length; i++) {
                if (dogs[i] == dog)
                    return true;
            }
        }
        return false;
    }
}
