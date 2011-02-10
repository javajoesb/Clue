package clue.gui;

import static clue.ClueEngine.currentPlayer;
import static clue.ClueEngine.enterRoom;
import static clue.ClueEngine.isAccusationReal;
import static clue.ClueEngine.makeSuspicion;
import static clue.util.BoxUtil.createHorizontalBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

  @Override
  public void enable(boolean isEnabled) {
    suspects.setEnabled(isEnabled);
    weapons.setEnabled(isEnabled);
    rooms.setEnabled(isEnabled);
    createRumor.setEnabled(isEnabled);
    createAccusation.setEnabled(isEnabled);
    enterRoom.setEnabled(isEnabled);
  }

  private Accusation generateCurrentAccusation() {
    return new Accusation((Suspect) suspects.getSelectedItem(), (Room) rooms.getSelectedItem(), (Weapon) weapons.getSelectedItem());
  }

  private void initGui() {
    final Box box = Box.createVerticalBox();
    box.add(createHorizontalBox(new JLabel("Room"), rooms));
    box.add(createHorizontalBox(new JLabel("Suspect"), suspects));
    box.add(createHorizontalBox(new JLabel("Weapon"), weapons));
    box.add(createHorizontalBox(createRumor, createAccusation));
    final JPanel p = new JPanel();
    p.add(enterRoom);
    box.add(p);
    add(box);
  }

  private void initListeners() {
    createRumor.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        final Player currentPlayer = currentPlayer();
        final Accusation accusation = generateCurrentAccusation();
        makeSuspicion(currentPlayer, accusation);
      }
    });
    enterRoom.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        enterRoom((Room) rooms.getSelectedItem(), (Suspect) suspects.getSelectedItem());
      }
    });
    createAccusation.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        final Accusation accusation = generateCurrentAccusation();
        if (isAccusationReal(accusation)) {
          JOptionPane.showMessageDialog(SuspicionPanel.this,
              String.format("Yes, %s with the %s in the %s", accusation.getSuspect(), accusation.getWeapon(), accusation.getRoom()));
        } else {
          JOptionPane.showMessageDialog(SuspicionPanel.this, "Nope, You loose");
        }
      }
    });
    ClueEngine.addGameListener(new GameEventAdapter() {
      @Override
      public void startGame(GameEvent e) {
        enable(true);
      }
    });
  }
}
