package clue.model;

import java.util.Random;

public class Dice {

  private static final Random random = new Random();

  private int side;

  public Dice() {
    side = shake();
  }

  public int getSide() {
    return side;
  }

  public int shake() {
    side = random.nextInt(6) + 1;
    return side;
  }
}
