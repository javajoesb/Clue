package clue;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import clue.event.GameEvent;
import clue.event.GameEventListener;
import clue.event.GameState;
import clue.model.Card;
import clue.model.Player;
import clue.model.Room;
import clue.model.Suspect;
import clue.model.Weapon;

public class ClueEngine {

  private static final ClueEngine instance;
  private final List<GameEventListener> listeners;
  private final LinkedList<Card> cellarCards;
  static {
    instance = new ClueEngine();
  }

  public static final ClueEngine get() {
    return instance;
  }

  private ClueEngine() {
    isStarted = false;
    listeners = new LinkedList<GameEventListener>();
    cellarCards = new LinkedList<Card>();
  }

  private boolean isStarted;

  public void startGame(Integer numberOfPlayers) {
    if (isStarted) {
      return;
    }
    List<Card> cards = populateCards();
    shuffle(cards);
    selectSuspectRoomWepon(cards);
    buildPlayerList(numberOfPlayers);
    dealCards(cards);
    publishStartGame(new GameEvent(GameState.START));
    isStarted = true;
  }

  private void shuffle(List<Card> cards) {
    Collections.shuffle(cards);
  }

  public List<Card> getCellarCards() {
    return new LinkedList<Card>(cellarCards);
  }

  private List<Card> populateCards() {
    LinkedList<Card> cards = new LinkedList<Card>();
    for (Suspect suspect : Suspect.values()) {
      cards.add(suspect);
    }
    for (Weapon weapon : Weapon.values()) {
      cards.add(weapon);
    }
    for (Room room : Room.values()) {
      cards.add(room);
    }
    return cards;
  }

  private void dealCards(List<Card> cards) {
    Iterator<Card> itr = cards.iterator();
    int playerIndex = 0;
    while (itr.hasNext()) {
      players.get(playerIndex).addCard(itr.next());
      itr.remove();
      playerIndex++;
      if (playerIndex > players.size() - 1) {
        playerIndex = 0;
        if (cards.size() < players.size())
          break;
      }
    }
    itr = cards.iterator();
    while (itr.hasNext()) {
      cellarCards.add(itr.next());
      itr.remove();
    }
  }

  private void buildPlayerList(Integer numberOfPlayers) {
    players = new LinkedList<Player>();
    for (int i = 0; i < numberOfPlayers; i++) {
      players.add(new Player(String.format("Player-%s", i + 1)));
    }
  }

  private void selectSuspectRoomWepon(List<Card> cards) {
    Card weapon = null;
    Card room = null;
    Card suspect = null;
    Iterator<Card> itr = cards.iterator();
    while (itr.hasNext()) {
      Card card = itr.next();
      if (card instanceof Weapon && weapon == null) {
        weapon = card;
        itr.remove();
      } else if (card instanceof Room && room == null) {
        room = card;
        itr.remove();
      } else if (card instanceof Suspect && suspect == null) {
        suspect = card;
        itr.remove();
      }
    }
  }

  private void publishStartGame(GameEvent gameEvent) {
    for (GameEventListener l : listeners) {
      l.startGame(gameEvent);
    }
  }

  List<Player> players;

  public List<Player> getPlayers() {
    return new LinkedList<Player>(players);
  }

  public void addGameListener(GameEventListener eventListener) {
    listeners.add(eventListener);
  }
}
