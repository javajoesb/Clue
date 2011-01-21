package clue.model;

import java.util.LinkedList;
import java.util.List;

public class Player {

  private final Suspect suspect;
  private List<Card> cards;
  private boolean isCurrentPlayer;

  public Player(Suspect suspect) {
    this.suspect = suspect;
    this.cards = new LinkedList<Card>();
  }

  public String getName() {
    return suspect.name();
  }

  public void addCard(Card card) {
    cards.add(card);
  }

  public List<Card> getCards() {
    return new LinkedList<Card>(cards);
  }

  public boolean hasCardFor(Accusation accusation) {
    // TODO Auto-generated method stub
    return false;
  }

  public Card chooseCardFor(Accusation accusation) {
    // TODO Auto-generated method stub
    return null;
  }

  public Suspect asSuspect() {
    return suspect;
  }

  public void setCurrentPlayer(boolean currentPlayer) {
    this.isCurrentPlayer = currentPlayer;
  }

  public boolean isCurrentPlayer() {
    return isCurrentPlayer;
  }
}
