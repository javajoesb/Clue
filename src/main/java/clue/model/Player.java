package clue.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Player {

  private final Suspect suspect;
  private final List<Card> cards;
  private boolean isCurrentPlayer;
  private final Map<Player, List<Card>> displayed;

  public Player(Suspect suspect) {
    this.suspect = suspect;
    this.cards = new LinkedList<Card>();
    this.displayed = new HashMap<Player, List<Card>>();
  }

  public void addCard(Card card) {
    cards.add(card);
  }

  public Suspect asSuspect() {
    return suspect;
  }

  public Card chooseCardFor(Player currentPlayer, Accusation accusation) {
    final List<Card> list = getDisplayedList(currentPlayer);
    // return the card we have shown before.
    if (list.contains(accusation.getSuspect())) {
      return accusation.getSuspect();
    }
    if (list.contains(accusation.getWeapon())) {
      return accusation.getWeapon();
    }
    if (list.contains(accusation.getRoom())) {
      return accusation.getRoom();
    }
    // go find the card
    for (final Card card : cards) {
      if (card.equals(accusation.getRoom()) || card.equals(accusation.getWeapon()) || card.equals(accusation.getSuspect())) {
        list.add(card);
        return card;
      }
    }
    throw new RuntimeException(String.format("Dear developer, please call hasCardFor %s before choosingCard", ToStringBuilder.reflectionToString(accusation)));
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Player other = (Player) obj;
    if (suspect != other.suspect) {
      return false;
    }
    return true;
  }

  public List<Card> getCards() {
    return new LinkedList<Card>(cards);
  }

  private List<Card> getDisplayedList(Player currentPlayer) {
    List<Card> list = displayed.get(currentPlayer);
    if (list == null) {
      list = new LinkedList<Card>();
      displayed.put(currentPlayer, list);
    }
    return list;
  }

  public String getName() {
    return suspect.name();
  }

  public boolean hasCardFor(Accusation accusation) {
    for (final Card card : cards) {
      if (card.equals(accusation.getRoom()) || card.equals(accusation.getWeapon()) || card.equals(accusation.getSuspect())) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((suspect == null) ? 0 : suspect.hashCode());
    return result;
  }

  public boolean isCurrentPlayer() {
    return isCurrentPlayer;
  }

  public void setCurrentPlayer(boolean currentPlayer) {
    this.isCurrentPlayer = currentPlayer;
  }

}
