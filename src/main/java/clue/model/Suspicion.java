package clue.model;

public class Suspicion {

  private final Player player;

  private final Accusation accusation;

  public Suspicion(Player player, Accusation accusation) {
    this.player = player;
    this.accusation = accusation;
  }

  public Accusation getAccusation() {
    return accusation;
  }

  public Player getPlayer() {
    return player;
  }

}
