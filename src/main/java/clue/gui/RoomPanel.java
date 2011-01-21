package clue.gui;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
    initListeners();
  }

  private void initListeners() {
    this.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 3) {
          showCards();
          Timer timer = new Timer("Player-Card-Flipper", true);
          timer.schedule(new TimerTask() {

            @Override
            public void run() {
              SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                  hideCards();
                }

              });
            }
          }, 1000);
        }
      }

    });
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
  }

  public void removeSuspect(Suspect suspect) {
    clearSuspect();
    suspects.remove(suspect);
  }

  public void accuse(Suspect suspect) {
    currentSuspect = suspect;
  }

  public void clearSuspect() {
    currentSuspect = null;
  }

  public void addWeapon(Weapon weapon) {
    weapons.add(weapon);
  }

  public void removeWeapon(Weapon weapon) {
    clearWeapon();
    weapons.remove(weapon);
  }

  public void accuse(Weapon weapon) {
    currentWeapon = weapon;
  }

  public void clearWeapon() {
    currentWeapon = null;
  }

  public void redraw() {
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

  private void showCards() {
    for (Component component : box.getComponents()) {
      if (component instanceof CardPanel) {
        CardPanel cardPanel = (CardPanel) component;
        cardPanel.showCard();
      }
    }
  }

  private void hideCards() {
    for (Component component : box.getComponents()) {
      if (component instanceof CardPanel) {
        CardPanel cardPanel = (CardPanel) component;
        cardPanel.hideCard();
      }
    }
  }

}
