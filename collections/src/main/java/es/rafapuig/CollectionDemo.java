package es.rafapuig;

import org.junit.jupiter.api.Test;

import java.util.*;

public class CollectionDemo {

    static final String[] europeanCountriesArray =
            {"España", "Francia", "Portugal", "Italia", "Suiza"};

    static final String[] americanCountriesArray =
            {"EE.UU.", "Canada", "Brasil", "Argentina", "Cuba", "Colombia"};

    static final String[] africanCountriesArray =
            {"Egipto", "Túnez", "Marruecos", "Camerún", "Senegal"};

    /**
     * Imprimir una colección en la consola
     */
    static void print(Collection<?> collection) {
        System.out.println(collection);
    }


    /**
     * Colección modificable (mutable), se pueden añadir y quitar elementos
     */
    static Collection<String> getEuropeanCountries() {
        List<String> countries = List.of(europeanCountriesArray);

        // Crear una colección modificable "envolviéndola" con un LinkedList
        return new HashSet<>(countries);
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
        //Colección inmodificable
        // No se puede añadir ni eliminar elementos --> lanzaría una UnsupportedOperationException
        return List.of(africanCountriesArray);
    }



    public static void main(String[] args) {
        new CollectionDemo().run();
    }

    void run() {
        testAddToCollection();
        testAddToUnmodifiableCollection();
        testRemove();
        testRemoveOnUnmodifiableCollection();
        testRemoveOnUnmodifiableCollectionView();
        testCheckElementsMethods();
        testCollectionIterator();
        testToArray();
        testToArrayDestinationArrayEnoughSpace();
        testToArrayDestinationArrayNotEnoughSpace();
        testToArrayDestinationArrayNotEnoughSpaceSizeCeroFunctional();
        testCollectionToStream();
    }


    //------------- AÑADIR ELEMENTOS ---------------------------

    @Test
    void testAddToCollection() {
        Collection<String> countries = getEuropeanCountries();
        print(countries);

        // Al añadir un elemento en una colección, dependerá del tipo concreto de colección
        // en que posición se añade el nuevo elemento
        // Las tienen secuenciación y respetan la organización según el orden de llegada a la colección
        // Los conjuntos, depende de la implementación, no preservan el orden de inserción
        System.out.println("Añadiendo Reino Unido...");
        countries.add("Reino Unido".toUpperCase());
        print(countries);

        System.out.println("Añadiendo Bélgica...");
        countries.add("Bélgica".toUpperCase());
        print(countries);

        System.out.println("Añadiendo Suecia y Noruega...");
        //El metodo addAll permite añadir varios elementos que provienen de una colección
        countries.addAll(Arrays.asList("Suecia", "Noruega"));
        print(countries);

        System.out.println("Añadiendo Alemania y Austria...");
        countries.addAll(List.of("Alemania", "Austria"));
        print(countries);
    }


    @Test
    void testAddToUnmodifiableCollection() {
        //Añadir a una colección no modificable envolviendo la lista inmodificable en una modificable
        Collection<String> countries = new ArrayList<>(getAfricanCountries());
        print(countries);
        System.out.println("Añadiendo Mozambique...");
        countries.add("Mozambique");
        print(countries);

        //Obtenemos una VISTA no modificable de la colección
        //Añadir a una vista no modificable lanza una excepción (aunque la colección subyacente fuera modificable)
        Collection<String> countriesUnmodifiable = Collections.unmodifiableCollection(countries);
        try {
            System.out.println("Añadiendo Angola...");
            //Comprobamos que no se puede añadir elementos a una colección inmodificable
            countriesUnmodifiable.add("Angola");
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getClass().getSimpleName() + ": No se puede añadir en una vista no modificable");
        }
        finally {
            print(countriesUnmodifiable);
        }

