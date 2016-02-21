package ship;

import servlet.Configuration;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomShipGenerator implements Randomizer {
    private final int BOARD_SIZE = Configuration.boardSize();
    private final Random random = new Random();

    public Ship generate(ShipType shipType,Set<Position> usedPositions,Randomizer randomizer) {
        int direction = randomizer.nextDirection();
        PositionBoundary positionBoundary = computeBoundary(shipType, direction);
        Position position = randomizer.nextPosition(positionBoundary);
        Set<Position> all = null;
        switch (shipType) {
            case BATTLESHIP:
                switch (direction) {
                    case 0:
                        all = computePositions(position,5, 1, 0);
                        break;
                    case 1:
                        all = computePositions(position,5, 0, 1);
                        break;

                }
                break;
            case DESTROYER:
                switch (direction) {
                    case 0:
                        all = computePositions(position,3, 1, 0);
                        break;
                    case 1:
                        all = computePositions(position,3, 0, 1);
                        break;

                }
                break;


        }
        return new Ship(all,shipType);
    }

    private Set<Position> computePositions(Position position, int numTiles, int deltaX, int deltaY) {
        Set<Position> all = new HashSet<>();
        for (int i=0;i<numTiles;i++) {
            all.add(position);
            position = new Position(position.x+deltaX,position.y+deltaY);
        }
        return all;
    }

    private PositionBoundary computeBoundary(ShipType shipType, int direction) {
        switch (shipType) {
            case BATTLESHIP:
                switch (direction) {
                    case 0:
                        return PositionBoundary.create(0,0,BOARD_SIZE-5,BOARD_SIZE-1);
                    case 1:
                        return PositionBoundary.create(0,0,BOARD_SIZE-1,BOARD_SIZE-5);

                }
                throw new RuntimeException("unknown");
            case DESTROYER:
                switch (direction) {
                    case 0:
                        return PositionBoundary.create(0,0,BOARD_SIZE-3,BOARD_SIZE-1);
                    case 1:
                        return PositionBoundary.create(0,0,BOARD_SIZE-1,BOARD_SIZE-3);
                }
                throw new RuntimeException("unknown");
        }
        return null;
    }

    @Override
    public int nextDirection() {
        return random.nextInt(4);
    }

    @Override
    public Position nextPosition(PositionBoundary positionBoundary) {

        return null;
    }
}
