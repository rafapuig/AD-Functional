package es.rafapuig;

import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.util.*;

public class CollectionDemo {

    static final String[] europeanCountriesArray =
            {"España", "Francia", "Portugal", "Italia"};

    static final String[] americanCountriesArray =
            {"EE.UU.", "Canada", "Brasil", "Argentina", "Cuba", "Colombia"};

    static final String[] africanCountriesArray =
            {"Egipto", "Tunez", "Marruecos", "Camerún", "Senegal"};

    /**
     * Imprimir una colección en la consola
     */
    static void print(Collection<?> collection) {
        System.out.println(collection);
    }


    static Collection<String> getEuropeanCountries() {
        List<String> countries = List.of(europeanCountriesArray);

        // Crear una colección modificable "envolviéndola" con un LinkedList
        return new LinkedList<>(countries);
    }


    static Collection<String> getAmericanCountries() {
        Collection<String> countriesUnmodifiable = List.of(americanCountriesArray);

        // Crear una colección modificable "envolviéndola" con un LinkedList
        return new LinkedList<>(countriesUnmodifiable);
    }

    /**
     * Devuelve una lista no modificable de nombres de países africanos
     */
    static Collection<String> getAfricanCountries() {
        return List.of(africanCountriesArray);
    }


    @Test
    void addToUnmodifiableCollectionTest() {
        //Añadir a una colección no modificable envolviendo la lista inmodificable en una modificable
        Collection<String> countries = new ArrayList<>(getAfricanCountries());
        print(countries);
        countries.add("Mozambique");

        //Añadir a una colección inmodificable lanza una excepción
        try {
            Collection<String> countriesUnmodifiable = Collections.unmodifiableCollection(countries);
            countriesUnmodifiable.add("Mozambique");
            print(countriesUnmodifiable);
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }

        //List.of devuelve una lista no modificable
        try {
            Collection<String> countriesUnmodifiable = List.of("España", "Portugal", "Italia");
            countriesUnmodifiable.add("Francia");
            print(countriesUnmodifiable);
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addToCollectionTest() {
        Collection<String> europeanCountries = getEuropeanCountries();
        print(europeanCountries);
        europeanCountries.add("Reino Unido");
        print(europeanCountries);

        europeanCountries.add("Bélgica");
        print(europeanCountries);

        europeanCountries.addAll(Arrays.asList("Suecia", "Noruega"));
        print(europeanCountries);

        europeanCountries.addAll(List.of("Alemania", "Austria"));
        print(europeanCountries);
    }


    void run() {
        addToCollectionTest();
        addToUnmodifiableCollectionTest();
    }


    public static void main(String[] args) {

        new CollectionDemo().run();


        //Colección modificable (mutable), se pueden añadir y quitar elementos
        //Collection<String> paises = new LinkedList<>(Arrays.asList(paisesArray));
        Collection<String> paises = new LinkedList<>(List.of(europeanCountriesArray)); //otra forma

        //Colección inmodificable
        // No se puede añadir ni eliminar elementos --> lanzaría una UnsupportedOperationExceptio
        Collection<String> paisesUnmodifiable = List.of(europeanCountriesArray);

        print(paises);
        paises.add("Inglaterra");
        print(paises);

        //Añadir elementos a la colección

        //Comprobamos que no se puede añadir elementos a una colección inmodificable
        try {
            paisesUnmodifiable.add("Belgica");
        } catch (UnsupportedOperationException e) {
            System.out.println("No se puede insertar en una coleccion inmodificable - " + e);
        }

        //El metodo addAll permite añadir varios elementos que provienen de una colección
        paises.addAll(List.of("Japon", "Australia"));
        print(paises);

        //El metodo List.of permite como argumento un array
        paises.addAll(List.of(americanCountriesArray));
        print(paises);

        //Eliminar elementos
        paises.remove("Francia");
        print(paises);

        //Elimina los elementos coincidentes con los de la colección proporcionada
        paises.removeAll(List.of("Portugal", "Brasil", "Colombia"));
        print(paises);

        //Borra todos los elementos de la colección menos los que están en la colección proporcionada
        paises.retainAll(List.of(americanCountriesArray));
        print(paises);

        //Borrar todos los elementos de la colección, la vacía
        paises.clear();
        print(paises);

        paises.addAll(List.of(europeanCountriesArray));
        System.out.println("paises.contains(\"España\") = " + paises.contains("España"));
        System.out.println("paises.contains(\"Grecia\") = " + paises.contains("Grecia"));
        paises.remove("España");
        System.out.println("paises.contains(\"España\") = " + paises.contains("España"));
        print(paises);

        System.out.println("paises.containsAll(List.of(\"Francia\",\"Portugal\")) = "
                + paises.containsAll(List.of("Francia", "Portugal")));

        System.out.println("paises.isEmpty() = " + paises.isEmpty());
        System.out.println("paises.size() = " + paises.size());
        paises.clear();
        System.out.println("paises.isEmpty() = " + paises.isEmpty());
        System.out.println("paises.size() = " + paises.size());

        paises.addAll(List.of(americanCountriesArray));
        //Iterador
        Iterator<String> iterator = paises.iterator();
        while (iterator.hasNext()) {
            System.out.println("iterator.next() = " + iterator.next());
        }
        for (String pais : paises) {
            System.out.println("pais = " + pais);
        }
    }

    //------------------TO ARRAY ------------------------------

    @Test
    void testToArray() {
        Collection<String> countries = getAmericanCountries();

        //Arrays
        Object[] objs = countries.toArray(); // Crear un array de referencias de tipo Object
        System.out.println(Arrays.toString(objs));

        objs[0] = "------";
        System.out.println(Arrays.toString(objs));

        // No modifica la colección original
        print(countries);
    }

    @Test
    void testToArrayDestinationArrayEnoughSpace() {

        Collection<String> countries = getAmericanCountries();

        //Crear un array de la misma longitud que el tamaño de la colección
        String[] destArray = new String[countries.size()];
        System.out.println(Arrays.toString(destArray)); // Inicialmente contiene nulls

        // Copia los elementos de la colección en el array proporcionado
        String[] resultArray = countries.toArray(destArray);
        System.out.println(Arrays.toString(destArray)); //Ahora el array contiene los mismos valores que la colecciñon

        System.out.println("Son el mismo array? " + (destArray == resultArray));

        // Cambiamos un elemento del array
        destArray[0] = "------";
        System.out.println(Arrays.toString(destArray)); // El cambio se refleja en el array
        print(countries);   // Pero no afecta a la colección original
    }

    @Test
    void testToArrayDestinationArrayNotEnoughSpace() {

        Collection<String> countries = getAmericanCountries();

        // Si el tamaño del array proporcionado es insuficiente para copiar todos los elementos
        // de la colección, se creará un nuevo array del tamaño necesario

        String[] destArray = new String[countries.size() - 1];
        System.out.println(Arrays.toString(destArray)); // Inicialmente contiene nulls

        // Copia los elementos de la colección en el array proporcionado
        // La referencia al array donde se copia se devuelve por el metodo toArray
        String[] resultArray = countries.toArray(destArray);
        System.out.println(Arrays.toString(destArray)); //Ahora el array destino sigue sin tener los valores

        System.out.println("Son el mismo array? " + (destArray == resultArray));
        System.out.println(Arrays.toString(resultArray)); // Es el array resultado el que los tiene

        // Si usamos la referencia al array que devuelve el metodo toArray siempre estaremos
        // seguros que estamos usando el array donde en realidad se han copiado los valores

        // Cambiamos un elemento del array
        resultArray[0] = "------";
        System.out.println(Arrays.toString(resultArray)); // El cambio se refleja en el array
        print(countries);   // Pero no afecta a la colección original
    }

    @Test
    void testToArrayDestinationArrayNotEnoughSpaceSizeCero() {

        Collection<String> countries = getAmericanCountries();

        // Ahora no vamos a copiar en el array proporcionado,
        // el array proporcionado es de tamaño cero,
        // se proporciona para que el runtime pueda saber el tipo de los elementos
        // del array que debe crear
        String[] resultArray = countries.toArray(new String[countries.size()]); // Reemplazar por 0

        System.out.println(Arrays.toString(resultArray)); // Es el array resultado el que los tiene

        // Cambiamos un elemento del array
        resultArray[0] = "------";
        System.out.println(Arrays.toString(resultArray)); // El cambio se refleja en el array
        print(countries);   // Pero no afecta a la colección original
    }

    @Test
    void testToArrayDestinationArrayNotEnoughSpaceSizeCeroFunctional() {

        Collection<String> countries = getAmericanCountries();

        //Usando la referencia al metodo constructor de los arrays
        String[] resultArray = countries.toArray(String[]::new); // Reemplazar por 0

        System.out.println(Arrays.toString(resultArray));

        // Cambiamos un elemento del array
        resultArray[0] = "------";
        System.out.println(Arrays.toString(resultArray)); // El cambio se refleja en el array
        print(countries);   // Pero no afecta a la colección original
    }


    //-------------------- TO STREAM --------------------------------

    static <T> Collection<T> generateEmptyCollection() {
        return new ArrayList<>();
    }

    //Streams (Stream API)
    @Test
    void testListToStream() {
        Collection<String> countries = generateEmptyCollection();

        countries.addAll(List.of(americanCountriesArray));
        countries.addAll(List.of(europeanCountriesArray));

        // Imprimir por consola
        // los nombres de los paises que empiezan por C o por E
        countries.stream()
                .filter(pais -> pais.startsWith("C") || pais.startsWith("E")) // filtrado
                .map(String::toUpperCase)                                           // mapeo
                .forEach(System.out::println);                                      // consumo
    }

}
