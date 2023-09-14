package functional;

@FunctionalInterface
public interface Mapper<T> {

    //El metodo abstracto
    int map(T source);

    //Metodo generico ...y estatico
    public static <U> int[] mapToInt(U[] list, Mapper<? super U> mapper) {
        int[] mappedValues = new int[list.length];
        for (int i = 0; i < list.length; i++) {
            //Mapear el objeto a un entero
            mappedValues[i] = mapper.map(list[i]);
        }
        return mappedValues;
    }

}
