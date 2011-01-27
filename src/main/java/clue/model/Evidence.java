package clue.model;

public class Evidence {

  private final Player playerWithCard;
  private final Card card;

  public Evidence(Player playerWithCard, Card card) {
    this.playerWithCard = playerWithCard;
    this.card = card;
  }

  public Player whoShowed() {
    return this.playerWithCard;
  }

  public Card whichCard() {
    return this.card;
  }
}
