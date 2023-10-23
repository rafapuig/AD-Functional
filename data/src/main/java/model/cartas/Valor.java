package model.cartas;

public enum Valor {
    AS(1),
    DOS(2),
    TRES(3),
    CUATRO(4),
    CINCO(5),
    SEIS(6),
    SIETE(7),
    SOTA(10),
    CABALLO(11),
    REY(12);

    private int number;

    Valor(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
