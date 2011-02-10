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

  private static final List<GameEventListener> listeners;
  private static final LinkedList<Card> cellarCards;
  private static Accusation accusation;
  private static boolean isStarted;
  private static List<Player> players;

  static {
    isStarted = false;
    listeners = new LinkedList<GameEventListener>();
    cellarCards = new LinkedList<Card>();
    accusation = null;
  }

  public static void addGameListener(GameEventListener eventListener) {
    listeners.add(eventListener);
  }

  private static Player buildPlayerList(Suspect currentSuspect) {
    players = new LinkedList<Player>();
    // put current player first
    final Player currentPlayer = new Player(currentSuspect);
    currentPlayer.setCurrentPlayer(true);
    players.add(currentPlayer);
    for (final Suspect suspect : Suspect.values()) {
      if (!suspect.equals(currentSuspect)) {
        players.add(new Player(suspect));
      }
    }
    return currentPlayer;
  }

  public static Player currentPlayer() {
    for (final Player player : players) {
      if (player.isCurrentPlayer()) {
        return player;
      }
    }
    throw new RuntimeException("Dear developer, I could not find my current player");
  }

  private static void dealCards(List<Card> cards, int numberOfPlayers) {
    Iterator<Card> itr = cards.iterator();
    int playerIndex = 0;
    // dish out the cards to each player
    while (itr.hasNext()) {
      players.get(playerIndex).addCard(itr.next());
      itr.remove();
      playerIndex++;
      if (playerIndex > numberOfPlayers - 1) {
        playerIndex = 0;
        if (cards.size() < numberOfPlayers) {
          break;
        }
      }
    }
    // put the rest of the cards in the cellar.
    itr = cards.iterator();
    while (itr.hasNext()) {
      cellarCards.add(itr.next());
      itr.remove();
    }
  }

  public static void enterRoom(Room room, Suspect suspect) {
    for (final GameEventListener event : listeners) {
      event.movePlayer(room, suspect);
    }
  }

  private static void fireAddEvidence(final Evidence evidence) {
    SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {
        for (final GameEventListener event : listeners) {
          event.addEvidence(evidence);
        }
      }
    });
  }

  public static List<Card> getCellarCards() {
    return new LinkedList<Card>(cellarCards);
  }

  public static List<Player> getPlayers() {
    return new LinkedList<Player>(players);
  }

  public static boolean isAccusationReal(Accusation accusation) {
    return ClueEngine.accusation.equals(accusation);
  }

  public static void makeSuspicion(final Player currentPlayer, final Accusation suspicion) {
    if (suspicion.getRoom() != null && suspicion.getRoom().equals(Room.Cellar)) {
      for (final GameEventListener event : listeners) {
        event.error(new GameError(String.format("You can't make an accusation in the %s, Try just entering the %s", Room.Cellar.name(), Room.Cellar.name())));
      }
    } else {
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          for (final GameEventListener event : listeners) {
            event.makeSuspcision(new Suspicion(currentPlayer, suspicion));
          }
        }
      });
      SwingUtilities.invokeLater(new Runnable() {

        @Override
        public void run() {
          for (final Player player : players) {
            if (!player.equals(currentPlayer) && player.hasCardFor(suspicion)) {
              final Card card = player.chooseCardFor(currentPlayer, suspicion);
              for (final GameEventListener event : listeners) {
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

  private static List<Card> populateCards() {
    final LinkedList<Card> cards = new LinkedList<Card>();
    for (final Suspect suspect : Suspect.values()) {
      cards.add(suspect);
    }
    for (final Weapon weapon : Weapon.values()) {
      cards.add(weapon);
    }
    for (final Room room : Room.values()) {
      if (!Room.Cellar.equals(room)) {
        cards.add(room);
      }
    }
    return cards;
  }

  private static void publishMarkMyCards(Player currentPlayer) {
    final List<Card> cards = currentPlayer.getCards();
    for (final Card card : cards) {
      Accusation suspicion;
      if (card instanceof Suspect) {
        suspicion = new Accusation((Suspect) card, null, null);
      } else if (card instanceof Room) {
        suspicion = new Accusation(null, (Room) card, null);
      } else if (card instanceof Weapon) {
        suspicion = new Accusation(null, null, (Weapon) card);
      } else {
        throw new RuntimeException(String.format("Dear developer, I don't what kind of card %s is", card.getClass()));
      }
      makeSuspicion(currentPlayer, suspicion);
      fireAddEvidence(new Evidence(currentPlayer, card));
    }
  }

  private static void publishStartGame(final GameEvent gameEvent) {
    for (final GameEventListener l : listeners) {
      l.startGame(gameEvent);
    }
  }

  private static void selectSuspectRoomWepon(List<Card> cards) {
    Weapon weapon = null;
    Room room = null;
    Suspect suspect = null;
    final Iterator<Card> itr = cards.iterator();
    while (itr.hasNext()) {
      final Card card = itr.next();
      if (card instanceof Weapon && weapon == null) {
        weapon = (Weapon) card;
        itr.remove();
      } else if (card instanceof Room && room == null) {
        room = (Room) card;
        itr.remove();
      } else if (card instanceof Suspect && suspect == null) {
        suspect = (Suspect) card;
        itr.remove();
      }
    }
    accusation = new Accusation(suspect, room, weapon);
  }

  private static void shuffle(List<Card> cards) {
    if (!Boolean.getBoolean("skip.shuffle")) {
      Collections.shuffle(cards);
    }
  }

  public static void startGame(Integer numberOfPlayers, Suspect currentSuspect) {
    if (isStarted) {
      return;
    }
    final List<Card> cards = populateCards();
    shuffle(cards);
    selectSuspectRoomWepon(cards);
    final Player currentPlayer = buildPlayerList(currentSuspect);
    dealCards(cards, numberOfPlayers);
    publishStartGame(new GameEvent(GameState.START));
    publishMarkMyCards(currentPlayer);
    isStarted = true;
  }
}
