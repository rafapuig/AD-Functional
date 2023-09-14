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

        alumnoList.add(new Alumno("12345678", "AMADOR", new String[]{"DENADOR", "PEREZ"}, 34));

        return alumnoList;
    }

    public static void main(String[] args) {
        listTest();

        //arrayListTest();
    }

    private static void arrayListTest() {
        List<Alumno> alumnoList = getAlumnos();

        for (Alumno alumno : alumnoList) {
            System.out.println(alumno);
        }

        printList(alumnoList);
    }

    static void linkedListTest() {
        LinkedList<Alumno> alumnoList = new LinkedList<>(getAlumnos());

        alumnoList.descendingIterator();
    }

    static <T> void printList(List<T> list) {
        for (T elem : list) {
            System.out.println(elem);
        }
    }

    static void print(List<?> list) {
        System.out.println(list);
    }


    static void listTest() {
        List<String> ingredients = new ArrayList<>(List.of("arroz", "leche", "harina"));

        ingredients.add(1, "cafe");
        ingredients.add(ingredients.size(), "azucar"); //En ultimo lugar
        ingredients.add(0, "huevos");

        print(ingredients);

        String ingrediente = ingredients.get(2);
        System.out.println("ingredientes[2] = " + ingrediente);

        ingredients.set(2, "cafe en polvo");
        print(ingredients);

        ingredients.add(2, "azucar"); // Duplicado
        int index = ingredients.indexOf("azucar");
        System.out.println("index = " + index);

        index = ingredients.lastIndexOf("azucar");
        System.out.println("index = " + index);

        index = ingredients.indexOf("tomate");
        System.out.println("index = " + index);

        ingredients.addAll(5, List.of("mantequilla", "levadura"));
        print(ingredients);
        index = ingredients.indexOf("mantequilla");
        System.out.println("index = " + index);

        ListIterator<String> listIterator = ingredients.listIterator();
        while (listIterator.hasNext()) {
            System.out.println("listIterator.next() = " + listIterator.next());
        }
        while (listIterator.hasPrevious()) {
            String item = listIterator.previous();
            if (item.equals("azucar")) {
                listIterator.set("azucar moreno");
            }
            System.out.println(item);
        }
        print(ingredients);

        ingredients.replaceAll(i -> i.equals("azucar moreno") ? "azucar de caña" : i);
        print(ingredients);


        //ingredients.sort(String::compareTo);
        ingredients.sort(null);
        ingredients.sort((s1, s2) -> s1.compareTo(s2));
        print(ingredients);


    }

}
