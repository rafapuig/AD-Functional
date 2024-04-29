package exercises.record;

import model.tennis.TennisPlayer;
import model.tennis.Tournament;

import java.util.*;

public class RecordWinnersManager {

    // Un mapa cuyas claves son los torneos y su valor asociado es otro mapa
    // cuya clave es el tenista y el valor asociado es un entero indicando el número de veces que lo ha ganado
    private final Map<Tournament, Map<TennisPlayer, Integer>> map = new HashMap<>();

    public Map<Tournament, Map<TennisPlayer, Integer>> getMap() {
        return map;
    }

    public void registerWinner(Tournament tournament, TennisPlayer player) {

        //Si no existe una entrada para el torneo en el mapa, la añadimos una entrada al mapa de torneos
        var tournamentWinnersMap = map.computeIfAbsent(tournament,
                t -> new HashMap<>() // Qué tiene que hacer si no encuentra el torneo: añadir una Entry en el map
        );

        // Tenemos que aumentar en una unidad el número de torneos de este tipo que ha ganado el jugador
        tournamentWinnersMap.compute(
                player, // Buscamos en el mapa la entrada con esta clave (el player)
                (key, oldValue) -> oldValue == null ? 1 : oldValue + 1);
        // la expresión lambda procesa, para la clave (el jugador): key
        // y el valor asociado actualmente (número de ediciones de ese torneo ganados hasta
        // el momento por ese jugador): oldValue
        // Puede ser que sea la primera vez que lo gana en ese caso el oldValue es null
        // Por tanto, para devolver el nuevo valor que debe quedar asociado al jugador
        // el código del cuerpo de la lambda comprueba si el valor actual es null (no ha ganado todavía)
        // y entonces devuelve 1, si no, lo que hace es devolver el valor actual incrementado en 1

        // Otra forma de hacer el aumento del número ediciones de ese torneo ganadas por el jugador
        // Mediante estas dos instrucciones:

        //tournamentMap.computeIfPresent(
        //        player, //Busca si hay una entrada para el jugador, solo la habrá si ya ha ganado este torneo alguna vez
        //        (key, oldValue) -> oldValue + 1);  //Si ya ha ganado incrementamos en 1

        //tournamentMap.computeIfAbsent(
        //        player, // Si no se encuentra el jugador
        //        p -> 1);    // entonces como es la primera vez que gana ese torneo le asociamos un 1

        //tournamentMap.putIfAbsent(player, 1);    //También podemos hacer lo anterior de esta manera (sin lambda)

        //System.out.println(map.get(tournament).get(player));
    }


    @Override
    public String toString() {

        StringJoiner sj = new StringJoiner(System.lineSeparator());

        sj.add("Palmares:");
        sj.add(System.lineSeparator());

        // Procesamos cada entrada del mapa de torneos
        map.forEach(
                (tournament, tournamentWinnersMap) -> {
                    sj.add(tournament.getName());
                    sj.add("--------------------------");

                    sj.add(processTournamentWinnersMap1(tournamentWinnersMap));

                    //Alternativamente, podríamos hacer uso del Stream API para lo mismo
                    //sj.add(processTournamentWinnersMap2(tournamentWinnersMap));

                    sj.add(System.lineSeparator());
                }
        );
        return sj.toString();
    }

    private static String processTournamentWinnersMap1(
            Map<TennisPlayer, Integer> tournamentWinnersMap) {

        StringJoiner sj = new StringJoiner(System.lineSeparator());

        List<Map.Entry<TennisPlayer, Integer>> tournamentWinners =
                new ArrayList<>(tournamentWinnersMap.entrySet());

        // Ordenamos la lista según el valor asociado a la clave de cada entrada
        tournamentWinners.sort(Comparator.comparing(Map.Entry::getValue));
        // Este código es equivalente
        tournamentWinners.sort(Map.Entry.comparingByValue());

        tournamentWinners
                .reversed() //Invierte el orden para ir de mayor a menor
                .forEach(winnerEntry ->
                        sj.add(winnerEntry.getKey().getName() + // Nombre del ganador
                               "\t" + winnerEntry.getValue())); // Número de victorias en el torneo

        return sj.toString();
    }


    // Cuando veamos el stream API veremos que se puede implementar el método de esta manera
    private static String processTournamentWinnersMap2(
            Map<TennisPlayer, Integer> tournamentWinnersMap) {

        StringJoiner sj = new StringJoiner(System.lineSeparator());

        //Comparator<Map.Entry<TennisPlayer, Integer>> comparator = Map.Entry.comparingByValue();
        //Comparator<Map.Entry<TennisPlayer, Integer>> reverse = comparator.reversed();

        //Usamos el stream API (ya se verá con más detalle en la unidad de Streams)
        tournamentWinnersMap.entrySet().stream()
                .sorted(Map.Entry.<TennisPlayer, Integer>comparingByValue().reversed())
                .forEachOrdered(winnerEntry ->
                        sj.add(winnerEntry.getKey().getName() +
                               "\t" + winnerEntry.getValue()));

        return sj.toString();
    }
}
