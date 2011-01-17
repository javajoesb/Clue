package clue;

import java.util.LinkedList;
import java.util.List;

import clue.model.Player;

public class ClueEngine {

  private boolean isStarted;
  List<Player> players;

  public ClueEngine() {
    isStarted = false;
  }

  public void startGame(Integer numberOfPlayers) {
    if (isStarted) {
      return;
    }
    players = new LinkedList<Player>();
    for (int i = 0; i < numberOfPlayers; i++) {
      players.add(new Player(String.format("Player-%s", i + 1)));
    }
  }
}
