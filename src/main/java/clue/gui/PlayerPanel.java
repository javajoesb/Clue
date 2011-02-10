package clue.gui;

import static clue.ClueEngine.addGameListener;

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

import clue.event.GameEventAdapter;
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

  private CardPanel findCardPanel(Card card) {
    for (final Component component : box.getComponents()) {
      if (component instanceof CardPanel) {
        final CardPanel cardPanel = (CardPanel) component;
        if (cardPanel.isCard(card)) {
          return cardPanel;
        }
      }
    }
    return null;
  }

  public void hideCards() {
    if (!this.player.isCurrentPlayer()) {
      for (final Component component : box.getComponents()) {
        if (component instanceof CardPanel) {
          final CardPanel cardPanel = (CardPanel) component;
          cardPanel.hideCard();
        }
      }
    }
  }

  private void initGui() {
    add(box);

    this.setBorder(BorderFactory.createEtchedBorder());
    final JLabel jLabel = new JLabel(player.getName());
    jLabel.setBackground(SuspectColor.getColor(player.asSuspect()));
    box.add(jLabel);
    box.add(new JLabel("----"));
    for (final Card card : player.getCards()) {
      final CardPanel cardPanel = new CardPanel(card);
      cardPanel.hideCard();
      box.add(cardPanel);
    }
  }

  private void initListeners() {
    // we have to add this in the event queue as we are creating the panel in
    // that thread.
    SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {
        addGameListener(new GameEventAdapter() {
          @Override
          public boolean showCard(Card card) {
            return PlayerPanel.this.showCard(card);
          }
        });
      }
    });

    this.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 3) {
          showCards();
          final Timer timer = new Timer("Player-Card-Flipper", true);
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

  public boolean showCard(Card card) {
    final CardPanel cardPanel = findCardPanel(card);
    if (cardPanel != null) {
      cardPanel.showCard();
      final Timer timer = new Timer("Card Flipper Timer", true);
      timer.schedule(new TimerTask() {

        @Override
        public void run() {
          cardPanel.hideCard();
        }
      }, 3000);
      return true;
    }
    return false;
  }

  public void showCards() {
    for (final Component component : box.getComponents()) {
      if (component instanceof CardPanel) {
        final CardPanel cardPanel = (CardPanel) component;
        cardPanel.showCard();
      }
    }
  }
}
