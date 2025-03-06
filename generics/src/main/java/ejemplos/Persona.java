package ejemplos;

public class  Persona {

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

    public static void presentar(Persona _this) {

        System.out.println("Hola, soy " + _this.nombre);
    }

    public void presentar() {
        System.out.println("Hola, soy " + this.nombre);
    }

    public void decirQueSoy() {
        System.out.println("Hola, soy una persona");
    }



}

class Demo {
    public static void main(String[] args) {
        Persona jose = new Persona("Jose");
        Persona maria = new Persona("Maria");

        jose.decirQueSoy();
        maria.decirQueSoy();

        jose.saludar();
        maria.saludar();

        jose.getNombre();
        maria.getNombre();

        Persona.saludar(jose);
        Persona.saludar(maria);

        Persona.presentar(jose);
        Persona.presentar(maria);
        jose.presentar();
        maria.presentar();

        stringDemo();

        new Persona("Pedro").saludar();
    }

    static void stringDemo() {
        String nombre = new String("Jose");
        String result = nombre.toUpperCase();

        String texto = new String("+---+");
        String repeated = new String("-.-").repeat(4);
        System.out.println(repeated);

        String valor = String.format("%d - %d", 15, 30);
        System.out.println(valor);

        String frase = String.join(", ", "Hola", "Rojo", "Adios");
        System.out.println(frase);
    }
}
