package exercises.events;

public class PersonEventsTest {

    public static void main(String[] args) {

        Person person = new Person();

        //Crear un EventHandler mediante una expresion lambda (tema 3)
        EventHandler<Person, EventArgs> yearTurnedHandler1 =
                (sender, eventArgs) -> {
                    System.out.println("sender.getName() = " + sender.getName());
                    System.out.println("sender.getAge() = " + sender.getAge());
                    System.out.println("Cumpleaños feliz lambda");
                };

        //Crear un EventHandler mediante un tipo anonimo
        EventHandler<Person, EventArgs> yearTurnedHandler2 =
                new EventHandler<Person, EventArgs>() {
                    @Override
                    public void invoke(Person sender, EventArgs eventArgs) {
                        System.out.println("sender.getName() = " + sender.getName());
                        System.out.println("sender.getAge() = " + sender.getAge());
                        System.out.println("Cumpleaños feliz anonimo");
                    }
                };

        //Crearlo mediante una referencia a metodo (tema 3)
        EventHandler<Person, EventArgs> yearTurnedHandler3 =
                PersonEventsTest::person_yearTurned;

        //Crearlo mediante una lambda expresion equivalente a la referencia a metodo (tema 3)
        EventHandler<Person, EventArgs> yearTurnedHandler4 =
                (sender, eventArgs) -> person_yearTurned(sender, eventArgs);


        //Añadir (suscribir) como escuchadores (manejadores) del evento cumplir años del objeto persona
        person.getYearTurned().addHandler(yearTurnedHandler1);
        person.getYearTurned().addHandler(yearTurnedHandler2);
        person.getYearTurned().addHandler(yearTurnedHandler3);
        person.getYearTurned().addHandler(yearTurnedHandler4);


        //ahora la persona cumple años
        person.turnYear();
        // Por tanto, se invocará a todos los manejadores de eventos registrados


        //Añadir un manejador de eventos para el evento cambio de nombre del objeto persona
        person.getNameChanged().addHandler(new EventHandler<Person, Person.NameChangedEventArgs>() {
            @Override
            public void invoke(Person sender, Person.NameChangedEventArgs eventArgs) {
                System.out.println("sender.getName() = " + sender.getName());
                System.out.println("eventArgs.getOldName() = " + eventArgs.getOldName());
                System.out.println("eventArgs.getNewName() = " + eventArgs.getNewName());
            }
        });

        //Cambiamos el nombre de la persona
        person.setName("Armando Bronca"); //Se invocara a los manejadores del evento cambio de nombre


        //Desregistramos los 4 manejadores de evento que escuchan el evento cumplir años
        person.getYearTurned().removeHandler(yearTurnedHandler1);
        person.getYearTurned().removeHandler(yearTurnedHandler2);
        person.getYearTurned().removeHandler(yearTurnedHandler3);
        person.getYearTurned().removeHandler(yearTurnedHandler4);

        //ahora cuando la persona cumple años ya no se invoca a ningun manejador
        person.turnYear();
    }



    //Metodo callback
    //Este metodo se puede usar como funcion manejadora de eventos porque su firma
    //Es compatible con la firma del método invoke del interface EventHandler<S,T>
    static void person_yearTurned(Person p, EventArgs e) {
        System.out.println("La persona " + p.getName() + " acaba de cumplir "
                + p.getAge() + " años.");
    }
}
