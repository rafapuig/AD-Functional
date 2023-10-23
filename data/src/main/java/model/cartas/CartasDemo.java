package model.cartas;

import model.cartas.scoring.BriscaScorer;
import model.cartas.scoring.NaipeScorer;

import java.util.*;
import java.util.function.ToIntFunction;

import static model.cartas.Naipes.*;

public class CartasDemo {

    public static void main(String[] args) {

        testGenerateBaraja();
        //barajaPlayersTest();

        //puntuadorTest();

        //testFilterNaipe();
    }

    static void testGenerateBaraja() {
        List<Naipe> baraja = generateBaraja();
        baraja.add(new Naipe(Palo.OROS, Valor.REY));
        System.out.println(baraja);
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


    static void testPuntuadorPorDefecto() {

        Naipe naipe = new Naipe(Palo.BASTOS, Valor.REY);

        System.out.println("Puntuando la carta: " + naipe);

        int points = Naipes.scoring(naipe);
        System.out.println("puntos = " + points);

        ToIntFunction<Naipe> scorer = NaipeScorer.DEFAULT::score;

        points = Naipes.scoring(naipe, scorer);
        System.out.println("puntos = " + points);
    }

    static void testPuntuadorLambda1() {

        Naipe naipe = new Naipe(Palo.BASTOS, Valor.REY);

        System.out.println("Puntuando la carta: " + naipe);

        //Expresion lambda
        ToIntFunction<Naipe> scorer = n -> n.getValor().getNumber() * 3;

        int points = Naipes.scoring(naipe, scorer);
        System.out.println("puntos = " + points);
    }

    static void testPuntuadorLambda2() {

        Naipe naipe = new Naipe(Palo.BASTOS, Valor.REY);

        System.out.println("Puntuando la carta: " + naipe);

        ToIntFunction<Naipe> scorer = n -> switch (n.getValor()) {
            case AS -> 11;
            case TRES -> 10;
            case REY -> 4;
            case CABALLO -> 3;
            case SOTA -> 2;
            default -> 0;
        };

        int points = Naipes.scoring(naipe, scorer);
        System.out.println("puntos = " + points);
    }

    static void  testPuntuadorClaseImplementadora() {

        Naipe naipe = new Naipe(Palo.BASTOS, Valor.REY);

        System.out.println("Puntuando la carta: " + naipe);

        ToIntFunction<Naipe> scorer = new BriscaScorer()::score;

        int points = Naipes.scoring(naipe, scorer);
        System.out.println("puntos = " + points);
    }

    static void testPuntuadorMetodoReferencia() {

        Naipe naipe = new Naipe(Palo.BASTOS, Valor.REY);

        //Referencia a metodo statico
        ToIntFunction<Naipe> scorer = Naipes::scoreByBriscaValue;
        int points = Naipes.scoring(naipe, scorer);
        System.out.println("puntos = " + points);

        //Referencia a metodo instancia implicita
        ToIntFunction<Naipe> scorer1 = new BriscaScorer()::score;
        points = Naipes.scoring(naipe, scorer);
        System.out.println("puntos = " + points);

        //Metodo referencia estatico
        ToIntFunction<Naipe> scorer2 = NaipeScorer::getDefaultScore;
        points = Naipes.scoring(naipe, scorer);
        System.out.println("puntos = " + points);
    }

    static void testGetAses() {

        List<Naipe> ases = getAses(BARAJA);

        System.out.println(ases);
    }

    static void testFilterNaipe() {

        EnumSet<Valor> figurasSet = EnumSet.of(Valor.SOTA, Valor.CABALLO, Valor.REY);

        NaipeFilter figurasFilter = naipe -> figurasSet.contains(naipe.getValor());

        List<Naipe> figuras = getNaipesFilteredBy(BARAJA, figurasFilter);

        System.out.println(figuras);
    }


}
