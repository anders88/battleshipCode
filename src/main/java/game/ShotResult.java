package game;

import ship.Position;

import java.util.List;

public class ShotResult {
    public final List<Position> hits;
    public final boolean gameFinished;
    public final int shotsNextRound;
    public final int roundsCompleted;

    public ShotResult(List<Position> hits, boolean gameFinished, int shotsNextRound, int roundsCompleted) {
        this.hits = hits;
        this.gameFinished = gameFinished;
        this.shotsNextRound = shotsNextRound;
        this.roundsCompleted = roundsCompleted;
    }
}
