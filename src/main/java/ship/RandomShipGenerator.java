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
                case CARRIER:
                    switch (direction) {
                        case 0:
                            all = computePositions(position,3,1,0,usedPositions);
                            Optional<Set<Position>> rest = computePositions(Position.pos(position.x + 1, position.y + 1), 3, 1, 0, usedPositions);
                            if (all.isPresent() && rest.isPresent()) {
                                all.get().addAll(rest.get());
                            } else {
                                all = Optional.empty();
                            }
                            break;
                        case 1:
                            all = computePositions(position,3,0,1,usedPositions);
                            rest = computePositions(Position.pos(position.x + 1, position.y + 1), 3, 0, 1, usedPositions);
                            if (all.isPresent() && rest.isPresent()) {
                                all.get().addAll(rest.get());
                            } else {
                                all = Optional.empty();
                            }
                            break;
                        case 2:
                            all = computePositions(position,3,1,0,usedPositions);
                            rest = computePositions(Position.pos(position.x - 1, position.y + 1), 3, 1, 0, usedPositions);
                            if (all.isPresent() && rest.isPresent()) {
                                all.get().addAll(rest.get());
                            } else {
                                all = Optional.empty();
                            }
                            break;
                        case 3:
                            all = computePositions(position,3,0,1,usedPositions);
                            rest = computePositions(Position.pos(position.x + 1, position.y - 1), 3, 0, 1, usedPositions);
                            if (all.isPresent() && rest.isPresent()) {
                                all.get().addAll(rest.get());
                            } else {
                                all = Optional.empty();
                            }
                            break;
                    }
                    break;
                case SUBMARINE:
                    switch (direction) {
                        case 0:
                            all = computePositions(position,3,1,0,usedPositions);
                            Optional<Set<Position>> rest = computePositions(Position.pos(position.x + 1, position.y - 1), 1, 0, 0, usedPositions);
                            if (all.isPresent() && rest.isPresent()) {
                                all.get().addAll(rest.get());
                            } else {
                                all = Optional.empty();
                            }
                            break;
                        case 1:
                            all = computePositions(position,3,0,1,usedPositions);
                            rest = computePositions(Position.pos(position.x + 1, position.y + 1), 1, 0, 0, usedPositions);
                            if (all.isPresent() && rest.isPresent()) {
                                all.get().addAll(rest.get());
                            } else {
                                all = Optional.empty();
                            }
                            break;
                        case 2:
                            all = computePositions(position,3,1,0,usedPositions);
                            rest = computePositions(Position.pos(position.x + 1, position.y + 1), 1, 0, 0, usedPositions);
                            if (all.isPresent() && rest.isPresent()) {
                                all.get().addAll(rest.get());
                            } else {
                                all = Optional.empty();
                            }
                            break;
                        case 3:
                            all = computePositions(position,3,0,1,usedPositions);
                            rest = computePositions(Position.pos(position.x - 1, position.y + 1), 1, 0, 0, usedPositions);
                            if (all.isPresent() && rest.isPresent()) {
                                all.get().addAll(rest.get());
                            } else {
                                all = Optional.empty();
                            }
                            break;
                    }
                    break;
                case BATTLESHIP:
                    switch (direction) {
                        case 0:
                            all = computePositions(position, 5, 1, 0, usedPositions);
                            break;
                        case 1:
                            all = computePositions(position, 5, 0, 1, usedPositions);
                            break;
                        case 2:
                            all = computePositions(position, 5, 1, 1, usedPositions);
                            break;
                        case 3:
                            all = computePositions(position, 5, 1, -1, usedPositions);
                            break;
                        default:
                            throw new RuntimeException("Unknown direction " + direction + " for " + shipType);

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
                        case 2:
                            all = computePositions(position, 3, 1, 1, usedPositions);
                            break;
                        case 3:
                            all = computePositions(position, 3, 1, -1, usedPositions);
                            break;
                        default:
                            throw new RuntimeException("Unknown direction " + direction + " for " + shipType);

                    }
                    break;
                case MINESWEEPER:
                    switch (direction) {
                        case 0:
                            all = computePositions(position, 2, 1, 0, usedPositions);
                            break;
                        case 1:
                            all = computePositions(position, 2, 0, 1, usedPositions);
                            break;
                        case 2:
                            all = computePositions(position, 2, 1, 1, usedPositions);
                            break;
                        case 3:
                            all = computePositions(position, 2, 1, -1, usedPositions);
                            break;
                        default:
                            throw new RuntimeException("Unknown direction " + direction + " for " + shipType);

                    }
                    break;
                default:
                    throw new RuntimeException("Unknown type " + shipType);

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
            case CARRIER:
                switch (direction) {
                    case 0:
                        return PositionBoundary.create(0,0,BOARD_SIZE-4,BOARD_SIZE-2);
                    case 1:
                        return PositionBoundary.create(0,0,BOARD_SIZE-2,BOARD_SIZE-4);
                    case 2:
                        return PositionBoundary.create(0,1,BOARD_SIZE-4,BOARD_SIZE-1);
                    case 3:
                        return PositionBoundary.create(1,0,BOARD_SIZE-1,BOARD_SIZE-4);
                    default:
                        throw new RuntimeException("Unknown direction " + direction + " for " + shipType);

                }
            case SUBMARINE:
                switch (direction) {
                    case 0:
                        return PositionBoundary.create(0,1,BOARD_SIZE-3,BOARD_SIZE-1);
                    case 1:
                        return PositionBoundary.create(0,0,BOARD_SIZE-2,BOARD_SIZE-3);
                    case 2:
                        return PositionBoundary.create(0,0,BOARD_SIZE-3,BOARD_SIZE-2);
                    case 3:
                        return PositionBoundary.create(1,0,BOARD_SIZE-1,BOARD_SIZE-3);
                    default:
                        throw new RuntimeException("Unknown direction " + direction + " for " + shipType);

                }
            case BATTLESHIP:
                switch (direction) {
                    case 0:
                        return PositionBoundary.create(0,0,BOARD_SIZE-5,BOARD_SIZE-1);
                    case 1:
                        return PositionBoundary.create(0,0,BOARD_SIZE-1,BOARD_SIZE-5);
                    case 2:
                        return PositionBoundary.create(0,0,BOARD_SIZE-5,BOARD_SIZE-5);
                    case 3:
                        return PositionBoundary.create(0,4,BOARD_SIZE-5,BOARD_SIZE-1);
                    default:
                        throw new RuntimeException("Unknown direction " + direction + " for " + shipType);

                }
            case DESTROYER:
                switch (direction) {
                    case 0:
                        return PositionBoundary.create(0,0,BOARD_SIZE-3,BOARD_SIZE-1);
                    case 1:
                        return PositionBoundary.create(0,0,BOARD_SIZE-1,BOARD_SIZE-3);
                    case 2:
                        return PositionBoundary.create(0,0,BOARD_SIZE-3,BOARD_SIZE-3);
                    case 3:
                        return PositionBoundary.create(0,2,BOARD_SIZE-3,BOARD_SIZE-1);
                    default:
                        throw new RuntimeException("Unknown direction " + direction + " for " + shipType);
                }
            case MINESWEEPER:
                switch (direction) {
                    case 0:
                        return PositionBoundary.create(0,0,BOARD_SIZE-2,BOARD_SIZE-1);
                    case 1:
                        return PositionBoundary.create(0,0,BOARD_SIZE-1,BOARD_SIZE-2);
                    case 2:
                        return PositionBoundary.create(0,0,BOARD_SIZE-2,BOARD_SIZE-2);
                    case 3:
                        return PositionBoundary.create(0,2,BOARD_SIZE-2,BOARD_SIZE-1);
                    default:
                        throw new RuntimeException("Unknown direction " + direction + " for " + shipType);
                }
            default:
                throw new RuntimeException("Unknown shiptype " + shipType);

        }
    }

    @Override
    public int nextDirection() {
        return random.nextInt(4);
    }

    @Override
    public Position nextPosition(PositionBoundary positionBoundary) {
        int xpos = random.nextInt(positionBoundary.max.x - positionBoundary.min.x + 1) + positionBoundary.min.x;
        int ypos = random.nextInt(positionBoundary.max.y - positionBoundary.min.y + 1) + positionBoundary.min.y;
        return new Position(xpos,ypos);
    }
}
