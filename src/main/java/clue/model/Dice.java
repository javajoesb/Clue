package clue.model;

import java.util.Random;

public class Dice {

  private static final Random random = new Random();

  public Dice() {
    side = shake();
  }

  private int side;

  public int getSide() {
    return side;
  }

  public int shake() {
    side = random.nextInt(6) + 1;
    return side;
  }
}
