package ship;

public enum ShipType {
    CARRIER(8),BATTLESHIP(7),SUBMARINE(4),DESTROYER(3),MINESWEEPER(2);

    public final int shots;

    ShipType(int shots) {
        this.shots = shots;
    }
}
