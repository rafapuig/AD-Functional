package exercises.events;

import exercises.events.lambda.LambdaUtils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Handler;

/**
 * Clase Event para gestionar una lista de manejadores de evento añadiendo y quitando de la lista
 *
 * @param <S> Sender o sujeto que provoca el evento, será una clase que añade un campo de tipo Event
 * @param <T> EventArgs o un subtipo que contiene los datos odicionales que se quieren propocionar junto con el evento
 */
public class Event<S, T extends EventArgs> {

    //Lista interna donde se van a registrar los controladores de evento
    private List<EventHandler<S, T>> handlers;

    public Event() {
        this.handlers = new LinkedList<>();
    }

    //Añade un manejador de evento al evento
    public void addHandler(EventHandler<S, T> handler) {
        handlers.add(handler);
    }

    //Elimina de la lista de manejadores de eventos el manejador proporcionado
    public void removeHandler(EventHandler<S, T> handler) {
        //handlers.remove(handler);

        /*handlers.removeIf(item -> LambdaUtils.isLambda(handler) ?
                LambdaUtils.equalLambdaCode(item, handler) :
                item.equals(handler));*/
        handlers.removeIf(item -> LambdaUtils.equals(item, handler));
    }

    public void raiseEvent(S sender, T eventArgs) {
        /*for (EventHandler<S, T> handler : handlers) {
            handler.invoke(sender, eventArgs);
        }*/
        handlers.forEach(handler -> handler.invoke(sender, eventArgs));
    }

    public List<EventHandler<S, T>> getHandlers() {
        return handlers;
    }
}
