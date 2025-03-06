package ejemplos;

public class Punto {

    int x,y;

    public Punto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Punto sumar(Punto punto) {

        int x = this.x + punto.x;
        int y = this.y + punto.y;
        return new Punto(x, y);

    }

    public static Punto sumar(Punto punto1, Punto punto2) {
        int x = punto1.x + punto2.x;
        int y = punto1.y + punto2.y;
        return new Punto(x, y);
    }

}

class PuntoDemo {
    public static void main(String[] args) {
        Punto p1 = new Punto(1, 5);
        Punto p2 = new Punto(3, 2);

        p1.sumar(p2);
        p2.sumar(p1);

        Punto.sumar(p1, p2);
        Punto.sumar(p2, p1);

    }
}
