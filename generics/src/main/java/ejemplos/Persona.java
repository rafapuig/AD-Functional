package ejemplos;

public class Persona {

    String nombre;

    public Persona(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void saludar() {
        //System.out.println("Hola, soy " + this.nombre);
        saludar(this);
    }

    public static void saludar(Persona persona) {
        //persona.saludar();
        System.out.println("Hola, soy " + persona.nombre);
    }

}

class Demo {
    public static void main(String[] args) {
        Persona jose = new Persona("Jose");
        Persona maria = new Persona("Maria");

        jose.saludar();
        maria.saludar();

        Persona.saludar(jose);
        Persona.saludar(maria);
    }
}
