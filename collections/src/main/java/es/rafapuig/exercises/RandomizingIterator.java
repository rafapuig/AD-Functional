package es.rafapuig.exercises;

import java.util.*;

public class RandomizingIterator<T> implements Iterator<T> {

    private final Iterator<T> iterator;

    public RandomizingIterator(Iterator<T> iterator) {

        List<T> list = new ArrayList<T>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        Collections.shuffle(list);

        this.iterator =list.iterator();
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public T next() {
        return this.iterator.next();
    }


    // Demo de uso
    public static void main(String[] args) {

        Set<String> set = new HashSet<>() {

            @Override
            public Iterator<String> iterator() {
                return new RandomizingIterator<>(super.iterator());
            }
        };

        set.addAll(List.of("A", "B", "C", "D", "E", "F"));

        for (int i = 0; i < 5; i++) {
            System.out.println(set);
        }

        /*StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (String s : set) {
            sj.add(s);
        }
        System.out.println(sj);*/
    }

}
