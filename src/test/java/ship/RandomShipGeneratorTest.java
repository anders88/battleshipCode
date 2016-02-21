package ship;

import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ship.Position.pos;

public class RandomShipGeneratorTest {

    private RandomShipGenerator randomShipGenerator = new RandomShipGenerator();
    private Randomizer randomizer = mock(Randomizer.class);

    @Test
    public void shouldGenerateStraightBattleship() throws Exception {
        when(randomizer.nextDirection()).thenReturn(0);
        when(randomizer.nextPosition(any(PositionBoundary.class))).thenReturn(pos(0,0));

        Ship generated = randomShipGenerator.generate(ShipType.BATTLESHIP, Collections.emptySet(), randomizer);

        verify(randomizer).nextPosition(new PositionBoundary(pos(0,0),pos(15,19)));

        assertThat(generated.getShipType()).isEqualTo(ShipType.BATTLESHIP);
        Set<Position> position = generated.getPosition();
        assertThat(position).hasSize(5).containsOnly(pos(0,0),pos(1,0),pos(2,0),pos(3,0),pos(4,0));
    }

    @Test
    public void shouldGenerateStraightDestoryer() throws Exception {
        when(randomizer.nextDirection()).thenReturn(0);
        when(randomizer.nextPosition(any(PositionBoundary.class))).thenReturn(pos(0,0));

        Ship generated = randomShipGenerator.generate(ShipType.DESTROYER, Collections.emptySet(), randomizer);

        verify(randomizer).nextPosition(new PositionBoundary(pos(0,0),pos(17,19)));

        assertThat(generated.getShipType()).isEqualTo(ShipType.DESTROYER);
        Set<Position> position = generated.getPosition();
        assertThat(position).hasSize(3).containsOnly(pos(0,0),pos(1,0),pos(2,0));
    }

    @Test
    public void shouldGenerateDestoryerGoingDown() throws Exception {
        when(randomizer.nextDirection()).thenReturn(2);
        when(randomizer.nextPosition(any(PositionBoundary.class))).thenReturn(pos(0,0));

        Ship generated = randomShipGenerator.generate(ShipType.DESTROYER, Collections.emptySet(), randomizer);

        verify(randomizer).nextPosition(new PositionBoundary(pos(0,0),pos(17,17)));

        assertThat(generated.getShipType()).isEqualTo(ShipType.DESTROYER);
        Set<Position> position = generated.getPosition();
        assertThat(position).hasSize(3).containsOnly(pos(0,0),pos(1,1),pos(2,2));
    }

    @Test
    public void shouldGenerateVerticalBattleship() throws Exception {
        when(randomizer.nextDirection()).thenReturn(1);
        when(randomizer.nextPosition(any(PositionBoundary.class))).thenReturn(pos(0,0));

        Ship generated = randomShipGenerator.generate(ShipType.BATTLESHIP, Collections.emptySet(), randomizer);

        verify(randomizer).nextPosition(new PositionBoundary(pos(0,0),pos(19,15)));

        assertThat(generated.getShipType()).isEqualTo(ShipType.BATTLESHIP);
        Set<Position> position = generated.getPosition();
        assertThat(position).hasSize(5).containsOnly(pos(0,0),pos(0,1),pos(0,2),pos(0,3),pos(0,4));
    }

    @Test
    public void shouldAvoidUsedFields() throws Exception {
        when(randomizer.nextDirection()).thenReturn(0);
        when(randomizer.nextPosition(any(PositionBoundary.class))).thenReturn(pos(0,0));


        Set<Position> used = new HashSet<>();
        used.add(pos(2,0));
        Ship generated = randomShipGenerator.generate(ShipType.BATTLESHIP, used, randomizer);

        verify(randomizer).nextPosition(new PositionBoundary(pos(0,0),pos(15,19)));

        assertThat(generated.getShipType()).isEqualTo(ShipType.BATTLESHIP);
        Set<Position> position = generated.getPosition();
        assertThat(position).hasSize(5).containsOnly(pos(3,0),pos(4,0),pos(5,0),pos(6,0),pos(7,0));

    }
}