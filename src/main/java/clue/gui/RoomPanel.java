package clue.gui;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clue.model.Card;
import clue.model.Room;

public class RoomPanel extends JPanel {
  private static final long serialVersionUID = 1L;

  private final Room room;
  private List<CardPanel> cardPanels;

  private Box box;

  public RoomPanel(Room room) {
    this.room = room;
    this.cardPanels = Collections.emptyList();
    box = Box.createVerticalBox();
    box.add(new JLabel(room.name()));
    box.add(new JLabel("---"));
    add(box);
    this.setBorder(BorderFactory.createRaisedBevelBorder());
  }

  public void addCards(List<Card> cards) {
    removeCards();
    this.cardPanels = new LinkedList<CardPanel>();
    for (Card card : cards) {
      CardPanel cardPanel = new CardPanel(card);
      cardPanels.add(cardPanel);
      box.add(cardPanel);
    }
    this.revalidate();
  }

  private void removeCards() {
    for (CardPanel cardPanel : cardPanels) {
      box.remove(cardPanel);
    }
    this.revalidate();
  }
}
