import model.football.*;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsExamplesFootball {

    public static void main(String[] args) {
        getAllGoalsOfMatchweek();
        getAllGoalsOfMatchweek2();
        getAllGoalsOfMatch();
        getAllGoalsOfMatch2();
        getGoalsScoredTeam();
        getGoalsScoredTeam2();
        matchesPlayerTeam();
        matchesPlayerTeam2();
        matchesPlayerTeam3();
        matchesPlayerByTeam();
    }

    static void getAllGoalsOfMatchweek() {

        int goals = Matches.MATCHES_SAMPLE.stream()
                .filter(match -> match.getLeague() == League.LA_LIGA)
                .filter(match -> match.getMatchweek() == 11)
                .mapToInt(match -> match.getLocalGoals().size() + match.getVisitorGoals().size())
                .sum();

        System.out.println("Goles = " + goals);

    }

    static void getAllGoalsOfMatch() {

        OptionalInt goals = Matches.MATCHES_SAMPLE.stream()
                .filter(match -> match.getLeague() == League.LA_LIGA)
                .filter(match -> match.getMatchweek() == 11)
                .filter(match -> match.getLocal() == Teams.BARCELONA)
                .filter(match -> match.getVisitor() == Teams.REAL_NADRID)
                .mapToInt(match -> match.getLocalGoals().size() + match.getVisitorGoals().size())
                .findAny();

        goals.ifPresentOrElse(
                n -> System.out.println("Goles = " + n),
                () -> System.out.println("No se encontró el partido"));
    }

    static void getAllGoalsOfMatch2() {

        Long goals = Matches.MATCHES_SAMPLE.stream()
                .filter(match -> match.getLeague() == League.LA_LIGA)
                .filter(match -> match.getMatchweek() == 11)
                .filter(match -> match.getLocal() == Teams.BARCELONA)
                .filter(match -> match.getVisitor() == Teams.REAL_NADRID)
                .collect(Collectors.teeing(
                        Collectors.mapping(Match::getLocalGoals,
                                Collectors.flatMapping(List::stream, Collectors.toList())),
                        Collectors.flatMapping(match -> match.getVisitorGoals().stream(),
                                Collectors.toList()),
                        (l1, l2) -> Stream.concat(l1.stream(), l2.stream())
                                .count()));

        if (goals != null) {
            System.out.println("Goles = " + goals);
        } else {
            System.out.println("No se encontró el partido");
        }
    }

    static void getAllGoalsOfMatchweek2() {

        Long goals = Matches.MATCHES_SAMPLE.stream()
                .filter(match -> match.getLeague() == League.LA_LIGA)
                .filter(match -> match.getMatchweek() == 11)
                .collect(Collectors.teeing(
                        Collectors.mapping(Match::getLocalGoals,
                                Collectors.flatMapping(List::stream, Collectors.toList())),
                        Collectors.flatMapping(match -> match.getVisitorGoals().stream(),
                                Collectors.toList()),
                        (l1, l2) -> Stream.concat(l1.stream(), l2.stream())
                                .count()));

        if (goals != null) {
            System.out.println("Goles = " + goals);
        } else {
            System.out.println("No se encontró el partido");
        }
    }

    static void getGoalsScoredTeam() {

        Team team = Teams.VILLAREAL;

        Long goals = Matches.MATCHES_SAMPLE.stream()
                .collect(Collectors.teeing(
                        Collectors.filtering(match -> match.getLocal() == team,
                                Collectors.mapping(Match::getLocalGoals,
                                        Collectors.flatMapping(List::stream,
                                                Collectors.counting()))
                        ), Collectors.filtering(match -> match.getVisitor() == team,
                                Collectors.mapping(Match::getVisitorGoals,
                                        Collectors.flatMapping(List::stream,
                                                Collectors.counting()))),
                        Long::sum));

        System.out.println("Goles totales del " + team.name() + " = " + goals);

    }

    static void getGoalsScoredTeam2() {

        Team team = Teams.VILLAREAL;

        Long localGoals = Matches.MATCHES_SAMPLE.stream()
                .filter(match -> match.getLocal().equals(team))
                .map(Match::getLocalGoals)
                .mapToLong(List::size)
                .sum();

        Long awayGoals = Matches.MATCHES_SAMPLE.stream()
                .filter(match -> match.getVisitor().equals(team))
                .map(Match::getVisitorGoals)
                .flatMap(List::stream)
                .count();

        long totalGoals = localGoals + awayGoals;

        System.out.println("Goles totales del " + team.name() + " = " + totalGoals);
    }

    static void matchesPlayerTeam() {
        Team team = Teams.REAL_NADRID;

        //Jugados en casa
        long home = Matches.MATCHES_SAMPLE.stream()
                .filter(match -> match.getLocal().equals(team))
                .count();

        //Jugados fuera de casa
        long away = Matches.MATCHES_SAMPLE.stream()
                .filter(match -> match.getVisitor().equals(team))
                .count();

        long total = home + away;

        System.out.println(team.name() + " ha jugado " +
                home + " partidos en casa y " +
                away + " partidos fuera, total = " + total);
    }

    static void matchesPlayerTeam2() {

        Function<Team, Predicate<Match>> playsLocal =
                aTeam -> match -> match.getLocal().equals(aTeam);

        Function<Team, Predicate<Match>> playsAway =
                aTeam -> match -> match.getVisitor().equals(aTeam);

        Function<Team, Predicate<Match>> playsMatch =
                aTeam -> playsLocal.apply(aTeam).or(playsAway.apply(aTeam));

        Team team = Teams.REAL_NADRID;

        //Jugados en casa
        long home = Matches.MATCHES_SAMPLE.stream()
                .filter(playsLocal.apply(team))
                .count();

        //Jugados fuera de casa
        long away = Matches.MATCHES_SAMPLE.stream()
                .filter(playsAway.apply(team))
                .count();

        long total = Matches.MATCHES_SAMPLE.stream()
                .filter(playsMatch.apply(team))
                .count();

        System.out.println(team.name() + " ha jugado " +
                home + " partidos en casa y " +
                away + " partidos fuera, total = " + total);
    }

    static void matchesPlayerTeam3() {

        Function<Team, Predicate<Match>> playsLocal =
                aTeam -> match -> match.getLocal().equals(aTeam);

        Function<Team, Predicate<Match>> playsAway =
                aTeam -> match -> match.getVisitor().equals(aTeam);

        Function<Team, Predicate<Match>> playsMatch =
                aTeam -> playsLocal.apply(aTeam).or(playsAway.apply(aTeam));

        Team team = Teams.REAL_NADRID;

        var stats = Matches.MATCHES_SAMPLE.stream()
                .collect(Collectors.teeing(
                        Collectors.teeing(
                                Collectors.filtering(playsAway.apply(team),
                                        Collectors.counting()),
                                Collectors.filtering(playsLocal.apply(team),
                                        Collectors.counting()),
                                List::of),
                        Collectors.filtering(playsMatch.apply(team),
                                Collectors.counting()),
                        (list, total) -> List.of(list.get(1), list.get(0), total))); // Stream.concat(list.stream(), Stream.of(total))


        //Jugados en casa
        long home = stats.get(0);

        //Jugados fuera de casa
        long away = stats.get(1);

        long total = stats.get(2);

        System.out.println(team.name() + " ha jugado " +
                home + " partidos en casa y " +
                away + " partidos fuera, total = " + total);
    }

    static void matchesPlayerByTeam() {

        Function<Team, Predicate<Match>> playsLocal =
                aTeam -> match -> match.getLocal().equals(aTeam);

        Function<Team, Predicate<Match>> playsAway =
                aTeam -> match -> match.getVisitor().equals(aTeam);

        Function<Team, Predicate<Match>> playsMatch =
                aTeam -> playsLocal.apply(aTeam).or(playsAway.apply(aTeam));

        Team team = Teams.REAL_NADRID;

        var stats = Matches.MATCHES_SAMPLE.stream()
                .collect(Collectors.teeing(
                        Collectors.groupingBy(Match::getLocal,
                                Collectors.counting()),
                        Collectors.groupingBy(Match::getVisitor,
                                Collectors.counting()),
                        (m1, m2) -> {
                            Map<Team, Long> totales = new HashMap<>(m2);

                            m1.forEach((Team key, Long value) ->
                                    totales.merge(key, value, Long::sum));
                            //m2.compute(key, (k,v) -> v == null ? value : v + value));
                            return List.of(m1,m2,totales);
                        }
                ));

        stats.get(0).forEach(
                (key, value) -> System.out.println(key.name() + " = " + value));

        stats.get(1).forEach(
                (key, value) -> System.out.println(key.name() + " = " + value));

        stats.get(2).forEach(
                (key, value) -> System.out.println(key.name() + " = " + value));

    }


}
