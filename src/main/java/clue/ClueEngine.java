package clue;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;

import clue.event.GameError;
import clue.event.GameEvent;
import clue.event.GameEventListener;
import clue.event.GameState;
import clue.model.Accusation;
import clue.model.Card;
import clue.model.Evidence;
import clue.model.Player;
import clue.model.Room;
import clue.model.Suspect;
import clue.model.Suspicion;
import clue.model.Weapon;

public class ClueEngine {

  private static final ClueEngine instance;
  private final List<GameEventListener> listeners;
  private final LinkedList<Card> cellarCards;
  private Accusation currentSuspicion;

  private List<Player> players;

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

  public void startGame(Integer numberOfPlayers, Suspect currentSuspect) {
    if (isStarted) {
      return;
    }
    List<Card> cards = populateCards();
    shuffle(cards);
    selectSuspectRoomWepon(cards);
    final Player currentPlayer = buildPlayerList(currentSuspect);
    dealCards(cards, numberOfPlayers);
    publishStartGame(new GameEvent(GameState.START));
    publishMarkMyCards(currentPlayer);
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
      if (!Suspect.None.equals(suspect)) {
        cards.add(suspect);
      }
    }
    for (Weapon weapon : Weapon.values()) {
      if (!Weapon.None.equals(weapon)) {

        cards.add(weapon);
      }
    }
    for (Room room : Room.values()) {
      if (!Room.Cellar.equals(room) || !Room.None.equals(room)) {
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

  private Player buildPlayerList(Suspect currentSuspect) {
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
    return currentPlayer;
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

  private void publishStartGame(final GameEvent gameEvent) {
    for (GameEventListener l : listeners) {
      l.startGame(gameEvent);
    }
  }

  private void publishMarkMyCards(Player currentPlayer) {
    List<Card> cards = currentPlayer.getCards();
    for (Card card : cards) {
      Accusation accusation;
      if (card instanceof Suspect) {
        accusation = new Accusation((Suspect) card, Room.None, Weapon.None);
      } else if (card instanceof Room) {
        accusation = new Accusation(Suspect.None, (Room) card, Weapon.None);
      } else if (card instanceof Weapon) {
        accusation = new Accusation(Suspect.None, Room.None, (Weapon) card);
      } else {
        throw new RuntimeException(String.format(
            "Dear developer, I don't what kind of card %s is", card.getClass()));
      }
      makeSuspicion(currentPlayer, accusation);
      fireAddEvidence(new Evidence(currentPlayer, card));
    }
  }

  private void fireAddEvidence(final Evidence evidence) {
    SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {
        for (GameEventListener event : listeners) {
          event.addEvidence(evidence);
        }
      }
    });
  }

  public List<Player> getPlayers() {
    return new LinkedList<Player>(players);
  }

  public void addGameListener(GameEventListener eventListener) {
    listeners.add(eventListener);
  }

  public void makeSuspicion(final Player currentPlayer,
      final Accusation accusation) {
    currentSuspicion = accusation;
    if (accusation.getRoom().equals(Room.Cellar)) {
      for (GameEventListener event : listeners) {
        event.error(new GameError(String.format(
            "You can't make an accusation in the %s, Try just entering the %s",
            Room.Cellar.name(), Room.Cellar.name())));
      }
    } else {
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          for (GameEventListener event : listeners) {
            event.makeSuspcision(new Suspicion(currentPlayer, accusation));
          }
        }
      });
      SwingUtilities.invokeLater(new Runnable() {

        @Override
        public void run() {
          for (Player player : players) {
            if (!player.equals(currentPlayer) && player.hasCardFor(accusation)) {
              Card card = player.chooseCardFor(currentPlayer, accusation);
              for (GameEventListener event : listeners) {
                // stop showing cards if we showed one.
                if (event.showCard(card)) {
                  fireAddEvidence(new Evidence(player, card));
                  return;
                }
              }
            }
          }
        }
      });
    }
  }

  public void enterRoom(Room room, Suspect suspect) {
    for (GameEventListener event : listeners) {
      event.movePlayer(room, suspect);
    }
  }

  public Player currentPlayer() {
    for (Player player : players) {
      if (player.isCurrentPlayer()) {
        return player;
      }
    }
    throw new RuntimeException(
        "Dear developer, I could not find my current player");
  }
}
