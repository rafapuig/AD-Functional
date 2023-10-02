package functional;

public interface Priced {

    default double getPrice() {
        return 1.0;
    }

}
