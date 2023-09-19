package exercises.events;

public class Person {

    private String name;    //Nombre de la persona
    private int age;        //Edad en años de la persona

    void turnYear() {       //Metodo cumplir un año
        age++;
        //LLama al metodo que dispara el evento YearTurned
        // Como no hace falta info adicional pasamos la referencia a la instancia
        // estatica declarada con tal fin en la clase EventArgs
        onYearTurned(EventArgs.empty);
    }


    //Campo referencia a un objeto de tipo Event para el evento yearTurned
    private final Event<Person, EventArgs> yearTurned = new Event<>();

    //Getter para que el codigo que usa objetos persona pueda acceder a la refencia al evento
    public Event<Person, EventArgs> getYearTurned() {
        return this.yearTurned;
    }

    //Metodo para disparar el evento YearTurned
    protected void onYearTurned(EventArgs e) {
        yearTurned.raiseEvent(this, e);
    }

    public String getName() {
        return name;
    }

    //Setter del campo Name
    //Cuando se llama a este setter se cambia el valor del campo nombre
    //y se llama al metodo que lanza el evento NamedChanged
    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        //new NameChangedEventArgs() { { this.oldName = oldName, this.newName = name}};
        //En este caso se propocina un objeto derivado de EventArgs (NameChangedEventArgs)
        //este objeto se instacia y se da valor a los campos que guardar la info que queremos
        //propocinar al manejador de eventos (el nombre viejo y el nuevo)
        onNameChanged(new NameChangedEventArgs(oldName, this.name));
    }

    private final Event<Person, NameChangedEventArgs> nameChanged = new Event<Person,NameChangedEventArgs>();

    public Event<Person, NameChangedEventArgs> getNameChanged() {
        return nameChanged;
    }

    protected void onNameChanged(NameChangedEventArgs e) {
        nameChanged.raiseEvent(this, e);
    }

    public int getAge() {
        return age;
    }


    /**
     * Clase que extiende la clase base de los arguemtos de evento
     * para agrupar mediante campos de la clase la info adicional
     * que se quiere proporcionar junto con el evento
     * En este caso la info es el antiguo nombre y el nuevo
     * cuando se produzca el evento NamedChanged
     * La sugerencia para el nombre es NombreEvento + EventArgs como sufijo
     * Por tanto NamedChangedEventArgs
     */
    public static class NameChangedEventArgs extends EventArgs {
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
    }

}
