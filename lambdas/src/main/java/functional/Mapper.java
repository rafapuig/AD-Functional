package functional;

@FunctionalInterface
public interface Mapper<T> { //la interfaz funcional es genérica para el tipo T

    //El método abstracto único SAM
    int map(T source);  //A partir de un valor de tipo T debe devolver un int

    //Método genérico ... y estático
    // En una interfaz funcional puede haber tantos métodos estáticos como se quiera
    // (? super U significa cualquier objeto con tipo entre Object y U)
    public static <U> int[] mapToInt(U[] list, Mapper<? super U> mapper) {
        int[] mappedValues = new int[list.length];
        for (int i = 0; i < list.length; i++) {
            //Mapear el elemento i de tipo T de la lista a un entero
            mappedValues[i] = mapper.map(list[i]);
        }
        return mappedValues;
    }

}
