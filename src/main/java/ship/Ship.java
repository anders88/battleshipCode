package ship;

import java.util.HashSet;
import java.util.Set;

public class Ship {
    private final Set<Position> position;
    private final ShipType shipType;

    public Ship(Set<Position> position, ShipType shipType) {
        this.position = position;
        this.shipType = shipType;
    }

    public Set<Position> getPosition() {
        return new HashSet<>(position);
    }

    public ShipType getShipType() {
        return shipType;
    }

    public boolean isSunk(Set<Position> hits) {
        return hits.containsAll(position);
    }

    public boolean isHit(Position shot) {
        return position.contains(shot);
    }
}
