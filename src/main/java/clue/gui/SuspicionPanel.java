package clue.gui;

import static clue.util.BoxUtil.createHorizontalBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clue.ClueEngine;
import clue.event.GameEvent;
import clue.event.GameEventAdapter;
import clue.gui.model.RoomComboBoxModel;
import clue.model.Accusation;
import clue.model.Room;
import clue.model.Suspect;
import clue.model.Weapon;

public class SuspicionPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  private final ClueFrame parent;
  private final JComboBox suspects;
  private final JComboBox weapons;
  private final JComboBox rooms;
  private final ComboBoxModel suspectModel;
  private final ComboBoxModel weaponModel;
  private final ComboBoxModel roomModel;
  private final JButton createRumor;
  private final JButton createAccusation;
  private final JButton enterRoom;

  public SuspicionPanel(ClueFrame parent) {
    this.parent = parent;
    this.suspects = new JComboBox(this.suspectModel = new DefaultComboBoxModel(Suspect.values()));
    this.weapons = new JComboBox(this.weaponModel = new DefaultComboBoxModel(Weapon.values()));
    this.rooms = new JComboBox(this.roomModel = new RoomComboBoxModel(Room.values()));
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
        ClueEngine.get()
            .makeSuspicion(ClueEngine.get().currentPlayer(), new Accusation((Suspect) suspects.getSelectedItem(), (Room) rooms.getSelectedItem(), (Weapon) weapons.getSelectedItem()));
      }
    });
    enterRoom.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        ClueEngine.get().enterRoom((Room) roomModel.getSelectedItem(), (Suspect) suspectModel.getSelectedItem());
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
