package exercises.events;

/**
 * Person es una clase que genera eventos cuando cambia su estado
 * Cuando cambia el atributo Name se dispara el evento OnNameChanged
 * Cuando cambia la persona cumple una año se dispara el evento OnYearTurned
 */
public class Person {
    private String name;    //Nombre de la persona
    private int age;        //Edad en años de la persona

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * Llamar a este metodo provoca indirectamente se que lance el evento OnYearTurned
     */
    void turnYear() {       //Metodo cumplir un año
        System.out.println(getName() + " está cumpliendo años...");
        age++;
        // LLama al metodo que dispara el evento OnYearTurned
        // Como no se adjunta junto al evento información adicional
        // pasamos la referencia a la instancia estática empty declarada con tal fin en la clase EventArgs
        onYearTurned(EventArgs.EMPTY);
    }

    //Campo referencia a un objeto de tipo Event para el evento onYearTurned
    private final Event<Person, EventArgs> onYearTurned = new Event<>();

    //Getter para que el codigo que usa objetos persona pueda acceder a la referencia al evento
    public Event<Person, EventArgs> getOnYearTurned() {
        return this.onYearTurned;
    }

    public void addOnYearTurnedHandler(EventHandler<Person, EventArgs> yearTurnedHandler) {
        this.onYearTurned.addHandler(yearTurnedHandler);
    }

    public void removeOnYearTurnedHandler(EventHandler<Person, EventArgs> yearTurnedHandler) {
        this.onYearTurned.removeHandler(yearTurnedHandler);
    }

    //Metodo para disparar el evento YearTurned
    //Es mejor no invocar el metodo raiseEvent del evento directamente sino hacerlo a traves de la llamada
    //a un metodo protected que se encargue de lanzarlo
    //De esta manera las clases derivadas pueden reemplazar el metodo virtual onNombreEvento y controlar
    //si se lanza o no
    protected void onYearTurned(EventArgs e) {
        this.onYearTurned.raiseEvent(this, e);
    }

    public String getName() {
        return name;
    }

    //Setter del campo Name
    //Cuando se llama a este setter se cambia el valor del campo nombre
    //y se llama al metodo que lanza el evento NamedChanged
    public void setName(String name) {
        System.out.println(getName() + " está cambiando de nombre ...");
        String oldName = this.name;
        this.name = name;
        //new NameChangedEventArgs() { { this.oldName = oldName, this.newName = name}};
        //En este caso se propocina un objeto derivado de EventArgs (NameChangedEventArgs)
        //este objeto se instacia y se da valor a los campos que guardar la info que queremos
        //proporcionar al manejador de eventos (el nombre viejo y el nuevo)
        onNameChanged(new NameChangedEventArgs(oldName, this.name));
    }

    private final Event<Person, NameChangedEventArgs> nameChanged = new Event<Person, NameChangedEventArgs>();

    public Event<Person, NameChangedEventArgs> getOnNameChanged() {
        return nameChanged;
    }

    public void addOnNameChangedHandler(EventHandler<Person, NameChangedEventArgs> namedChangedHandler) {
        nameChanged.addHandler(namedChangedHandler);
    }

    public void removeOnNameChangedHandler(EventHandler<Person, NameChangedEventArgs> namedChangedHandler) {
        nameChanged.removeHandler(namedChangedHandler);
    }

    protected void onNameChanged(NameChangedEventArgs e) {
        nameChanged.raiseEvent(this, e);
    }


    public int getAge() {
        return age;
    }


    /**
     * Clase que extiende la clase base de los argumentos de evento
     * para agrupar mediante campos de la clase la info adicional
     * que se quiere proporcionar junto con el evento
     * En este caso la info es el antiguo nombre y el nuevo
     * cuando se produzca el evento NamedChanged
     * La sugerencia para el nombre es NombreEvento + EventArgs como sufijo
     * Por tanto NamedChangedEventArgs
     */

    /*public class NameChangedEventArgs extends EventArgs {
        protected String oldName; //Campo con la info del nombre antes del cambio
        protected String newName; //Nombre despues del cambio

        public NameChangedEventArgs(String oldName, String newName) {
            this.oldName = oldName;
            this.newName = newName;
        }

        public String getOldName() {
            return oldName;
        }

        public String getNewName() {
            return newName;
        }
    }*/


    /**
     * Podemos usar el nuevo tipo record de Java si lo unico que queremos es agrupar datos de en
     * un objeto inmutable
     *
     * @param getOldName
     * @param getNewName
     */
    public record NameChangedEventArgs(String getOldName, String getNewName) implements EventArgs {
       /* public String getOldName() {
            return oldName;
        }

        public String getNewName() {
            return newName;
        }*/
    }

}
