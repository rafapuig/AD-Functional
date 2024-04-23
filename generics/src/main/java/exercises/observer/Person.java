package exercises.observer;

public class Person {

    final Observable<String> name = new Observable<>();
    final Observable<Integer> age = new Observable<>();

    String getName() {
        return name.get();
    }

    void setName(String name) {
        this.name.set(name);
    }

    Integer getAge() {
        return age.get();
    }

    void setAge(Integer age) {
        this.age.set(age);
    }
}
