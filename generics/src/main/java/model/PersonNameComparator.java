package model;

import model.Person;

import java.util.Comparator;

public class PersonNameComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        return Comparator
                .<Person, String>comparing(p -> p.getName())
                .compare(p1, p2);
    }
}
