package exercises.observer;

public class Person {

    private final Observable<String> name;
    private final Observable<Integer> age;

    public Person(String name, int age) {
        this.age = new Observable<>(age);
        this.name = new Observable<>(name);
    }

    Observable<String> getObservableName() {
        return this.name;
    }

    String getName() {
        return name.get();
    }

    void setName(String name) {
        this.name.set(name);
    }


    Observable<Integer> getObservableAge() {
        return this.age;
    }

    Integer getAge() {
        return age.get();
    }

    void setAge(Integer age) {
        this.age.set(age);
    }
}