        //List.of devuelve una lista no modificable
        Collection<String> europeanCountries = List.of(europeanCountriesArray);
        try {
            System.out.println("Añadiendo Grecia...");
            europeanCountries.add("Grecia");
            print(europeanCountries);
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getClass().getSimpleName() + ": No se puede añadir en una colección no modificable");
        } finally {
            print(europeanCountries);
        }
    }


    //-------------- ELIMINAR Y VACIAR  ---------------------------

    @Test
    void testRemove() {
        Collection<String> countries = getEuropeanCountries();
        print(countries);

        System.out.println("Eliminando Francia...");
        //Eliminar un elemento
        countries.remove("Francia");
        print(countries);

        System.out.println("Eliminando Portugal, Brasil y Colombia...");
        //Elimina los elementos coincidentes con los de la colección proporcionada
        countries.removeAll(List.of("Portugal", "Brasil", "Colombia"));
        print(countries); // Solamente habrá podido eliminar Portugal (los otros no están)


        System.out.println("Retener a España, Suiza y Andorra...");
        //Borra todos los elementos de la colección menos los que están en la colección proporcionada
        countries.retainAll(List.of("España", "Suiza", "Andorra"));
        print(countries); // Quedarán España y Suiza (Andorra no pertenece a la colección)

        System.out.println("Vaciando la colección (borrando todos los elementos)...");
        //Borrar todos los elementos de la colección, la vacía
        countries.clear();
        print(countries);
    }

    @Test
    void testRemoveOnUnmodifiableCollection() {
        Collection<String> countries = getAfricanCountries();
        print(countries);

        try {
            System.out.println("Eliminando Marruecos...");
            //Eliminar un elemento
            countries.remove("Marruecos");

        } catch (UnsupportedOperationException e) {
            System.out.println(e.getClass().getSimpleName() + ": No se puede eliminar de una colección no modificable");
        } finally {
            print(countries);
        }

        try {
            System.out.println("Eliminando Marruecos, Brasil y Colombia...");
            //Elimina los elementos coincidentes con los de la colección proporcionada
            countries.removeAll(List.of("Marruecos", "Brasil", "Colombia"));
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getClass().getSimpleName() + ": No se puede eliminar de una colección no modificable");
        } finally {
            print(countries);
        }

        try {
            System.out.println("Retener a España, Marruecos y Andorra...");
            //Borra todos los elementos de la colección menos los que están en la colección proporcionada
            countries.retainAll(List.of("España", "Marruecos", "Andorra"));
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getClass().getSimpleName() + ": No se puede modificar de una colección no modificable");
        } finally {
            print(countries);
        }

        try {
            System.out.println("Vaciando la colección (borrando todos los elementos)...");
            //Borrar todos los elementos de la colección, la vacía
            countries.clear();
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getClass().getSimpleName() + ": No se puede vaciar una colección no modificable");
        } finally {
            print(countries);
        }

    }

    @Test
    void testRemoveOnUnmodifiableCollectionView() {
        Collection<String> countries = Collections.unmodifiableCollection(getEuropeanCountries());
        print(countries);

        try {
            System.out.println("Eliminando Francia...");
            //Eliminar un elemento
            countries.remove("Francia");

        } catch (UnsupportedOperationException e) {
            System.out.println(e.getClass().getSimpleName() + ": No se puede eliminar de una vista no modificable");
        } finally {
            print(countries);
        }

        try {
            System.out.println("Eliminando Portugal, Brasil y Colombia...");
            //Elimina los elementos coincidentes con los de la colección proporcionada
            countries.removeAll(List.of("Portugal", "Brasil", "Colombia"));
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getClass().getSimpleName() + ": No se puede eliminar de una vista no modificable");
        } finally {
            print(countries);
        }

        try {
            System.out.println("Retener a España, Suiza y Andorra...");
            //Borra todos los elementos de la colección menos los que están en la colección proporcionada
            countries.retainAll(List.of("España", "Suiza", "Andorra"));
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getClass().getSimpleName() + ": No se puede modificar de una vista no modificable");
        } finally {
            print(countries);
        }

        try {
            System.out.println("Vaciando la colección (borrando todos los elementos)...");
            //Borrar todos los elementos de la colección, la vacía
            countries.clear();
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getClass().getSimpleName() + ": No se puede vaciar una vista no modificable");
        } finally {
            print(countries);
        }

    }


    //---------------- COMPROBACIÓN DE ELEMENTOS

    @Test
    void testCheckElementsMethods() {

        Collection<String> countries = getEuropeanCountries();
        print(countries);

        System.out.println("countries.contains(\"España\") = " + countries.contains("España"));
        System.out.println("countries.contains(\"Grecia\") = " + countries.contains("Grecia"));

        System.out.println("Eliminamos España...");
        countries.remove("España");
        print(countries);
        System.out.println("countries.contains(\"España\") = " + countries.contains("España"));

        // Contains all
        System.out.println("countries.containsAll(List.of(\"Francia\",\"Portugal\")) = "
                + countries.containsAll(List.of("Francia", "Portugal")));

        System.out.println("countries.containsAll(List.of(\"Francia\",\"Grecia\")) = "
                + countries.containsAll(List.of("Francia", "Grecia")));

        // Esta vacía
        System.out.println("countries.isEmpty() = " + countries.isEmpty());

        // Cuantos elementos contiene la colección
        System.out.println("countries.size() = " + countries.size());

        System.out.println("Vaciamos la lista...");
        countries.clear();
        print(countries);

        System.out.println("countries.isEmpty() = " + countries.isEmpty());
        System.out.println("countries.size() = " + countries.size());
    }


    //---------------- ITERADOR -----------------------------------

    @Test
    void testCollectionIterator() {
        Collection<String> countries = getAmericanCountries();
        print(countries);

        System.out.println("Iterando mediante un iterator...");

        Iterator<String> iterator = countries.iterator();

        while (iterator.hasNext()) {
            String country = iterator.next();
            System.out.println("pais = " + country);
        }

        System.out.println("Iterando mediante el bucle foreach...");

        for (String pais : countries) {
            System.out.println("pais = " + pais);
        }
    }


    //------------------TO ARRAY ----------------------------------

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
        String[] resultArray = countries.toArray(new String[0]); // Reemplazar por 0

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
    void testCollectionToStream() {
        Collection<String> countries = generateEmptyCollection();

        countries.addAll(List.of(americanCountriesArray));
        countries.addAll(List.of(europeanCountriesArray));
        countries.addAll(List.of(africanCountriesArray));

        System.out.println(countries);
        System.out.println("Nombre en mayúsculas de los países que empiezan por C o E ordenados alfabéticamente");

        // Imprimir por consola
        // los nombres de los paises que empiezan por C o por E
        Collection<String> result = countries.stream()
                .filter(pais -> pais.startsWith("C") || pais.startsWith("E")) // filtrado
                .map(String::toUpperCase)                                           // mapeo
                .sorted()                                       // ordenación
                .toList();                                      // recolección

        System.out.println(result);
    }

}
