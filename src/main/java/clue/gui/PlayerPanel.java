package clue.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clue.model.Accusation;
import clue.model.Card;
import clue.model.Player;
import clue.model.Suspect;

public class PlayerPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  private final Player player;
  private final Map<Card, CardPanel> cardPanels;

  public PlayerPanel(Player player) {
    this.player = player;
    Box box = Box.createVerticalBox();
    add(box);
    this.setBorder(BorderFactory.createEtchedBorder());
    box.add(new JLabel(player.getName()));
    box.add(new JLabel("----"));
    cardPanels = new HashMap<Card, CardPanel>();
    for (Card card : player.getCards()) {
      JLabel label = new JLabel(card.name());
      if (card instanceof Suspect) {
        label.setBackground(SuspectColor.getColor((Suspect) card));
      }
      box.add(label);
      CardPanel cardPanel = new CardPanel(card);
      cardPanel.hideCard();
      cardPanels.put(card, cardPanel);
    }
  }

  public void showCard(Accusation accusation) {
    if (player.hasCardFor(accusation)) {
      Card card = player.chooseCardFor(accusation);
      final CardPanel cardPanel = cardPanels.get(card);
      cardPanel.showCard();
      Timer timer = new Timer("Card Flipper Timer", true);
      timer.schedule(new TimerTask() {

        @Override
        public void run() {
          cardPanel.hideCard();
        }
      }, 3000);
    }
  }
}
