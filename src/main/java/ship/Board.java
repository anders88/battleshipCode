package ship;

import servlet.Configuration;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Board {

    private List<Ship> ships;

    public Board() {
        ships = new ArrayList<>();
        RandomShipGenerator randomShipGenerator = new RandomShipGenerator();
        Set<Position> usedPositions = new HashSet<>();

        for (ShipType shipType : ShipType.values()) {
            Ship generated = randomShipGenerator.generate(shipType, usedPositions, randomShipGenerator);
            usedPositions.addAll(generated.getPosition());
            ships.add(generated);
        }
    }

    public void prettyPrint(PrintStream printWriter) {
        for (int y=0;y<Configuration.boardSize();y++) {
            StringBuilder line = new StringBuilder();
            for (int x=0;x<Configuration.boardSize();x++) {
                String sign = ".";
                Position pos = new Position(x, y);
                for (Ship ship : ships) {
                    if (ship.getPosition().contains(pos)) {
                        sign = ship.getShipType().toString().substring(0,1);
                        break;
                    }
                }
                line.append(sign);
            }
            printWriter.println(line);
        }
    }

    public List<Ship> getShips() {
        return new ArrayList<>(ships);
    }

    public static void main(String[] args) {
        Board board = new Board();
        board.prettyPrint(System.out);
    }

    public boolean isFinished(Set<Position> firedUpon) {
        return !ships.stream().filter(ship -> !ship.isSunk(firedUpon)).findAny().isPresent();
    }

    public boolean isHit(Position shot) {
        return ships.stream().filter(ship -> ship.isHit(shot)).findAny().isPresent();
    }
}
