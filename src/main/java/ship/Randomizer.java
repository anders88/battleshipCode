package ship;

public interface Randomizer {
    int nextDirection();
    Position nextPosition(PositionBoundary positionBoundary);
}
