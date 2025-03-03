package exercises.pairs;


import java.util.StringJoiner;

public class Employee extends Person {

    protected final int salary;

    public Employee(String name, int age, int salary) {
        super(name, age);
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Employee.class.getSimpleName() + "{", "}")
                .add("name='" + name + "'")
                .add("age=" + age)
                .add("salary=" + salary)
                .toString();
    }
}
