package clue.gui;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clue.ClueEngine;
import clue.event.GameError;
import clue.event.GameEvent;
import clue.event.GameEventAdapter;
import clue.model.Accusation;
import clue.model.Player;
import clue.model.Room;
import clue.model.Suspect;
import clue.model.Suspicion;
import clue.model.Weapon;

public class Board extends JPanel {

  private static final long serialVersionUID = 1L;

  private RoomPanel cellarPanel;
  private final Box roomBox;
  private final Box playerBox;
  private final ClueFrame parent;
  private final EvidencePanel evidencePanel;

  public Board(ClueFrame parent) {
    this.parent = parent;
    roomBox = Box.createHorizontalBox();
    playerBox = Box.createHorizontalBox();
    evidencePanel = new EvidencePanel();

    initGui();
    initListeners();
  }

  private void initGui() {
    for (Room room : Room.values()) {
      if (!Room.Cellar.equals(room)) {
        roomBox.add(new RoomPanel(room));
      }
    }
    // put cellar at the end
    roomBox.add(cellarPanel = new RoomPanel(Room.Cellar));

    add(roomBox);
    add(playerBox);
    add(evidencePanel);
    parent.validate();
    parent.repaint();
  }

  private void initListeners() {
    ClueEngine.get().addGameListener(new GameEventAdapter() {
      @Override
      public void startGame(GameEvent e) {
        for (Player player : ClueEngine.get().getPlayers()) {
          PlayerPanel playerPanel = new PlayerPanel(player);
          if (player.isCurrentPlayer()) {
            playerPanel.showCards();
          }
          playerBox.add(playerPanel);
        }
        // put remaining cards in the cellar.
        cellarPanel.addCards(ClueEngine.get().getCellarCards());

        Board.this.parent.validate();
        Board.this.parent.repaint();
      }

      @Override
      public void movePlayer(Room room, Suspect suspect) {
        clearSuspcision();
        Component[] components = roomBox.getComponents();
        for (Component component : components) {
          if (component instanceof RoomPanel) {
            RoomPanel roomPanel = (RoomPanel) component;
            if (roomPanel.isRoom(room)) {
              roomPanel.addSuspect(suspect);
            } else {
              roomPanel.removeSuspect(suspect);
            }
            roomPanel.redraw();
          }
        }
      }

      public void moveWeapon(Room room, Weapon weapon) {
        clearSuspcision();
        Component[] components = roomBox.getComponents();
        for (Component component : components) {
          if (component instanceof RoomPanel) {
            RoomPanel roomPanel = (RoomPanel) component;
            if (roomPanel.isRoom(room)) {
              roomPanel.addWeapon(weapon);
            } else {
              roomPanel.removeWeapon(weapon);
            }
            roomPanel.redraw();
          }
        }
      }

      public void clearSuspcision() {
        Component[] components = roomBox.getComponents();
        for (Component component : components) {
          if (component instanceof RoomPanel) {
            RoomPanel roomPanel = (RoomPanel) component;
            roomPanel.clearSuspect();
            roomPanel.clearWeapon();
            roomPanel.redraw();
          }
        }
      }

      @Override
      public void makeSuspcision(Suspicion suspicion) {
        Accusation accusation = suspicion.getAccusation();
        movePlayer(accusation.getRoom(), accusation.getSuspect());
        moveWeapon(accusation.getRoom(), accusation.getWeapon());
        Component[] components = roomBox.getComponents();
        for (Component component : components) {
          if (component instanceof RoomPanel) {
            RoomPanel roomPanel = (RoomPanel) component;
            if (roomPanel.isRoom(accusation.getRoom())) {
              roomPanel.accuse(accusation.getSuspect());
              roomPanel.accuse(accusation.getWeapon());
            }
            roomPanel.redraw();
          }
        }
      }

      @Override
      public void error(GameError error) {
        JOptionPane.showMessageDialog(Board.this.parent, error.getMessage());
      }
    });
  }
}
