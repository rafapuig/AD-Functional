package exercises.events;

import java.util.*;

public class PersonEventsTest {
    public static void main(String[] args) {
        //testYearTurnedEvent();
        //testNameChangedEvent();
        testPeopleEvents();
        //testPeopleTurnYearEvent();
        //testPeopleChangeNameEvent();
        //testSpecifyHandlers();
    }

    //Metodo callback
    //Este metodo se puede usar como función manejadora de eventos porque su firma
    //es compatible con la firma del método invoke de la interfaz EventHandler<S,T>
    static void person_yearTurned(Person p, EventArgs e) {
        System.out.println("La persona '" + p.getName() + "' acaba de cumplir "
                + p.getAge() + " años.");
    }

    //Metodo callback
    static void person_nameChanged(Person sender, Person.NameChangedEventArgs e) {
        System.out.println("La persona '" + sender.getName() + "' ha cambiado de nombre!!!");
        System.out.println("Nombre anterior = " + e.getOldName());
        System.out.println("Nombre actual   = " + e.getNewName());
    }


    static void testYearTurnedEvent() {
        Person aitor = new Person("Aitor Tilla", 30);
        Person belen = new Person("Belen Tilla", 21);

        EmptyEventHandler<Person> personTurnYearCallback =
                (p, e) -> System.out.println(p.getName() + " ha cumplido " + p.getAge() + " años!");

        aitor.addOnYearTurnedHandler(personTurnYearCallback);
        belen.addOnYearTurnedHandler(personTurnYearCallback);

        aitor.turnYear();
        belen.turnYear();

        aitor.removeOnYearTurnedHandler(personTurnYearCallback);

        aitor.turnYear();
        belen.turnYear();

        belen.removeOnYearTurnedHandler(personTurnYearCallback);

        aitor.turnYear();
        belen.turnYear();

        System.out.println(aitor.getName() + " tiene " + aitor.getAge() + " años");
        System.out.println(belen.getName() + " tiene " + belen.getAge() + " años");
    }

    static void testNameChangedEvent() {
        Person amador = new Person("Amador Denador", 15);
        Person sandra = new Person("Sandra Matica", 28);

        EventHandler<Person, Person.NameChangedEventArgs> personNameChangedCallback =
                PersonEventsTest::person_nameChanged;

        amador.addOnNameChangedHandler(personNameChangedCallback);
        sandra.addOnNameChangedHandler(personNameChangedCallback);

        amador.setName("Federico Jones");
        sandra.setName("Sandra Gones");

        amador.removeOnNameChangedHandler(personNameChangedCallback);
        sandra.removeOnNameChangedHandler(personNameChangedCallback);

        amador.setName("Amador Mido");
        sandra.setName("Sandra Cula");

        System.out.println(amador.getName());
        System.out.println(sandra.getName());
    }

    static void testPeopleEvents() {
        Person[] people = new Person[]{
                new Person("Esther Malgin", 67),
                new Person("Armando Bronca", 95),
                new Person("Alfonso Brado", 36)
        };

        for (Person p : people) {
            p.addOnYearTurnedHandler(PersonEventsTest::person_yearTurned);
        }

        List.of(people).forEach(Person::turnYear);

        Arrays.stream(people).forEach(
                p -> p.removeOnYearTurnedHandler(PersonEventsTest::person_yearTurned));
    }

    static void testPeopleTurnYearEvent() {
        List<Person> people = List.of(
                new Person("Esther Malgin", 67),
                new Person("Armando Bronca", 95),
                new Person("Alfonso Brado", 36)
        );

        List<EmptyEventHandler<Person>> turnYearHandlers = List.of(
                PersonEventsTest::person_yearTurned,
                (p, e) -> System.out.println("Feliz Cumpleaños " + p.getName())
        );

        people.forEach(person -> person.getOnYearTurned().getHandlers().addAll(turnYearHandlers));

        people.forEach(Person::turnYear);

        people.forEach(person -> person.getOnYearTurned().getHandlers().removeAll(turnYearHandlers));
    }

    static void testPeopleChangeNameEvent() {
        List<Person> people = List.of(
                new Person("Aitor Tilla", 67),
                new Person("Armando Bronca", 95),
                new Person("Alfonso Brado", 36)
        );

        Deque<String> names = new ArrayDeque<>(List.of("Victor Nado", "Hector Menta", "Raul Timo"));

        List<EventHandler<Person, Person.NameChangedEventArgs>> handlers = List.of(
                PersonEventsTest::person_nameChanged,
                (p, e) -> System.out.println("Hola " + p.getName() + ", antes " + e.getOldName())
        );

        people.forEach(person -> person.getOnNameChanged().getHandlers().addAll(handlers));

        people.forEach(person -> person.setName(names.pop()));

        people.forEach(person -> person.getOnNameChanged().getHandlers().removeAll(handlers));
    }

