package ship;

import servlet.Configuration;

import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class RandomShipGenerator implements Randomizer {
    private final int BOARD_SIZE = Configuration.boardSize();
    private final Random random = new Random();

    public Ship generate(ShipType shipType,Set<Position> usedPositions,Randomizer randomizer) {
        int direction = randomizer.nextDirection();
        PositionBoundary positionBoundary = computeBoundary(shipType, direction);
        Position position = randomizer.nextPosition(positionBoundary);
        Optional<Set<Position>> all = Optional.empty();
        while (true) {
            switch (shipType) {
                case BATTLESHIP:
                    switch (direction) {
                        case 0:
                            all = computePositions(position, 5, 1, 0, usedPositions);
                            break;
                        case 1:
                            all = computePositions(position, 5, 0, 1, usedPositions);
                            break;

                    }
                    break;
                case DESTROYER:
                    switch (direction) {
                        case 0:
                            all = computePositions(position, 3, 1, 0, usedPositions);
                            break;
                        case 1:
                            all = computePositions(position, 3, 0, 1, usedPositions);
                            break;

                    }
                    break;

            }
            if (all.isPresent()) {
                break;
            }
            position = Position.pos(position.x+1,position.y);
            if (position.x <= positionBoundary.max.x) {
                continue;
            }
            position = Position.pos(positionBoundary.min.x,position.y+1);
            if (position.y <= positionBoundary.max.y) {
                continue;
            }
            position = positionBoundary.min;
        }
        return new Ship(all.get(),shipType);
    }

    private Optional<Set<Position>> computePositions(Position position, int numTiles, int deltaX, int deltaY, Set<Position> usedPositions) {
        Set<Position> all = new HashSet<>();
        for (int i=0;i<numTiles;i++) {
            if (usedPositions.contains(position)) {
                return Optional.empty();
            }
            all.add(position);
            position = new Position(position.x+deltaX,position.y+deltaY);
        }
        return Optional.of(all);
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
