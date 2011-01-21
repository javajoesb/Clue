package clue;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import clue.event.GameError;
import clue.event.GameEvent;
import clue.event.GameEventListener;
import clue.event.GameState;
import clue.model.Accusation;
import clue.model.Card;
import clue.model.Player;
import clue.model.Room;
import clue.model.Suspect;
import clue.model.Weapon;

public class ClueEngine {

  private static final ClueEngine instance;
  private final List<GameEventListener> listeners;
  private final LinkedList<Card> cellarCards;
  private Accusation currentSuspicion;
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

  public void startGame(Integer numberOfPlayers, Suspect currentPlayer) {
    if (isStarted) {
      return;
    }
    List<Card> cards = populateCards();
    shuffle(cards);
    selectSuspectRoomWepon(cards);
    buildPlayerList(currentPlayer);
    dealCards(cards, numberOfPlayers);
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
      if (!Room.Cellar.equals(room)) {
        cards.add(room);
      }
    }
    return cards;
  }

  private void dealCards(List<Card> cards, int numberOfPlayers) {
    Iterator<Card> itr = cards.iterator();
    int playerIndex = 0;
    // dish out the cards to each player
    while (itr.hasNext()) {
      players.get(playerIndex).addCard(itr.next());
      itr.remove();
      playerIndex++;
      if (playerIndex > numberOfPlayers - 1) {
        playerIndex = 0;
        if (cards.size() < numberOfPlayers)
          break;
      }
    }
    // put the rest of the cards in the cellar.
    itr = cards.iterator();
    while (itr.hasNext()) {
      cellarCards.add(itr.next());
      itr.remove();
    }
  }

  private void buildPlayerList(Suspect currentSuspect) {
    players = new LinkedList<Player>();
    // put current player first
    Player currentPlayer = new Player(currentSuspect);
    currentPlayer.setCurrentPlayer(true);
    players.add(currentPlayer);
    for (Suspect suspect : Suspect.values()) {
      if (!suspect.equals(currentSuspect)) {
        players.add(new Player(suspect));
      }
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

  public void makeSuspicion(Accusation accusation) {
    currentSuspicion = accusation;
    if (accusation.getRoom().equals(Room.Cellar)) {
      for (GameEventListener event : listeners) {
        event.error(new GameError(String.format("You can't make an accusation in the %s, Try just entering the %s", Room.Cellar.name())));
      }
    } else {
      for (GameEventListener event : listeners) {
        event.makeSuspcision(accusation);
      }
    }
  }

  public void enterRoom(Room room, Suspect suspect) {
    for (GameEventListener event : listeners) {
      event.movePlayer(room, suspect);
    }
  }
}
