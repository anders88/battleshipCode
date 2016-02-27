package game;

import ship.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameCentral {
    private GameCentral() {
    }

    private static final GameCentral _instance = new GameCentral();

    public static GameCentral instance() {
        return _instance;
    }

    private transient List<Player> players = new ArrayList<>();

    public Player addPlayer(String name) {
        Player player = new Player(name);
        synchronized (players) {
            players.add(player);
        }
        return player;
    }

    public ShotResult shot(String id, List<Position> shots) {
        Optional<Player> playerOptional = players.stream()
                .filter(pl -> pl.getId().equals(id))
                .findAny();
        if (!playerOptional.isPresent()) {
            throw new FunctionalError(String.format("Illegal id '%s'",id));
        }
        Player player = playerOptional.get();
        synchronized (player) {
            SoloGame currentGame = player.getCurrentGame();
            if (currentGame ==  null || currentGame.isFinished()) {
                currentGame = new SoloGame();
                player.setCurrentGame(currentGame);
            }
            ShotResult shotResult = currentGame.fire(shots);
            if (shotResult.gameFinished) {
                player.reportFinishedGame(currentGame);
            }
            return shotResult;

        }
    }


}
