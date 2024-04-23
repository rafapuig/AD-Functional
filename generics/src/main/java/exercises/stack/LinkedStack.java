package exercises.stack;

import java.util.Collection;
import java.util.Iterator;

/**
 * Implementación de la pila genérica Stack<T> como una lista enlazada de nodos
 */
public class LinkedStack<T> implements Stack<T> {

    //Clase anidada Node<T> genérica
    //Esta clase interna está declarada como estática y, por tanto, no recibe la referencia al objeto
    //de clase externa (LinkedStack) que lo crea
    static class Node<T> {
        T data; // El dato que guarda el nodo será de tipo del parámetro T
        Node<T> link; //El campo link guarda una referencia al objeto Node siguiente

        //El constructor de objetos Node<T> necesita como argumentos:
        //La información a almacenar (que será de tipo T)
        //La referencia al nodo que le sucede en la pila (el node que queda apilado por debajo de él)
        public Node(T data, Node<T> link) {
            this.data = data;
            this.link = link;
        }
    }

    //El único campo que necesita un objeto LinkedStack es la referencia al nodo cima (o cabeza)
    //de la sucesión, ya que a partir de este se puede acceder mediante el campo link al siguiente
    //y desde el campo link del siguiente al siguiente, y asi sucesivamente
    Node<T> head;

    //Constructor por defecto de la LinkedStack
    public LinkedStack() {
        head = null;
    }

    //Construir una pila a partir de una colección de elementos
    public LinkedStack(Collection<T> collection) {
        this(); //Llamamos al constructor por defecto (sin argumentos)
        for(T elem : collection) {  //Y recorremos la colección
            push(elem);             //para ir apilando elemento a elemento
        }
    }

    //Apilar
    public void push(T info) {
        //Instanciar un nuevo nodo con la info y su enlace al nodo siguiente (que es la antigua cabeza)
        Node<T> node = new Node<>(info, head);
        head = node;        //Convertirlo en el nuevo nodo cabeza
    }

    //Obtener el valor de la cima sin modificar la pila
    public T peek() {
        if (!isEmpty()) {  //Comprobamos primero si la pila está vacía
            return peekCore(); // Ya que el método peekCore provoca una excepción si
        }                       // se llama cuando la pila está vacía
        return null;
    }

    private T peekCore() {
        return head.data;
    } //Devolver la info del nodo cabeza

    public T pop() {
        if (!isEmpty()) {
            T data = peekCore();
            //Para desapilar, // hacemos que la nueva cabeza pase a ser el nodo siguiente de la cabeza actual
            head = head.link;
            return data;
        } else return null;
    }

    public boolean isEmpty() {
        return head == null;
    } //La pila está vacía si el campo head es null


    //Clase anidada (interna) que implementa el iterador de la LinkedStack
    //Cuando se instancia, tendrá una referencia al objeto de clase externa (LinkedStack) que lo instanció
    //Y que es accesible mediante LinkedStack.this
    class LinkedStackIterator implements Iterator<T> {

        Node<T> current;  //referencia al nodo actual en el que está posicionada el iterador

        LinkedStackIterator() {
            current = LinkedStack.this.head; //La clase interna puede acceder a la instancia externa
        } //Al inicial nos situamos en la cabeza

        @Override
        public boolean hasNext() {
            return current != null;
        } //hay más elementos si la referencia current no es null

        @Override
        public T next() { // Consumimos el dato y avanzamos el iterador al nodo siguiente
            T data = current.data;
            current = current.link;
            return data;
        }
    }

    //Implementación del método iterator de la interfaz Iterator
    @Override
    public Iterator<T> iterator() {
        return new LinkedStackIterator();
    }
}
