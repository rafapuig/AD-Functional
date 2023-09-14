//Un tipo (clase o interfaz) con parámetro(s) de tipo en su declaration
//se denomina tipo genérico

//Por convencion se usa T si el parametro es un tipo, E si es un Elemento
//K si es un clave (Key), V si es un Valor, N si es un numero

public class Wrapper<T> {

    private T ref;

    public Wrapper(T ref) {
        this.ref = ref;
    }

    public T get() {
        return this.ref;
    }

    public void set(T ref) {
        this.ref = ref;
    }

}


