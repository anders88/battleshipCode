package ship;

import java.util.Set;

public class Ship {
    private final Set<Position> position;
    private final ShipType shipType;

    public Ship(Set<Position> position, ShipType shipType) {
        this.position = position;
        this.shipType = shipType;
    }

    public Set<Position> getPosition() {
        return position;
    }

    public ShipType getShipType() {
        return shipType;
    }
}
