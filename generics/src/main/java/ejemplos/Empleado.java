package ejemplos;

public class Empleado extends Persona {
    public Empleado(String nombre) {
        super(nombre);
    }

    @Override
    public void decirQueSoy() {
        System.out.println("Soy un empleado muy eficiente");
    }
}

class DemoEmp {
    public static void main(String[] args) {
        Persona jose = new Empleado("Jose");
        Persona maria = new Empleado("Maria");

        jose.decirQueSoy();
        maria.decirQueSoy();
    }
}
