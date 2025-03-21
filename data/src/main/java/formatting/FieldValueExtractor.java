package formatting;

public interface FieldValueExtractor<T, R> {
    R extract(T value);
}
