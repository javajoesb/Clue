package clue.gui;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clue.model.Accusation;
import clue.model.Card;
import clue.model.Room;

public class RoomPanel extends JPanel {
  private static final long serialVersionUID = 1L;

  private final Room room;
  private List<CardPanel> cardPanels;
  private SuspectPanel suspectPanelAccusation;
  private WeaponPanel weaponPanelAccousation;

  private Box box;

  private JLabel spacer;

  public RoomPanel(Room room) {
    this.room = room;
    this.cardPanels = Collections.emptyList();
    box = Box.createVerticalBox();
    box.add(new JLabel(room.name()));
    spacer = new JLabel("---");
    add(box);
    this.setBorder(BorderFactory.createRaisedBevelBorder());
  }

  public void addCards(List<Card> cards) {
    removeCards();
    this.cardPanels = new LinkedList<CardPanel>();
    if (!cards.isEmpty()) {
      box.add(spacer);
    }
    for (Card card : cards) {
      CardPanel cardPanel = new CardPanel(card);
      cardPanel.hideCard();
      cardPanels.add(cardPanel);
      box.add(cardPanel);
    }
    this.revalidate();
  }

  public void setAccusation(Accusation accusation) {
    if (accusation.getRoom().equals(room)) {
      suspectPanelAccusation = new SuspectPanel(accusation.getSuspect());
      add(suspectPanelAccusation);
      weaponPanelAccousation = new WeaponPanel(accusation.getWeapon());
      add(weaponPanelAccousation);
    }
  }

  private void removeCards() {
    box.remove(spacer);
    for (CardPanel cardPanel : cardPanels) {
      box.remove(cardPanel);
    }
    this.revalidate();
  }
}
