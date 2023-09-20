package es.rafapuig;

import es.rafapuig.model.Alumno;

import java.util.*;

public class ListDemo {

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
                        new String[] {"DENADOR", "PEREZ"}, 34));

        return alumnoList;
    }

    public static void main(String[] args) {
        listTest();

        //arrayListTest();
    }

    //Metodo auxiliar para imprimir por consola una lista mediante foreach
    static <T> void printList(List<T> list) {
        for (T elem : list) { //Como una List hereda de Iterable se puede usar en un foreach
            System.out.println(elem);
        }
    }


    //Metodo auxiliar para imprimir directamente la lista por consola
    static void print(List<?> list) {
        System.out.println(list);
    }


    private static void foreachListTest() {
        List<Alumno> alumnoList = getAlumnos();

        for (Alumno alumno : alumnoList) {
            System.out.println(alumno);
        }

        printList(alumnoList);
    }

    static void linkedListTest() {
        //Crear una lista enlazada a partir de los elementos de una otra coleccion
        LinkedList<Alumno> alumnoList = new LinkedList<>(getAlumnos());

        alumnoList.descendingIterator();
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

        //Estebler un nuevo elemento en la posicion, en sustitucion del elem anterior
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

}
