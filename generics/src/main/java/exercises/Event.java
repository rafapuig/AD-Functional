package exercises;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Event<S, T extends EventArgs> {

    List<EventHandler<S, T>> handlers;

    public Event() {
        this.handlers = new LinkedList<>();
    }

    public void addHandler(EventHandler<S, T> handler) {
        handlers.add(handler);
    }

    public void removeHandler(EventHandler<S, T> handler) {
        handlers.remove(handler);
    }

    public void raiseEvent(S sender, T eventArgs) {
        for (EventHandler<S, T> handler : handlers) {
            handler.invoke(sender, eventArgs);
        }
    }
}
