package model.academic;

public enum Nota {
    NE((byte) 0),
    CERO((byte) 0),
    UNO((byte) 1),
    DOS((byte) 2),
    TRES((byte) 3),
    CUATRO((byte) 4),
    CINCO((byte) 5),
    SEIS((byte) 6),
    SIETE((byte) 7),
    OCHO((byte) 8),
    NUEVE((byte) 9),
    DIEZ((byte) 10),
    MH((byte) 10);

    private final byte value;

    public byte getNumericValue() {
        return value;
    }

    Nota(byte calificacionNumerica) {
        this.value = calificacionNumerica;
    }

    public static Nota of(Integer value) {
        if (value == null) return Nota.NE;
        if (value >= Nota.values().length) throw new IllegalArgumentException();
        return Nota.values()[value + 1];
    }

    @Override
    public String toString() {
        if(this.equals(Nota.MH)) return "10 M.H.";
        if(this.equals(Nota.NE)) return  "NE";
        return Byte.toString(this.value);
    }
}



