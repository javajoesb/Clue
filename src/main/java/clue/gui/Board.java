package clue.gui;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import clue.ClueEngine;
import clue.event.GameEvent;
import clue.event.GameEventListener;
import clue.model.Player;
import clue.model.Room;

public class Board extends JPanel implements GameEventListener {

  private static final long serialVersionUID = 1L;

  private List<PlayerPanel> playerPanels;
  private List<RoomPanel> roomPanels;
  private RoomPanel cellarPanel;

  private final ClueFrame parent;

  public Board(ClueFrame parent) {
    this.parent = parent;
    playerPanels = new LinkedList<PlayerPanel>();
    roomPanels = new LinkedList<RoomPanel>();
    for (Room room : Room.values()) {
      RoomPanel roomPanel = new RoomPanel(room);
      add(roomPanel);
      roomPanels.add(roomPanel);
      if (Room.Cellar.equals(room)) {
        cellarPanel = roomPanel;
      }
    }
    parent.validate();
    parent.repaint();
  }

  @Override
  public void startGame(GameEvent e) {
    List<Player> players = ClueEngine.get().getPlayers();
    removePreviousPlayers();
    for (Player player : players) {
      PlayerPanel playerPanel = new PlayerPanel(player);
      add(playerPanel);
      playerPanels.add(playerPanel);
    }
    cellarPanel.addCards(ClueEngine.get().getCellarCards());

    parent.validate();
    parent.repaint();
  }

  private void removePreviousPlayers() {
    for (PlayerPanel playerPanel : playerPanels) {
      this.remove(playerPanel);
    }
    playerPanels.clear();
  }
}
