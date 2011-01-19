package clue.gui;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clue.model.Card;
import clue.model.Player;
import clue.model.Suspect;

public class PlayerPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  private final Player player;
  private final List<CardPanel> cards;

  public PlayerPanel(Player player) {
    this.player = player;
    Box box = Box.createVerticalBox();
    add(box);
    this.setBorder(BorderFactory.createEtchedBorder());
    box.add(new JLabel(player.getName()));
    box.add(new JLabel("----"));
    cards = new LinkedList<CardPanel>();
    for (Card card : player.getCards()) {
      JLabel label = new JLabel(card.name());
      label.setBackground(getColor(card));
      box.add(label);
    }
  }

  private Color getColor(Card card) {
    if (card instanceof Suspect) {
      Suspect suspect = (Suspect) card;
      if (Suspect.Mustard.equals(card)) {
        return Color.yellow;
      }
      if (Suspect.Plumb.equals(card)) {
        return new Color(12, 00, 24);
      }
      if (Suspect.White.equals(card)) {
        return Color.WHITE;
      }
      if (Suspect.Scarlett.equals(card)) {
        return Color.RED;
      }
      if (Suspect.Green.equals(card)) {
        return Color.GREEN;
      }
      if (Suspect.Peacock.equals(card)) {
        return Color.BLUE;
      }
    }
    return Color.white;
  }
}
