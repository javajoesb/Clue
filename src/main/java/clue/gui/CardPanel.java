package clue.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import clue.model.Card;

public class CardPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  private final Card card;

  public CardPanel(Card card) {
    this.card = card;
    this.add(new JLabel(card.name()));
  }
}
