package es.rafapuig;

import es.rafapuig.model.Alumno;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ListDemo {

    static final String[] COUNTRIES_ARRAY = {"España", "Francia", "Portugal", "Italia"};

    /**
     * Metodo auxiliar estático genérico para imprimir por consola una lista mediante foreach
     */
    static <T> void printList(List<T> list) {
        for (T elem : list) { //Como una List hereda de Iterable se puede usar en un foreach
            System.out.println(elem);
        }
    }

    /**
     * Metodo auxiliar para imprimir directamente la lista por consola
     * (no genérico, usa el unbounded wildcard para el parámetro de tipo del tipo del parámetro de entrada)
     */
    static void print(List<?> list) {
        System.out.println(list);
    }

    @Test
    void testArraysAsList() {
        String[] countriesArray = {"España", "Francia", "Portugal", "Italia"};
        //Crear una VISTA en forma de lista del array
        List<String> countries = Arrays.asList(countriesArray);
        print(countries);

        // Cambios en la vista de lista (en realidad serán cambios en el array subyacente)
        countries.set(0, "Holanda");
        // Y Se reflejan en el array
        System.out.println(Arrays.toString(countriesArray));

        //Si se modifica el array subyacente
        countriesArray[1] = "Belgica";
        // Los cambios se reflejan en la vista
        print(countries);
    }

    @Test
    void testListOf() {
        try {
            List<String> countries = List.of("España", "Francia", "Portugal", "Italia");

            //No permitido
            countries.set(0, "Holanda");
            print(countries);
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }

        assertThrows(UnsupportedOperationException.class, () -> {
            List<String> countries = List.of("España", "Francia", "Portugal", "Italia");
            countries.set(0, "Holanda");
        });
    }

    @Test
    void testModifyListOfThrowsException() {
        //Given
        List<String> countries = List.of("España", "Francia", "Portugal", "Italia");

        //When
        Executable setCountry = () -> countries.set(0, "Holanda");

        //Then
        assertThrows(UnsupportedOperationException.class, setCountry);
    }


    public static List<Alumno> getAlumnos() {
        List<Alumno> alumnoList = new ArrayList<>();

        alumnoList.add(new Alumno() {
            {
                setNIA("01020304");
                setNombre("ARMANDO");
                setApellidos("BRONCA", "SEGURA");
                setEdad(45);
            }
        });

        alumnoList.add(
                new Alumno("12345678", "AMADOR",
                        new String[]{"DENADOR", "PEREZ"}, 34));

        return alumnoList;
    }

    public static void main(String[] args) {
        //listTest();
        testListIterator();
    }


    static void listTest() {

        List<String> ingredients =
                new ArrayList<>(List.of("arroz", "leche", "harina"));

        //Metodos para añadir elementos

        //Añadir/insertar en la posicion 1 (la segunda) un elemento
        ingredients.add(1, "cafe");
        ingredients.add(ingredients.size(), "azucar"); //En ultimo lugar
        ingredients.add(0, "huevos"); //Delante de todos los elementos

        print(ingredients);

        //Obtener el elemento en una posicion /indice
        String ingrediente = ingredients.get(2);
        System.out.println("ingredientes[2] = " + ingrediente);

        //Establecer un nuevo elemento en la posición, en sustitución del elem anterior
        ingredients.set(2, "cafe en polvo");
        print(ingredients);

        ingredients.add(2, "azucar"); // Admite elementos duplicados

        //Obtener la posicion de la primera coincidencia con el elemento buscado
        int index = ingredients.indexOf("azucar"); //Empieza a buscar por el principio
        System.out.println("index = " + index);

        int lastIndex = ingredients.lastIndexOf("azucar"); //Empieza a buscar por el final
        System.out.println("lastIndex = " + lastIndex);

        index = ingredients.indexOf("tomate");
        System.out.println("index = " + index); //Si no encuentra devuelve -1

        ingredients.addAll(5, List.of("mantequilla", "levadura"));
        print(ingredients);
        index = ingredients.indexOf("mantequilla");
        System.out.println("index = " + index);


        //El iterador LisIterator
        ListIterator<String> listIterator = ingredients.listIterator();
        while (listIterator.hasNext()) {
            System.out.println("listIterator.next() = " + listIterator.next());
        }
        while (listIterator.hasPrevious()) { //Podemos ir hacia atras
            String item = listIterator.previous();
            if (item.equals("azucar")) {
                listIterator.set("azucar moreno");
            }
            System.out.println(item);
        }
        print(ingredients);

        //Reemplaza todos los ingredientes por otros aplicando la operacion
        ingredients.replaceAll(
                i -> i.equals("azucar moreno") ? "azucar de caña" : i);
        print(ingredients);


        //Ordenar una lista
        ingredients.sort(null); //Ordenacion "natural"
        //ingredients.sort(String::compareTo); //Ordenacion indicando comaparador
        ingredients.sort((s1, s2) -> s1.compareTo(s2));
        print(ingredients);
    }


    static void linkedListTest() {
        //Crear una lista enlazada a partir de los elementos de una otra coleccion
        LinkedList<Alumno> alumnoList = new LinkedList<>(getAlumnos());

        alumnoList.descendingIterator();
    }

    /**
     * Devuelve una lista modificable de nombres de ingredientes
     */
    static List<String> getIngredientsList() {
        return new ArrayList<>(List.of("arroz", "leche", "harina"));
    }

    static void testListIterator() {
        List<String> ingredients = getIngredientsList();

        ListIterator<String> listIterator = ingredients.listIterator();

        while (listIterator.hasNext()) {
            int index = listIterator.nextIndex();
            String ingredient = listIterator.next();
            System.out.print("index = " + index);
            System.out.println(", ingrediente = " + ingredient);
        }

        while (listIterator.hasPrevious()) {
            int index = listIterator.previousIndex();
            String ingredient = listIterator.previous();
            System.out.print("index = " + index);
            System.out.println(", ingrediente = " + ingredient);
        }

        while (listIterator.hasNext()) {
            int index = listIterator.nextIndex();
            String ingredient = listIterator.next();
            // Podemos establecer un nuevo elemento en la posición recuperada por next (o previous)
            listIterator.set(ingredient.toUpperCase());
        }

        iterateForwards(ingredients.listIterator());

        int initalPosition = ingredients.size(); // Debe ser un valor entre 0 y List.size()
        iterateBackwards(ingredients.listIterator(initalPosition));

        testAddingWithListIterator();
        testRemovingWithListIterator();
    }

    /**
     * El ListIterator añade un elemento a la lista en la posición indicada por el nextIndex
     * El cursor se sitúa entre los elementos previousIndex y nextIndex
     */
    private static void testAddingWithListIterator() {
        List<String> ingredients = getIngredientsList();
        ListIterator<String> listIterator2 = ingredients.listIterator();
        print(ingredients);

        System.out.println("next index = " + listIterator2.nextIndex());
        System.out.println("previous index = " + listIterator2.previousIndex());
        listIterator2.add("azucar".toUpperCase());
        print(ingredients);

        System.out.println("next index = " + listIterator2.nextIndex());
        System.out.println("previous index = " + listIterator2.previousIndex());
        listIterator2.add("chocolate".toUpperCase());
        print(ingredients);

        listIterator2.next();
        System.out.println("next index = " + listIterator2.nextIndex());
        System.out.println("previous index = " + listIterator2.previousIndex());
        listIterator2.add("limón".toUpperCase());
        print(ingredients);
    }

    static void testRemovingWithListIterator() {
        List<String> ingredients = getIngredientsList();
        ListIterator<String> listIterator = ingredients.listIterator();
        print(ingredients);

        System.out.println("next index = " + listIterator.nextIndex());
        System.out.println("previous index = " + listIterator.previousIndex());
        try {
            listIterator.remove();
        } catch (IllegalStateException e) {
            System.out.println(e.getClass().getSimpleName() + ": No se puede eliminar con previous index -1");
        }
        print(ingredients);

        listIterator.next();
        System.out.println("next index = " + listIterator.nextIndex());
        System.out.println("previous index = " + listIterator.previousIndex());
        listIterator.remove();
        print(ingredients);
        System.out.println("next index = " + listIterator.nextIndex());
        System.out.println("previous index = " + listIterator.previousIndex());

        System.out.println("Avazamos el iterador...");
        listIterator.next();
        System.out.println("Avazamos el iterador...");
        listIterator.next();
        System.out.println("next index = " + listIterator.nextIndex());
        System.out.println("previous index = " + listIterator.previousIndex());
        listIterator.remove();
        System.out.println("next index = " + listIterator.nextIndex());
        System.out.println("previous index = " + listIterator.previousIndex());
        listIterator.remove();
        print(ingredients);


    }

    /**
     * Recorre una List hacia adelante mediante un ListIterador
     * El cursor del iterador proporcionado puede encontrarse situado en cualquier posición de la lista
     */
    static <T> void iterateForwards(ListIterator<T> listIterator) {
        while (listIterator.hasNext()) {
            int index = listIterator.nextIndex();
            T element = listIterator.next();
            System.out.print("index = " + index);
            System.out.println(", element = " + element);
        }
    }

    /**
     * Recorre una List hacia adelante mediante un ListIterador
     * El cursor del iterador proporcionado puede encontrarse situado en cualquier posición de la lista
     */
    static <T> void iterateBackwards(ListIterator<T> listIterator) {
        while (listIterator.hasPrevious()) {
            int index = listIterator.previousIndex();
            T element = listIterator.previous();
            System.out.print("index = " + index);
            System.out.println(", element = " + element);
        }
    }

}
