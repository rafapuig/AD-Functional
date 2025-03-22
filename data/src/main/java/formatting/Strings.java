package formatting;

public class Strings {

    public static String centerAligned(String text, int width) {
        int textLength = text.length();

        // La cantidad de espacios de relleno será igual al ancho - longitud del texto
        int padding = width - textLength;

        // La mitad del espacio de relleno será la cantidad de relleno por la izquierda
        // (y la otra mitad por la derecha)
        int paddingLeft = padding / 2;

        // Cadena de formato para aplicar relleno por la izquierda
        // (alinea a la derecha un texto de longitud textLength en una logitud total de paddingLeft + textLength)
        String paddingLeftFormat = "%" + (paddingLeft + textLength) + "s";

        // Aplicamos el formato al texto proporcionado (quedara con paddingLeft espacios añadidos a su izquierda
        String paddedLeftText = paddingLeftFormat.formatted(text);

        // Ahora la cadena de formato alinea a la izquierda (signo menos) con un espacio total width
        String paddingRightFormat = "%-" + width + "s";

        //Al aplicar este formato a la cadena rellenada por la izquierda de longitud paddingLeft + textLength
        // se añadirán width - (paddingLeft + textLength) espacios a la derecha
        return paddingRightFormat.formatted(paddedLeftText);
    }

    public static String rightAligned(String text, int width) {
        return String.format("%" + width + "s", text);
    }

    public static String leftAligned(String text, int width) {
        return String.format("%-" + width + "s", text);
    }
}