    static void testSpecifyHandlers() {
        Person person = new Person("Aitor Tilla", 50);
        Person belen = new Person("Belen Tilla", 45);

        //Crear un EventHandler mediante una expresion lambda (tema 3)
        EventHandler<Person, EventArgs> yearTurnedHandler1 =
                (sender, eventArgs) -> {
                    System.out.println("sender.getName() = " + sender.getName());
                    System.out.println("sender.getAge() = " + sender.getAge());
                    System.out.println("Cumpleaños feliz (lambda)");
                };

        //Crear un EventHandler mediante un tipo anonimo
        EventHandler<Person, EventArgs> yearTurnedHandler2 =
                new EventHandler<Person, EventArgs>() {
                    @Override
                    public void invoke(Person sender, EventArgs eventArgs) {
                        System.out.println("sender.getName() = " + sender.getName());
                        System.out.println("sender.getAge() = " + sender.getAge());
                        System.out.println("Cumpleaños feliz (tipo anonimo)");
                    }
                };

        //Crearlo mediante una referencia a metodo (tema 3)
        EventHandler<Person, EventArgs> yearTurnedHandler3 =
                PersonEventsTest::person_yearTurned;

        //Crearlo mediante una lambda expresion equivalente a la referencia a metodo (tema 3)
        EventHandler<Person, EventArgs> yearTurnedHandler4 =
                (sender, eventArgs) -> person_yearTurned(sender, eventArgs);


        //Añadir (suscribir) como escuchadores (manejadores) del evento cumplir años del objeto persona
        person.getOnYearTurned().addHandler(yearTurnedHandler1);
        person.getOnYearTurned().addHandler(yearTurnedHandler2);
        person.getOnYearTurned().addHandler(yearTurnedHandler3);
        person.getOnYearTurned().addHandler(yearTurnedHandler4);

        //O tambien
        person.addOnYearTurnedHandler(PersonEventsTest::person_yearTurned);


        //ahora la persona cumple años
        person.turnYear();
        // Por tanto, se invocará a todos los manejadores de eventos registrados


        //Crear un manejador de eventos para el evento NamedChanged
        EventHandler<Person, Person.NameChangedEventArgs> nameChangedHandler1 =
                new EventHandler<Person, Person.NameChangedEventArgs>() {
                    @Override
                    public void invoke(Person sender, Person.NameChangedEventArgs eventArgs) {
                        System.out.println("Version del manejador con tipo anonimo");
                        System.out.println("sender.getName() = " + sender.getName());
                        System.out.println("eventArgs.getOldName() = " + eventArgs.getOldName());
                        System.out.println("eventArgs.getNewName() = " + eventArgs.getNewName());
                    }
                };

        EventHandler<Person, Person.NameChangedEventArgs> nameChangedHandler2 =
                (sender, eventArgs) -> {
                    System.out.println("Version del manejador con expresion lambda");
                    System.out.println("sender.getName() = " + sender.getName());
                    System.out.println("eventArgs.getOldName() = " + eventArgs.getOldName());
                    System.out.println("eventArgs.getNewName() = " + eventArgs.getNewName());
                };

        //Añadir manejadores de eventos para el evento cambio de nombre del objeto persona
        person.getOnNameChanged().addHandler(nameChangedHandler1);
        person.getOnNameChanged().addHandler(nameChangedHandler2);

        //Y tambien
        person.addOnNameChangedHandler(PersonEventsTest::person_nameChanged);

        //Cambiamos el nombre de la persona
        person.setName("Armando Bronca Segura"); //Se invocara a los manejadores del evento cambio de nombre

        //Desregistrar los manejadores de eventos
        person.getOnNameChanged().removeHandler(nameChangedHandler1);
        person.getOnNameChanged().removeHandler(nameChangedHandler2);

        //Y tambien
        person.removeOnNameChangedHandler(PersonEventsTest::person_nameChanged);


        //Desregistramos los 4 manejadores de evento que escuchan el evento cumplir años
        person.getOnYearTurned().removeHandler(yearTurnedHandler1);
        person.getOnYearTurned().removeHandler(yearTurnedHandler2);
        //person.getYearTurned().removeHandler(yearTurnedHandler3);
        person.getOnYearTurned().removeHandler(PersonEventsTest::person_yearTurned);

        //person.getYearTurned().removeHandler(yearTurnedHandler4);
        person.getOnYearTurned().removeHandler(
                (sender, eventArgs) -> person_yearTurned(sender, eventArgs));

        //O tambien
        person.removeOnYearTurnedHandler(PersonEventsTest::person_yearTurned);
        person.removeOnYearTurnedHandler((s, e) -> person_yearTurned(s, e));

        //ahora cuando la persona cumple años ya no se invoca a ningun manejador
        System.out.println("Una vez eliminados todos los manejadores...");
        person.turnYear();
    }
}