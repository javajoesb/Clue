package clue;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

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
  private final Stack<Card> cards;
  static {
    instance = new ClueEngine();
  }

  public static final ClueEngine get() {
    return instance;
  }

  private ClueEngine() {
    listeners = new LinkedList<GameEventListener>();
    cards = new Stack<Card>();
    isStarted = false;
    for (Suspect suspect : Suspect.values()) {
      cards.push(suspect);
    }
    for (Weapon weapon : Weapon.values()) {
      cards.push(weapon);
    }
    for (Room room : Room.values()) {
      cards.push(room);
    }
  }

  private boolean isStarted;

  public void startGame(Integer numberOfPlayers) {
    if (isStarted) {
      return;
    }
    shuffle();
    selectSuspectRoomWepon();
    buildPlayerList(numberOfPlayers);
    dealCards();
    publishStartGame(new GameEvent(GameState.START));
    isStarted = true;
  }

  private void dealCards() {
    Iterator<Card> itr = cards.iterator();
    int playerIndex = 0;
    while (itr.hasNext()) {
      players.get(playerIndex).addCard(itr.next());
      itr.remove();
      playerIndex++;
      if (playerIndex > players.size() - 1) {
        playerIndex = 0;
      }
    }
  }

  private void shuffle() {
    Collections.shuffle(cards);
  }

  private void buildPlayerList(Integer numberOfPlayers) {
    players = new LinkedList<Player>();
    for (int i = 0; i < numberOfPlayers; i++) {
      players.add(new Player(String.format("Player-%s", i + 1)));
    }
  }

  private void selectSuspectRoomWepon() {
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
