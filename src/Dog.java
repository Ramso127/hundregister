// @author Omar Almassri omal7554

public class Dog {

    private String name;
    private String breed;
    private int age;
    private int weight;
    private double tailLength;

    public Dog(String name, String breed, int age, int weight) {
        this.name = name.toLowerCase().strip();
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
        if(breed.equals("tax") || breed.equals("dachshund")) {
            tailLength = 3.7; // all dachshund has the taillength 3.7
        } else {
            tailLength = age * weight / 10.0;
        } return tailLength;
    }

    public String toString() {
        return "The dog " + name + " is of the " + breed + " breed, is " + age + " years old, weighs " + weight + " kilogram and has the tail length " + getTailLength() + ".";
    }
    
}
