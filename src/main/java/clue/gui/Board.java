package clue.gui;

import java.awt.Component;
import java.util.List;

import javax.swing.Box;
import javax.swing.JPanel;

import clue.ClueEngine;
import clue.event.GameEvent;
import clue.event.GameEventListener;
import clue.model.Accusation;
import clue.model.Player;
import clue.model.Room;

public class Board extends JPanel implements GameEventListener {

  private static final long serialVersionUID = 1L;

  private RoomPanel cellarPanel;
  private final Box roomBox;
  private final Box playerBox;

  private final ClueFrame parent;

  public Board(ClueFrame parent) {
    this.parent = parent;
    roomBox = Box.createHorizontalBox();
    playerBox = Box.createHorizontalBox();
    for (Room room : Room.values()) {
      if (Room.Cellar.equals(room)) {
        continue;
      }
      RoomPanel roomPanel = new RoomPanel(room);
      roomBox.add(roomPanel);
    }
    roomBox.add(cellarPanel = new RoomPanel(Room.Cellar));
    add(roomBox);
    add(playerBox);
    parent.validate();
    parent.repaint();
  }

  @Override
  public void startGame(GameEvent e) {
    playerBox.removeAll();
    List<Player> players = ClueEngine.get().getPlayers();
    for (Player player : players) {
      PlayerPanel playerPanel = new PlayerPanel(player);
      playerBox.add(playerPanel);
    }
    cellarPanel.addCards(ClueEngine.get().getCellarCards());

    parent.validate();
    parent.repaint();
  }

  @Override
  public void makeSuspcision(Accusation accusation) {
    Component[] components = roomBox.getComponents();
    for (Component component : components) {
      if (component instanceof RoomPanel) {
        RoomPanel roomPanel = (RoomPanel) component;
        if (roomPanel.isRoom(accusation.getRoom())) {
          roomPanel.addSuspect(accusation.getSuspect());
          roomPanel.addWeapon(accusation.getWeapon());
        } else {
          roomPanel.removeSuspect(accusation.getSuspect());
          roomPanel.removeWeapon(accusation.getWeapon());
        }
      }
    }
  }
}
