package clue.gui;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clue.model.Card;
import clue.model.Room;
import clue.model.Suspect;
import clue.model.Weapon;
import clue.util.BoxUtil;

public class RoomPanel extends JPanel {
  private static final long serialVersionUID = 1L;

  private final Room room;
  private final List<Card> cards;
  private final Box box;
  private final JLabel spacer;
  private final Set<Suspect> suspects;
  private Suspect currentSuspect;
  private final Set<Weapon> weapons;
  private Weapon currentWeapon;

  public RoomPanel(Room room) {
    this.room = room;
    this.cards = new LinkedList<Card>();
    this.suspects = new HashSet<Suspect>();
    this.weapons = new HashSet<Weapon>();
    this.box = Box.createVerticalBox();
    this.spacer = new JLabel("----");
    this.setBorder(BorderFactory.createRaisedBevelBorder());
    add(box);
    redraw();
  }

  public void addCards(List<Card> cards) {
    this.cards.clear();
    this.cards.addAll(cards);
    redraw();
  }

  public boolean isRoom(Room room) {
    return this.room.equals(room);
  }

  public void addSuspect(Suspect suspect) {
    suspects.add(suspect);
    currentSuspect = suspect;
    redraw();

  }

  public void addWeapon(Weapon weapon) {
    weapons.add(weapon);
    currentWeapon = weapon;
    redraw();
  }

  private void redraw() {
    box.removeAll();
    box.add(new JLabel(room.name()));
    if (!cards.isEmpty()) {
      box.add(spacer);
    }

    for (Card card : cards) {
      CardPanel cardPanel = new CardPanel(card);
      cardPanel.hideCard();
      box.add(cardPanel);
    }
    if (!suspects.isEmpty()) {
      box.add(spacer);
    }
    if (currentSuspect != null) {
      box.add(BoxUtil.createHorizontalBox(new JLabel("*"), new SuspectPanel(currentSuspect)));
    }
    for (Suspect suspect : suspects) {
      if (suspect.equals(currentSuspect)) {
        continue;
      }
      SuspectPanel suspectPanel = new SuspectPanel(suspect);
      box.add(suspectPanel);
    }
    if (currentWeapon != null) {
      box.add(BoxUtil.createHorizontalBox(new JLabel("*"), new WeaponPanel(currentWeapon)));
    }
    for (Weapon weapon : weapons) {
      if (weapon.equals(currentWeapon)) {
        continue;
      }
      box.add(new WeaponPanel(weapon));
    }
    this.revalidate();
  }

  public void removeSuspect(Suspect suspect) {
    if (suspect.equals(currentSuspect)) {
      currentSuspect = null;
    }
    suspects.remove(suspect);
    redraw();
  }

  public void removeWeapon(Weapon weapon) {
    if (weapon.equals(currentWeapon)) {
      currentWeapon = null;
    }
    weapons.remove(weapon);
    redraw();

  }
}
