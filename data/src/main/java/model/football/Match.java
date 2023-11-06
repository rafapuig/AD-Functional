package model.football;

import java.time.LocalDateTime;
import java.util.List;

public class Match {
    private final LocalDateTime dateTime;
    private final League league;
    private final int matchweek;

    private final Stadium stadium;
    private final Team local;
    private final Team visitor;
    private final List<Goal> localGoals;
    private final List<Goal> visitorGoals;

    public Match(LocalDateTime dateTime, League league, int matchweek, Stadium stadium, Team local, Team visitor, List<Goal> localGoals, List<Goal> visitorGoals) {
        this.dateTime = dateTime;
        this.league = league;
        this.matchweek = matchweek;
        this.stadium = stadium;
        this.local = local;
        this.visitor = visitor;
        this.localGoals = localGoals;
        this.visitorGoals = visitorGoals;
    }


    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public League getLeague() {
        return league;
    }

    public int getMatchweek() {
        return matchweek;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public Team getLocal() {
        return local;
    }

    public Team getVisitor() {
        return visitor;
    }

    public List<Goal> getLocalGoals() {
        return localGoals;
    }

    public List<Goal> getVisitorGoals() {
        return visitorGoals;
    }

    @Override
    public String toString() {
        return String.format("%s %d - %d %s",
                local, localGoals.size(), visitorGoals.size(), visitor) +
                dateTime.toString();
    }
}
