package clue.gui;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import clue.model.Accusation;
import clue.model.Card;
import clue.model.Player;

public class PlayerPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  private final Player player;
  private final Box box;

  public PlayerPanel(Player player) {
    this.player = player;
    this.box = Box.createVerticalBox();
    initGui();
    initListeners();
  }

  private void initGui() {
    add(box);

    this.setBorder(BorderFactory.createEtchedBorder());
    JLabel jLabel = new JLabel(player.getName());
    jLabel.setBackground(SuspectColor.getColor(player.asSuspect()));
    box.add(jLabel);
    box.add(new JLabel("----"));
    for (Card card : player.getCards()) {
      CardPanel cardPanel = new CardPanel(card);
      cardPanel.hideCard();
      box.add(cardPanel);
    }
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

  public void showCard(Accusation accusation) {
    if (player.hasCardFor(accusation)) {
      Card card = player.chooseCardFor(accusation);
      final CardPanel cardPanel = findCardPanel(card);
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

  private CardPanel findCardPanel(Card card) {
    for (Component component : box.getComponents()) {
      if (component instanceof CardPanel) {
        CardPanel cardPanel = (CardPanel) component;
        if (cardPanel.isCard(card)) {
          return cardPanel;
        }
      }
    }
    throw new RuntimeException(String.format("Dear developer, sI can't find card %s in my box", card));
  }

  public void showCards() {
    for (Component component : box.getComponents()) {
      if (component instanceof CardPanel) {
        CardPanel cardPanel = (CardPanel) component;
        cardPanel.showCard();
      }
    }
  }

  public void hideCards() {
    if (!this.player.isCurrentPlayer()) {
      for (Component component : box.getComponents()) {
        if (component instanceof CardPanel) {
          CardPanel cardPanel = (CardPanel) component;
          cardPanel.hideCard();
        }
      }
    }
  }
}
