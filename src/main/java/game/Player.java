package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Player {
    private final String id;
    private final String name;
    private SoloGame currentGame;
    private List<Integer> rounds = new ArrayList<>();

    public Player(String name) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public SoloGame getCurrentGame() {
        return currentGame;
    }

    public Player setCurrentGame(SoloGame currentGame) {
        this.currentGame = currentGame;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void reportFinishedGame(SoloGame game) {
        rounds.add(game.getRoundsCompleted());
    }
}
