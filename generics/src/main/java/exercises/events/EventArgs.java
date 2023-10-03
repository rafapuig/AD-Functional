package exercises.events;

/**
 * Clase base que se debe extender cuando se diseñe un nuevo tipo de
 * evento que necesite proporcionar informacion adicional asociada con el evento
 */
public interface EventArgs {

    public static final EventArgs EMPTY = new EventArgs() { };

    //static {
        //EMPTY = new EventArgs();
    //}

}
