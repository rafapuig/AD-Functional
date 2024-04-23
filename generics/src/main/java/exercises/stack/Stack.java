package exercises.stack;

//La interfaz Stack es una interface genérica con un parámetro de tipo T
//Extiende la interfaz Iterable cuyo parámetro de tipo, al ser también T,
//será del mismo tipo que el parámetro T de Stack
public interface Stack<T> extends Iterable<T> {
    //El método push sirve para apilar un nuevo dato en la pila,
    // es decir, ponerlo encima del que antes estaba en la cima
    public void push(T info); //public es redundante dado que un método abstracto es implícitamente de ámbito public

    //El método peek sirve para poder leer el valor de la cima de la pila, sin sacarlo
    T peek();

    //El método pop, saca el elemento en la cima de la pila
    T pop();

    //Con el método isEmpty se debe de devolver true si la pila esta vacia
    boolean isEmpty();
}
