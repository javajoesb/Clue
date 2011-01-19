package clue.gui;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import clue.ClueEngine;
import clue.event.GameEvent;
import clue.event.GameEventListener;
import clue.model.Player;

public class GamePanel extends JPanel implements GameEventListener {

  private static final long serialVersionUID = 1L;

  private List<PlayerPanel> playerPanels;

  private final ClueFrame parent;

  public GamePanel(ClueFrame parent) {
    this.parent = parent;
    playerPanels = new LinkedList<PlayerPanel>();
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
