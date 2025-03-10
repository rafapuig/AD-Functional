package exercises.pairs;

public class FieldPair<T> implements Pair<T> {

    private T first;
    private T second;

    public FieldPair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public T getFirst() {
        return first;
    }

    @Override
    public T getSecond() {
        return second;
    }

    @Override
    public void setFirst(T first) {
        this.first = first;
    }

    @Override
    public void setSecond(T second) {
        this.second = second;
    }

    @Override
    public FieldPair<T> swap() {
        return new FieldPair<>(second, first);
    }

    @Override
    public boolean contains(T item) {
        return getFirst().equals(item) || getSecond().equals(item);
    }

    @Override
    public final boolean equals(Object object) {
        if (!(object instanceof FieldPair<?> fieldPair)) return false;
        return first.equals(fieldPair.first) && second.equals(fieldPair.second);
    }

    @Override
    public int hashCode() {
        int result = first.hashCode();
        result = 31 * result + second.hashCode();
        return result;
    }
}
