package exercises.dominoes;

import exercises.pairs.FieldPair;

import java.util.*;

public class Domino {

    public static final int MIN_DOTS = 0;
    public static final int MAX_DOTS = 6;

    private final FieldPair<Integer> ends;

    public static boolean isValidNumberOfDots(int dots) {
        return dots >= MIN_DOTS && dots <= MAX_DOTS;
    }

    private static void checkNumberOfDots(int dots) {
        if (!isValidNumberOfDots(dots)) {
            throw new IllegalArgumentException("Número de puntos invalido, entre 0 y 6");
        }
    }

    public Domino(int firstEndDots, int secondEndDots) {
        this.ends = new FieldPair<>(firstEndDots, secondEndDots);
    }

    protected Domino(FieldPair<Integer> ends) {
        this.ends = ends;
    }

    public boolean isDouble() {
        return ends.getFirst().intValue() == ends.getSecond().intValue();
    }

    public boolean isSingle() {
        return !isDouble();
    }

    public boolean contains(int suit) {
        return ends.contains(suit);
    }

    Domino flip() {
        return new Domino(ends.swap());
    }

    @Override
    public final boolean equals(Object object) {
        if (!(object instanceof Domino domino)) return false;
        return ends.equals(domino.ends);
    }

    @Override
    public int hashCode() {
        return ends.hashCode();
    }

    private static String endToString(int dots) {
        return switch (dots) {
            case 1 -> " ∙ ";
            case 2 -> " ̇ .";
            case 3 -> "˙∙.";
            case 4 -> ": :";
            case 5 -> ":∙:";
            case 6 -> ":::";
            case 0 -> "   ";
            default -> " " + dots + " ";
        };
    }

    @Override
    public String toString() {
        return "[" + endToString(ends.getFirst()) + "|" + endToString(ends.getSecond()) + "]";
    }



    //TODO un mapa de int (offset) a String (resultado cacheado del StringBuilder de la ficha)


    private int getOffSet() {
        return ends.getSecond() + ends.getFirst() * 7 + 1;
    }

    public static int VERTICAL_BASE = 0x1F062;
    public static int HORIZONTAL_BASE = 0x1F030;

    protected static String toTileChar(boolean vertical, int offset) {
        final int base = vertical ? VERTICAL_BASE : HORIZONTAL_BASE;
        final int codePoint = base + offset;
        return new StringBuilder().appendCodePoint(codePoint).toString();

        /*char surrogateHigh = Character.highSurrogate(codePoint);
        char surrogateLow = Character.lowSurrogate(codePoint);
        return String.format("%s%s", surrogateHigh, surrogateLow);*/
        //Character.highSurrogate();
    }

    public static String backTileChar(boolean vertical) {
        return toTileChar(vertical, 0);
    }

    public String toTileChar(boolean vertical) {
        final int offset = getOffSet();
        return toTileChar(vertical, offset);
    }

    public String toVerticalTileChar() {
        return toTileChar(true);
    }

    public String toHorizontalTileChar() {
        return toTileChar(false);
    }
}

class Demo {

    public static void main(String[] args) {

        System.out.println(Domino.backTileChar(true));
        System.out.println(Domino.backTileChar(false));

        Set<Domino> dominoes = new LinkedHashSet<>();
        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 6; j++) {
                dominoes.add(new Domino(i,j));
            }
        }

        for (Domino domino : dominoes) {
            System.out.print(domino.toVerticalTileChar());
            System.out.print(domino.toHorizontalTileChar());
        }
    }
}
