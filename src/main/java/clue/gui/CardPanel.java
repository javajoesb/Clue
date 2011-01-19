package clue.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import clue.model.Card;

public class CardPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  private JLabel face;
  private JLabel back;

  public CardPanel(Card card) {
    face = new JLabel(card.name());
    back = new JLabel("??");
    this.add(back);
  }

  public void showCard() {
    SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {
        CardPanel.this.remove(back);
        CardPanel.this.add(face);
      }
    });
  }

  public void hideCard() {
    SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {
        CardPanel.this.remove(face);
        CardPanel.this.add(back);
      }
    });
  }
}
