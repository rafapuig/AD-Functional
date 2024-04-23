package model;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

public class Person implements Comparable<Person> {

    private int id;
    private String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "model.Person{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Person person)) return false; //Pattern variable
        return this.getId() == person.getId() &&
               this.getName().equals(person.getName());
    }

    /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false; //No pattern variable
        Person person = (Person) o;
        return getId() == person.getId() && getName().equals(person.getName());
    }*/

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

     /*@Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getName().hashCode();
        return result;
    }*/

    @Override
    public int compareTo(Person other) {
        return compareToImpl1(other);
        //compareToImpl2(other);
        //compareToImpl3(other);
        //compareToImpl4(other);
    }

    public int compareToImpl1(Person other) {
        return this.getName().compareTo(other.getName());
    }

    public int compareToImpl2(Person other) {
        return getNameComparator().compare(this, other);
    }

    public int compareToImpl3(Person other) {
        return Comparator
                .<Person, String>comparing(person -> person.getName()) // Expresión lambda
                .compare(this, other);
    }

    public int compareToImpl4(Person other) {

        Function<Person, String> personComparatorByName = person ->
                person.getName();

        return Comparator
                .<Person, String>comparing(personComparatorByName) // Funcional
                .compare(this, other);
    }





    public static class NameComparator implements Comparator<Person> {
        @Override
        public int compare(Person p1, Person p2) {

            /*return Comparator
                .<Person, String>comparing(p -> p.getName())
                .compare(p1, p2);*/

            return p1.getName().compareTo(p2.getName());
        }
    }

    /* Singleton */
    private static final NameComparator nameComparator = new NameComparator();

    public static Comparator<Person> getNameComparator() {
        return nameComparator;
    }
}
