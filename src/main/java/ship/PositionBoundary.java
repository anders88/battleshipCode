package ship;

import java.util.Objects;

public class PositionBoundary {
    public final Position min;
    public final Position max;

    public PositionBoundary(Position min, Position max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PositionBoundary)) return false;
        PositionBoundary that = (PositionBoundary) o;
        return Objects.equals(min, that.min) &&
                Objects.equals(max, that.max);
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }

    public static PositionBoundary create(int x0,int y0,int x1,int y1) {
        return new PositionBoundary(new Position(x0,y0),new Position(x1,y1));
    }

    @Override
    public String toString() {
        return "PositionBoundary{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}
