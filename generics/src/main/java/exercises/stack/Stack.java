package exercises.stack;

//La interfaz Stack es una clase generica con un parametro de tipo T
//Extiende la interfaz Iterable cuyo parametro de tipo al ser tambien T
//Será del mismo tipo que el parametro T de Stack
public interface Stack<T> extends Iterable<T> {
    //El metodo push sirve para apilar un nuevo dato en la pila,
    // es decir, ponerlo encima del que antes estaba en la cima
    public void push(T info); //public es redundante dado que todo metodo abstracto es implicitamente de ambito public

    //El metodo peek sirve para poder leer el valor de la cima de la pila, sin sacarlo
    T peek();

    //El metodo pop, saca el elemento en la cima de la pila
    T pop();

    //Con el metodo isEmpty se debe de devolver true si la pila esta vacia
    boolean isEmpty();
}
