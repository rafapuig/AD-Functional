package es.rafapuig;

import java.util.*;

public class CollectionDemo {

    public static void main(String[] args) {

        String[] paisesArray = {"España", "Francia", "Portugal", "Italia"};
        String[] paisesAmericaArray = {"EE.UU.", "Canada", "Brasil", "Argentina", "Cuba", "Colombia"};

        //Coleccion modificable
        //Collection<String> paises = new LinkedList<>(Arrays.asList(paisesArray));
        Collection<String> paises = new LinkedList<>(List.of(paisesArray)); //otra forma
        Collection<String> paisesInmutable = List.of(paisesArray);

        print(paises);
        paises.add("Inglaterra");
        print(paises);

        try {
            paisesInmutable.add("Belgica");
        } catch (UnsupportedOperationException e) {
            System.out.println("No se puede insertar en una coleccion inmutable - " + e);
        }

        paises.addAll(List.of("Japon", "Australia"));
        print(paises);

        paises.addAll(List.of(paisesAmericaArray));
        print(paises);

        //Eliminar elementos
        paises.remove("Francia");
        print(paises);

        paises.removeAll(List.of("Portugal", "Brasil", "Colombia"));
        print(paises);

        paises.retainAll(List.of(paisesAmericaArray));
        print(paises);

        paises.clear();
        print(paises);

        paises.addAll(List.of(paisesArray));
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

        paises.addAll(List.of(paisesAmericaArray));
        //Iterador
        Iterator<String> iterator = paises.iterator();
        while (iterator.hasNext()) {
            System.out.println("iterator.next() = " + iterator.next());
        }
        for (String pais : paises) {
            System.out.println("pais = " + pais);
        }

        //Arrays
        Object[] objs = paises.toArray();
        System.out.println(Arrays.toString(objs));

        String[] stringArray = paises.toArray(new String[0]);
        stringArray[0] = "Chile";
        System.out.println(Arrays.toString(stringArray));

        stringArray = paises.toArray(String[]::new);
        stringArray[0] = "Peru";
        System.out.println(Arrays.toString(stringArray));

        //Streams
        paises.clear();
        paises.addAll(List.of(paisesAmericaArray));
        paises.addAll(List.of(paisesArray));
        paises.stream()
                .filter(pais -> pais.startsWith("C") || pais.startsWith("E"))
                .map(String::toUpperCase)
                .forEach(System.out::println);

    }

    static void print(Collection<?> collection) {
        System.out.println(collection);
    }
}
