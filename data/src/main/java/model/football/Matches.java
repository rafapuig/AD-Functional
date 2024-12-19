package model.football;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public class Matches {

    public static List<Match> MATCHES_SAMPLE = List.of(
            new Match(
                    LocalDateTime.of(2023, Month.OCTOBER, 28, 16, 30),
                    League.LA_LIGA, 11,
                    Stadiums.CAMPNOU, Teams.BARCELONA, Teams.REAL_NADRID,
                    List.of(new Goal(Players.GUNDOGAN, 6)),
                    List.of(new Goal(Players.BELLINGHAM, 69),
                            new Goal(Players.BELLINGHAM, 92))),
            new Match(
                    LocalDateTime.of(2023, Month.OCTOBER, 28, 16, 30),
                    League.LA_LIGA, 11,
                    Stadiums.LOS_CARMENES, Teams.GRANADA, Teams.VILLAREAL,
                    List.of(new Goal(Players.G_VILLAR, 29),
                            new Goal(Players.BRYAN, 34)),
                    List.of(new Goal(Players.SORLOTH, 18),
                            new Goal(Players.SORLOTH, 23),
                            new Goal(Players.PAREJO, 28)))
    );

}
