// @author Omar Almassri omal7554

public class Dog {

    private final static double DACHSHUND_TAIL_LENGTH = 3.7;

    private String name;
    private String breed;
    private int age;
    private int weight;
    private double tailLength;
    private Owner owner;

    public Dog(String name, String breed, int age, int weight) {
        this.name = name.strip();
        this.breed = breed.toLowerCase().strip();
        this.age = age;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public int getAge() {
        return age;
    }

    public void increaseAge(int increaseAgeBy) {
        age = increaseAgeBy > 0 ? age + increaseAgeBy : age;
    }

    public int getWeight() {
        return weight;
    }

    public double getTailLength() { 
        if (breed.equals("tax") || breed.equals("dachshund")) {
            tailLength = DACHSHUND_TAIL_LENGTH;
        } else {
            tailLength = age * weight / 10.0;
        }
        return tailLength;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        if (this.owner != null)
            return;

        this.owner = owner;
        if (owner != null && !owner.ownsDog(this))
            owner.recieveDog(this);
    }

    public void removeOwnerOfDog() {
        if (owner == null)
            return;
        Owner tempOwner = owner;
        owner = null;
        tempOwner.removeDogFromOwner(this);
    }

    @Override
    public String toString() {
        String dogsOwner;
        if (owner != null)
            dogsOwner = ", owned by " + owner.getName();
        else
            dogsOwner = "";

        return "* " + name + " (" + breed + ", " + age + " years, " + weight + "kilo, " + getTailLength() + " cm tail"
                + dogsOwner + ")";
    }
}
