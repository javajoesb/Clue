package clue.gui;

import static clue.util.BoxUtil.createHorizontalBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clue.ClueEngine;
import clue.event.GameEvent;
import clue.event.GameEventAdapter;
import clue.gui.model.RoomComboBoxModel;
import clue.gui.model.SuspectComboBoxModel;
import clue.gui.model.WeaponComboBoxModel;
import clue.model.Accusation;
import clue.model.Player;
import clue.model.Room;
import clue.model.Suspect;
import clue.model.Weapon;

public class SuspicionPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  private final JComboBox suspects;
  private final JComboBox weapons;
  private final JComboBox rooms;
  private final JButton createRumor;
  private final JButton createAccusation;
  private final JButton enterRoom;

  public SuspicionPanel() {
    this.suspects = new JComboBox(new SuspectComboBoxModel(Suspect.values()));
    this.weapons = new JComboBox(new WeaponComboBoxModel(Weapon.values()));
    this.rooms = new JComboBox(new RoomComboBoxModel(Room.values()));
    createRumor = new JButton("Rumor");
    createAccusation = new JButton("Accuse");
    enterRoom = new JButton("Enter Room");

    initGui();
    enable(false);
    initListeners();
  }

  private void initGui() {
    Box box = Box.createVerticalBox();
    box.add(createHorizontalBox(new JLabel("Room"), rooms));
    box.add(createHorizontalBox(new JLabel("Suspect"), suspects));
    box.add(createHorizontalBox(new JLabel("Weapon"), weapons));
    box.add(createHorizontalBox(createRumor, createAccusation));
    box.add(enterRoom);
    add(box);
  }

  private void initListeners() {
    createRumor.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        Player currentPlayer = ClueEngine.get().currentPlayer();
        Accusation accusation = new Accusation((Suspect) suspects
            .getSelectedItem(), (Room) rooms.getSelectedItem(),
            (Weapon) weapons.getSelectedItem());
        ClueEngine.get().makeSuspicion(currentPlayer, accusation);
      }
    });
    enterRoom.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        ClueEngine.get().enterRoom((Room) rooms.getSelectedItem(),
            (Suspect) suspects.getSelectedItem());
      }
    });
    ClueEngine.get().addGameListener(new GameEventAdapter() {
      @Override
      public void startGame(GameEvent e) {
        enable(true);
      }
    });
  }

  @Override
  public void enable(boolean isEnabled) {
    suspects.setEnabled(isEnabled);
    weapons.setEnabled(isEnabled);
    rooms.setEnabled(isEnabled);
    createRumor.setEnabled(isEnabled);
    createAccusation.setEnabled(isEnabled);
    enterRoom.setEnabled(isEnabled);
  }
}
