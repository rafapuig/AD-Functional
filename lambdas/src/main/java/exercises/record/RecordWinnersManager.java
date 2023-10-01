package exercises.record;

import model.tennis.TennisPlayer;
import model.tennis.Tournament;

import java.util.*;

public class RecordWinnersManager {

    Map<Tournament, Map<TennisPlayer, Integer>> map = new HashMap<>();

    public void registerWinner(Tournament tournament, TennisPlayer player) {

        //Si no existe una entrada para el torneo la añadimos
        var tournamentMap = map.computeIfAbsent(tournament,
               t -> new HashMap<>()
        );

        //Integer i = tournamentMap.computeIfAbsent(player, p -> 1);
        //tournamentMap.computeIfPresent(player, (k, v) -> v + 1);

        tournamentMap.compute(player, (k,v) -> v == null ? 1 : v + 1);

        //System.out.println(map.get(tournament).get(player));
    }

    public Map<Tournament, Map<TennisPlayer, Integer>> getMap() {
        return map;
    }

    @Override
    public String toString() {

        StringJoiner sj = new StringJoiner("\n");

        sj.add("Palmares:\n");

        map.forEach(
                (t,tm) ->  {
                    sj.add(t.getName() + "\n--------------------------");
                    List<Map.Entry<TennisPlayer,Integer>> winners = new ArrayList<>(tm.entrySet());

                    winners.sort(Comparator.comparing(Map.Entry::getValue));
                    winners = winners.reversed();

                    winners.forEach(w -> sj.add(w.getKey().getName() + "\t" + w.getValue()));

                    sj.add("\n");
                }
        );

        return sj.toString();

       /* return new StringJoiner(", ", RecordWinnersManager.class.getSimpleName() + "[", "]")
                .add("map=" + map)
                .toString();*/
    }
}
