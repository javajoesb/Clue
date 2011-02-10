package clue.model;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class DiceTest {

  private Dice dice;

  @Before
  public void setUp() {
    dice = new Dice();
  }

  @Test
  public void testShake() {
    // just try a few.
    for (int i = 0; i < 1000; i++) {
      final int shake = dice.shake();
      assertTrue(String.format("%s", shake), shake > 0);
      assertTrue(String.format("%s", shake), shake < 7);
    }
  }
}
