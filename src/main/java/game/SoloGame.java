package game;

import ship.Board;
import ship.Position;

import java.util.*;
import java.util.stream.Collectors;

public class SoloGame {
    private final Board board = new Board();
    private Set<Position> firedUpon = new HashSet<>();
    private int shotsLeft = 20;
    private int roundsCompleted = 0;

    private Random random = new Random();

    public ShotResult fire(List<Position> shots) {
        roundsCompleted++;
        List<Position> shotHits = calculateHits(shots);

        boolean finished = board.isFinished(firedUpon);
        if (!finished) {
            shotsLeft = updateShotsLeft(shotsLeft);
        }
        return new ShotResult(shotHits, finished,shotsLeft, roundsCompleted);

    }

    private List<Position> calculateHits(List<Position> shots) {
        if (shots == null) {
            return Collections.emptyList();
        }
        List<Position> limitedShots = shots.size() > shotsLeft ? shots.subList(0,shotsLeft) : shots;
        List<Position> result = limitedShots.stream()
                .filter(board::isHit)
                .collect(Collectors.toList());

        return result;
    }

    private int updateShotsLeft(int myShots) {
        myShots-=random.nextInt(4);
        if (myShots < 2) {
            return 1;
        }
        return myShots;

    }
}
