package exercises;

public class PersonEventsTest {

    public static void main(String[] args) {

        Person person = new Person();

        EventHandler<Person, EventArgs> yearTurnedHandler1 =
                (sender, eventArgs) -> {
                    System.out.println("sender.getName() = " + sender.getName());
                    System.out.println("sender.getAge() = " + sender.getAge());
                    System.out.println("Cumpleaños feliz lambda");
                };

        EventHandler<Person, EventArgs> yearTurnedHandler2 =
                new EventHandler<Person, EventArgs>() {
                    @Override
                    public void invoke(Person sender, EventArgs eventArgs) {
                        System.out.println("sender.getName() = " + sender.getName());
                        System.out.println("sender.getAge() = " + sender.getAge());
                        System.out.println("Cumpleaños feliz anonimo");
                    }
                };

        EventHandler<Person, EventArgs> yearTurnedHandler3 =
                PersonEventsTest::person_yearTurned;

        EventHandler<Person, EventArgs> yearTurnedHandler4 =
                (sender, eventArgs) -> person_yearTurned(sender, eventArgs);


        person.getYearTurned().addHandler(yearTurnedHandler1);
        person.getYearTurned().addHandler(yearTurnedHandler2);
        person.getYearTurned().addHandler(yearTurnedHandler3);
        person.getYearTurned().addHandler(yearTurnedHandler4);
                ;

        person.turnYear();

        person.getNameChanged().addHandler(new EventHandler<Person, Person.NameChangedEventArgs>() {
            @Override
            public void invoke(Person sender, Person.NameChangedEventArgs eventArgs) {
                System.out.println("sender.getName() = " + sender.getName());
                System.out.println("eventArgs.getOldName() = " + eventArgs.getOldName());
                System.out.println("eventArgs.getNewName() = " + eventArgs.getNewName());
            }
        });

        person.setName("Armando Bronca");

        person.getYearTurned().removeHandler(yearTurnedHandler1);
        person.getYearTurned().removeHandler(yearTurnedHandler2);
        person.getYearTurned().removeHandler(yearTurnedHandler3);
        person.getYearTurned().removeHandler(yearTurnedHandler4);

        person.turnYear();
    }

    static void person_yearTurned(Person p, EventArgs e) {
        System.out.println("La persona " + p.getName() + " acaba de cumplir "
                + p.getAge() + " años.");
    }
}
