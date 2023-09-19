package exercises.stack;

import java.util.Collection;
import java.util.Iterator;

//Implementacion de la pila generica Stack<T> como una lista enlazada de nodos
public class LinkedStack<T> implements Stack<T> {


    //Clase que implementa el iterador de la LinkedStack
    class LinkedStackIterator implements Iterator<T> {

        Node<T> current;  //referencia al nodo actual en el que esta situado el iterador

        LinkedStackIterator() {
            current = head;
        } //Al inicial nos situamos en la cabeza

        @Override
        public boolean hasNext() {
            return current != null;
        } //hay mas si la refencia current no es null

        @Override
        public T next() { // Consumimos el dato y avanzamos el iterador al nodo siguiente
            T data = current.data;
            current = current.link;
            return data;
        }
    }

    //Implementacion del método iterator de la interfaz Iterator
    @Override
    public Iterator<T> iterator() {
        return new LinkedStackIterator();
    }


    //Clase anidada Node<T> generica
    static class Node<T> {
        T data; // El dato que guarda el nodo será de tipo del parametro T
        Node<T> link; //El campo link guarda una referencia al objeto Node siguiente

        //El constructor de objetos Node<T> necesita como argumentos:
        //La informacion a almancenar (que será de tipo T)
        //La referencia al nodo que le sucede en la pila (el que queda por debajo de el)
        public Node(T data, Node<T> link) {
            this.data = data;
            this.link = link;
        }
    }

    //El único campo que necesita un objeto Stack es la refencia al nodo cima (o cabeza)
    //de la sucesion, ya que a partir de este se puede acceder mediante el campo link al siguiente
    //y desde el campo link del siguente al siguente, y asi sucesivamente
    Node<T> head;

    //Constructor por defecto de la LinkedStack
    public LinkedStack() {
        head = null;
    }

    //Construir una pila a partir de una coleccion de elementos
    public LinkedStack(Collection<T> collection) {
        this(); //Llamamos al contructor por defecto (sin argumentos)
        for(T elem : collection) {  //Y recorremos la coleccion
            push(elem);             //para ir apilando elemento a elemento
        }
    }

    //Apilar
    public void push(T info) {
        //Instanciar un nuevo nodo con la info y su enlace al siguente es la antigua cabeza
        Node<T> node = new Node<>(info, head);
        head = node;        //Hacerlo la nueva cabeza
    }

    public T peek() {
        if (!isEmpty()) {  //Comprobamos si esta vacia la pila
            return peekCore(); //El metodo peekCore provocaria una excepcion si
        }                       // se llama y la pila esta vacia
        return null;
    }

    private T peekCore() {
        return head.data;
    } //Devolver la info del nodo cabeza

    public T pop() {
        if (!isEmpty()) {
            T data = peekCore();
            head = head.link; //Para desapilar, hacemos que la cabeza sea el nodo siguiente al a cabeza
            return data;
        } else return null;
    }

    public boolean isEmpty() {
        return head == null;
    } //La pila esta vacia si el campo head es null
}
