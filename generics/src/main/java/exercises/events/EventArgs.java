package exercises.events;

/**
 * Clase base que se debe extender cuando se diseñe un nuevo tipo de
 * evento que necesite proporcionar informacion adicional asociada con el evento
 */
public class EventArgs {

    public static EventArgs empty;

    static {
        empty = new EventArgs();
    }

}
