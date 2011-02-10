package clue.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import clue.model.Card;
import clue.model.Suspect;

public class CardPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  private final Card card;
  private final JLabel face;
  private final JLabel back;

  public CardPanel(Card card) {
    this.card = card;
    this.face = new JLabel(card.name());
    if (card instanceof Suspect) {
      this.face.setBackground(SuspectColor.getColor((Suspect) card));
    }

    this.back = new JLabel("??");
    this.add(back);
  }

  public void hideCard() {
    CardPanel.this.remove(face);
    CardPanel.this.add(back);
    this.revalidate();
  }

  public boolean isCard(Card card) {
    return this.card.equals(card);
  }

  public void showCard() {
    CardPanel.this.remove(back);
    CardPanel.this.add(face);
    this.revalidate();
  }
}
