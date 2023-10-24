package es.rafapuig.exercises.cartas;

import es.rafapuig.exercises.cartas.filtering.NaipeFilter;
import es.rafapuig.exercises.cartas.scoring.BriscaScorer;
import es.rafapuig.exercises.cartas.scoring.NaipeScorer;
import model.cartas.Naipe;
import model.cartas.Palo;
import model.cartas.Valor;

import java.util.*;

import static es.rafapuig.exercises.cartas.Naipes.*;

public class CartasDemo {

    public static void main(String[] args) {

        //barajaPlayersTest();
        testPuntuadorPorDefecto();
        testFilterNaipe();
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

        NaipeScorer scorer = NaipeScorer.DEFAULT;

        points = Naipes.scoring(naipe, scorer);
        System.out.println("puntos = " + points);
    }

    static void testPuntuadorAnonimo() {

        Naipe naipe = new Naipe(Palo.BASTOS, Valor.REY);

        System.out.println("Puntuando la carta: " + naipe);

        NaipeScorer scorer = new NaipeScorer() {
            @Override
            public int score(Naipe naipe) {
                return switch (naipe.getValor()) {
                    case AS -> 11;
                    case TRES -> 10;
                    case REY -> 4;
                    case CABALLO -> 3;
                    case SOTA -> 2;
                    default -> 0;
                };
            }
        };

        int points = Naipes.scoring(naipe, scorer);
        System.out.println("puntos = " + points);
    }

    static void  testPuntuadorClaseImplementadora() {

        Naipe naipe = new Naipe(Palo.BASTOS, Valor.REY);

        System.out.println("Puntuando la carta: " + naipe);

        NaipeScorer scorer = new BriscaScorer();

        int points = Naipes.scoring(naipe, scorer);
        System.out.println("puntos = " + points);
    }

    static void testPuntuadorMetodoReferencia() {

        Naipe naipe = new Naipe(Palo.BASTOS, Valor.REY);

        int points = Naipes.scoreByBriscaValue(naipe);
        System.out.println("puntos = " + points);

        points = NaipeScorer.getDefaultScore(naipe);
        System.out.println("puntos = " + points);
    }


    //--------------filtrado de la listas de cartas

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

    static void testFilterNaipe2() {

        NaipeFilter figurasFilter = Naipes::isFigura;

        List<Naipe> figuras = getNaipesFilteredBy(BARAJA, figurasFilter);

        System.out.println(figuras);
    }



}
