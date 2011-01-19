package clue.model;

import java.util.LinkedList;
import java.util.List;

public class Player {

  private final String name;
  private List<Card> cards;

  public Player(String name) {
    this.name = name;
    this.cards = new LinkedList<Card>();
  }

  public String getName() {
    return name;
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
}
