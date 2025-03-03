package exercises.pairs;

import java.util.StringJoiner;

public class Person {

    protected final String name;
    protected final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", getClass().getSimpleName() + "{", "}")
                .add("name='" + name + "'")
                .add("age=" + age)
                .toString();
    }
}
