/**
 * @author Omar Almassri omal7554
 */

public class Owner {

    private String name;
    private DogCatalog ownedDogs;

    public Owner(String name) {
        this.name = name.strip();
        ownedDogs = new DogCatalog();
    }

    public String getName() {
        return name;
    }

    public void recieveDog(Dog dog) {
        if (dog.getOwner() != null && !dog.getOwner().equals(this))
            return;

        if (!ownsDog(dog)) {
            ownedDogs.addDog(dog);
            dog.setOwner(this);
        }
    }

    public boolean ownsDog(Dog dog) {
        return ownedDogs.containsDog(dog);
    }

    public void removeDogFromOwner(Dog dog) {
        if (!ownsDog(dog))
            return;
        ownedDogs.removeDog(dog);
        dog.removeOwnerOfDog();
    }

    public DogCatalog getOwnedDogs() {
        return ownedDogs;
    }

    @Override
    public String toString() {
        return name + " [" + ownedDogs.allDogNames() + "]";
    }
}
