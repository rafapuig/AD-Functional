package es.rafapuig.exercises.cartas;

import java.util.*;
import java.util.function.ToIntFunction;

import static es.rafapuig.exercises.cartas.Naipes.generateBaraja;
import static es.rafapuig.exercises.cartas.Naipes.scoring;

public class CartasDemo {

    public static void main(String[] args) {

        //barajaPlayersTest();

        puntuadorTest();
    }

    private static void barajaPlayersTest() {
        List<Naipe> baraja = generateBaraja();

        System.out.println(baraja);

        Collections.shuffle(baraja);

        System.out.println(baraja);

        //Crear los abanicos de los jugadores
        List<List<Naipe>> abanicos = new ArrayList<>();

        int numPlayers = 4;
        for (int i = 0; i < numPlayers; i++) {
            List<Naipe> abanico = new LinkedList<>();
            abanicos.add(abanico);
        }

        //Repartir las cartas en los abanicos de los jugadores
        int counter = 0;
        /*for (Naipe naipe : baraja) {
            abanicos.get(counter++ % abanicos.size()).add(naipe);
        }*/

        int numNaipesPlayer = 6;
        Iterator<Naipe> iterator = baraja.listIterator();
        while (iterator.hasNext() && counter < numPlayers * numNaipesPlayer) {
            abanicos.get(counter++ % abanicos.size()).add(iterator.next());
        }
        List<Naipe> mazo = new ArrayList<>();
        iterator.forEachRemaining(mazo::add);

        for (List<Naipe> abanico : abanicos) {
            System.out.println(abanico);
        }

        System.out.println(mazo);
    }

    static void puntuadorTest() {
        Naipe naipe = new Naipe(Palo.BASTOS, Valor.REY);

        int points;

        points = Naipes.scoring(naipe);


        ToIntFunction<Naipe> scorer = n -> n.getValor().getNumber();

        points = Naipes.scoring(naipe, scorer);

        System.out.println("puntos = " + points);

        scorer = n -> switch (n.getValor()) {
            case AS -> 11;
            case TRES -> 10;
            case REY -> 4;
            case CABALLO -> 3;
            case SOTA -> 2;
            default -> 0;
        };
        points = Naipes.scoring(naipe, scorer);
        System.out.println("puntos = " + points);


        scorer = Naipes::scoreByBriscaValue;
        points = Naipes.scoring(naipe, scorer);
        System.out.println("puntos = " + points);


        Scorer scorer1 = new BriscaScorer();
        scorer1 = n -> n.getValor().getNumber() * 3;
        scorer1 = Naipes::scoreByBriscaValue;
        scorer1 = Scorer::getDefaultScore;

    }

}
