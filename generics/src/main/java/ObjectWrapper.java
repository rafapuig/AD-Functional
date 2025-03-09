/**
 * La clase ObjectWrapper puede almacenar la referencia a cualquier tipo de objeto
 * Hace de envoltorio de un objeto
 */
public class ObjectWrapper {

    /**
     * Campo para almacenar la referencia a un objeto cuyo tipo real puede ser cualquiera
     * ya que la clase Object es la clase raíz en la jerarquía de herencia
     */
    private Object ref;

    public ObjectWrapper(Object ref) {
        this.ref = ref;
    }

    public Object get() {
        return ref;
    }

    public void set(Object ref) {
        this.ref = ref;
    }
}

class ObjectWrapperDemo {

    public static void main(String[] args) {

        ObjectWrapper wrapper = new ObjectWrapper("Hola mundo");

        // Aunque sabemos que hemos almacenado un String en el envoltorio
        // Tenemos que hacer el casting a String cuando llamamos al metodo get
        // ya que nos devuelve una referencia de tipo Object
        String texto = (String) wrapper.get(); // Downcasting (siempre explicito)
        System.out.println(texto);

        wrapper.set("Otro texto");
        texto = (String) wrapper.get(); // Cada vez tenemos que hacer el downcasting
        System.out.println(texto);

        // Esto no es deseable, lo ideal sería que el compilador detectara esta instrucción como un error
        wrapper.set(Integer.valueOf(77)); // Podemos establecer la referencia a un objeto de tipo Integer
        try {
            // Intento de hacer un casting a String, pero ahora el tipo real del objeto es Integer
            String texto2 = (String) wrapper.get(); //El downcasting fallará ClassCastException
        } catch (ClassCastException e) {
            System.out.println(e);
        }
    }

}
