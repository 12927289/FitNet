package e.matthew.fitnet;

public class UserProfile {

    public String name;
    public int age;
    public int height;
    public int weight;
    public String gender;

    public UserProfile() {

    }

    public UserProfile (String name, int age, int height, int weight, String gender) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }
}