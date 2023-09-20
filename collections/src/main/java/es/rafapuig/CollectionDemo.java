package es.rafapuig;

import java.util.*;

public class CollectionDemo {

    //Imprimir una coleccion en la consola
    static void print(Collection<?> collection) {
        System.out.println(collection);
    }

    public static void main(String[] args) {

        String[] paisesArray =
                {"España", "Francia", "Portugal", "Italia"};

        String[] paisesAmericaArray =
                {"EE.UU.", "Canada", "Brasil", "Argentina", "Cuba", "Colombia"};

        //Coleccion modificable (mutable), se pueden añadir y quitar elementos
        //Collection<String> paises = new LinkedList<>(Arrays.asList(paisesArray));
        Collection<String> paises = new LinkedList<>(List.of(paisesArray)); //otra forma

        //Coleccion inmutable
        Collection<String> paisesInmutable = List.of(paisesArray);

        print(paises);
        paises.add("Inglaterra");
        print(paises);

        //Añadir elementos a la colleccion

        //Comprobamos que no se puede añadir elementos a una coleccion inmutable
        try {
            paisesInmutable.add("Belgica");
        } catch (UnsupportedOperationException e) {
            System.out.println("No se puede insertar en una coleccion inmutable - " + e);
        }

        //El metodo addAll permite añadir varios elementos que provienen de una coleccion
        paises.addAll(List.of("Japon", "Australia"));
        print(paises);

        //El metodo List.of permite como argumento un array
        paises.addAll(List.of(paisesAmericaArray));
        print(paises);

        //Eliminar elementos
        paises.remove("Francia");
        print(paises);

        //Elimina los elementos coincidientes con los de la coleccion proporcionada
        paises.removeAll(List.of("Portugal", "Brasil", "Colombia"));
        print(paises);

        //Borra todos los elementos de la coleccion menos los que estan en la coleccion proporcionada
        paises.retainAll(List.of(paisesAmericaArray));
        print(paises);

        //Borrar todos los elementos de la coleccion
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

}
