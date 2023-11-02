package es.rafapuig.strings;

public class StringUtils {

    public static String padRightToLength(String text, int targetLength) {
        return String.format("%s%" + (targetLength - text.length()) + "c", text, ' ');
    }

    public static String padLeftToLength(String text, int targetLength) {
        return String.format("%" + (targetLength - text.length()) + "c%s", ' ', text);
    }

}
