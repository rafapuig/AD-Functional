package exercises;

public interface EventHandler<S, T extends EventArgs> {

    void invoke(S sender, T eventArgs);
}
