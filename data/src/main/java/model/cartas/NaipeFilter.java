package model.cartas;

@FunctionalInterface
public interface NaipeFilter {

    boolean filter(Naipe naipe);
}
