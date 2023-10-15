package es.rafapuig;

import java.util.*;

public class LegacyDemo {

    static String[] namesArray = {"Rafa", "Raul", "Ruben", "Ramon"};


    public static void main(String[] args) {



        Stack<String> stack = new Stack<>();
        stack.elements();

        BitSet bitSet = new BitSet();

        //vectorTest();
        //hashTableTest();
        //iteratorTest();
        //hashTableTest();
        iteratorRemoveTest();
        //listIteratorTest();

        //bitSetDemo();
    }

    private static void vectorTest() {

        //Vector es una clase legacy de Java 1.0
        Vector<String> namesVector = new Vector<>(Arrays.asList(namesArray));

        System.out.println("Enumeration: ");
        Enumeration<String> enumeration = namesVector.elements();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            System.out.println("name = " + name);
        }

        //Patron adaptador para usar un Iterator
        System.out.println("Enumeration adaptado a Iterator: ");
        Iterator<String> iterator = namesVector.elements().asIterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            System.out.println("name = " + name);
        }

        namesVector.add("Enrique"); // usando la interfaz List
        namesVector.addElement("Jose"); // v1.0
        namesVector.removeElementAt(0); //v1.0 no develve nada
        String elem = namesVector.remove(0);
        System.out.println("Eliminado: " + elem);

        //namesVector.forEach(System.out::println);
        System.out.println(namesVector);
    }

    private static void iteratorTest() {
        List<String> names = List.of("Rafa", "Raul", "Ruben", "Ramon");

        Iterable<String> iterable = names;
        Iterator<String> iterator = iterable.iterator();

        while (iterator.hasNext()) {
            String name = iterator.next();
            System.out.println("name = " + name);
        }

        //El bucle for each es azucar sintactica para el bucle de un iterador
        for (String name : iterable) {
            System.out.println("name = " + name);
        }

        //Expresion lambda
        iterable.forEach(name -> System.out.println("name = " + name));

        //Streams
        Collection<String> collection = names;
        collection.stream()
                .forEach(name -> System.out.println("name = " + name));
    }


    private static void hashTableTest() {
        //La clase HashTable etiende la clase abstracta Dictionary
        System.out.println("Hash table: ");
        Hashtable<Integer, String> ht = new Hashtable<>();
        ht.put(1, "Rafa");
        ht.put(5, "Ramon");
        ht.put(4, "Raul");
        ht.put(3, "Ruben");
        ht.put(2, "Ricardo");
        ht.putAll(Map.of(6, "Roberto", 8, "Emilio", 7, "Lucas"));

        //Enumeration
        for (Enumeration<String> elems = ht.elements(); elems.hasMoreElements(); ) {
            System.out.println(elems.nextElement());
        }

        for (Enumeration<Integer> elems = ht.keys(); elems.hasMoreElements(); ) {
            System.out.println(elems.nextElement());
        }

        //Adaptador de Enumeration a Iterator
        Iterator<String> iterator = ht.elements().asIterator();
        while (iterator.hasNext()) {
            System.out.println("values.next() = " + iterator.next());
        }

        //Con lambdas
        iterator.forEachRemaining(s -> System.out.println("s = " + s));


        //Iterators
        for (String s : ht.values()) { //Collection
            System.out.println("s = " + s);
        }

        for (Integer i : ht.keySet()) { //Set
            System.out.println("i = " + i);
        }

        for (Map.Entry<Integer, String> entry : ht.entrySet()) {
            System.out.println(entry);
            System.out.println("K: " + entry.getKey() + ", " + "V: " + entry.getValue());
        }

        //Aqui si se ordena
        ht.forEach((k, v) -> System.out.println("K: " + k + ", V: " + v));
    }


    static void iteratorRemoveTest() {

        Iterable<String> iterable = Arrays.asList(namesArray); //Inmutable

        //iterable = new ArrayList<String>(Arrays.asList(namesArray)); //Mutable

        Iterator<String> iterator = iterable.iterator();

        while (iterator.hasNext()) {
            String elem = iterator.next();
            System.out.println("Eliminando elem = " + elem);
            try {
                iterator.remove();
                iterator.remove();
            } catch (IllegalStateException | UnsupportedOperationException e) {
                System.out.println(e.toString());
            }
        }
    }

    static void listIteratorTest() {

        ListIterator<String> listIterator = new ArrayList<String>(Arrays.asList(namesArray)).listIterator();

        while (listIterator.hasNext()) {
            int index = listIterator.nextIndex();
            System.out.println("index = " + index);
            String item = listIterator.next();
            listIterator.set(item.toUpperCase());
            System.out.println("item = " + item);
        }

        listIterator.add("Carlos");
        listIterator.add("Pedro");

        while (listIterator.hasPrevious()) {
            int index = listIterator.previousIndex();
            System.out.println("index = " + index);
            String item = listIterator.previous();
            if (item.equals("Rafa".toUpperCase())) {
                listIterator.remove();
                System.out.println("item = " + item + " eliminado!");
            } else {
                System.out.println("item = " + item);
            }
        }
    }

    static void bitSetDemo() {
        BitSet bitSet = new BitSet(32);
        bitSet.set(7, true);
        bitSet.set(15, true);
        bitSet.or(BitSet.valueOf(new byte[]{(byte) 192}));
        System.out.println(bitSet.length());
        System.out.println(bitSet.size());
        System.out.println(bitSet);
    }

}