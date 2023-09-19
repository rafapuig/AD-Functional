package exercises.events;

/**
 * Esta interfaz funcional determina la firma de los metodos manejadores de eventos
 * @param <S> referencia al objeto que ha lanzado el evento
 * @param <T> objeto que agrupa la informacion adicional asociada al evento
 */
@FunctionalInterface
public interface EventHandler<S, T extends EventArgs> {

    void invoke(S sender, T eventArgs);
}
