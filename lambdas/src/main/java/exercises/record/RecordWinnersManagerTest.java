package exercises.record;

import model.tennis.TennisPlayers;
import model.tennis.Tournaments;

public class RecordWinnersManagerTest {

    public static void main(String[] args) {
        RecordWinnersManager rwm = new RecordWinnersManager();

        rwm.registerWinner(Tournaments.ROLAND_GARROS, TennisPlayers.RAFA_NADAL);
        rwm.registerWinner(Tournaments.ROLAND_GARROS, TennisPlayers.RAFA_NADAL);
        rwm.registerWinner(Tournaments.ROLAND_GARROS, TennisPlayers.FEDERER);
        rwm.registerWinner(Tournaments.ROLAND_GARROS, TennisPlayers.RAFA_NADAL);
        rwm.registerWinner(Tournaments.WIMBLEDON, TennisPlayers.FEDERER);
        rwm.registerWinner(Tournaments.US_OPEN,TennisPlayers.RAFA_NADAL);
        rwm.registerWinner(Tournaments.US_OPEN,TennisPlayers.DJOKOVIC);
        rwm.registerWinner(Tournaments.US_OPEN,TennisPlayers.DJOKOVIC);
        rwm.registerWinner(Tournaments.US_OPEN,TennisPlayers.DJOKOVIC);
        rwm.registerWinner(Tournaments.US_OPEN,TennisPlayers.RAFA_NADAL);
        rwm.registerWinner(Tournaments.US_OPEN, TennisPlayers.FEDERER);

        System.out.println(rwm.getMap());

        System.out.println(rwm);

    }
}
