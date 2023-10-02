package functional;

import java.util.StringJoiner;
import java.util.function.Supplier;

public class Item implements Priced {
    private String name = "Desconocido";
    private double price = 0.0;

    public Item() {
        System.out.println("Llamada al constructor Item()");
    }

    public Item(String name) {
        this.name = name;
        System.out.println("Llamada al constructor Item(String)");
    }

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
        System.out.println("Llamada al constructor Item(String, double)");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Item.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("price=" + price)
                .toString();
    }

    public void test() {
        Supplier<String> s1 = this::toString;
        Supplier<String> s2 = Item.super::toString;
        Supplier<Double> d3 = this::getPrice;
        Supplier<Double> d4 = Priced.super::getPrice;

        System.out.println("this::toString = " + s1.get());
        System.out.println("Item.super::toString = " + s2.get());
        System.out.println("this::getPrice = " + d3.get());
        System.out.println("Priced.super::getPrice = " + d4.get());
    }
}
