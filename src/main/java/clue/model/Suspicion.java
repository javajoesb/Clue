package clue.model;

public class Suspicion {

  public Suspicion(Player player, Accusation accusation) {
    this.player = player;
    this.accusation = accusation;
  }

  private final Player player;

  public Player getPlayer() {
    return player;
  }

  private final Accusation accusation;

  public Accusation getAccusation() {
    return accusation;
  }

}
