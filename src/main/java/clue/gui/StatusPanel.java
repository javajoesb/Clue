package clue.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import clue.ClueEngine;
import clue.event.GameEvent;
import clue.event.GameEventAdapter;

public class StatusPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  // private final ClueFrame parent;
  private final JLabel label;

  public StatusPanel(ClueFrame parent) {
    // this.parent = parent;
    this.label = new JLabel();
    this.label.setPreferredSize(new Dimension(parent.getWidth() - 10, 20));
    this.label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    this.add(label);
    ClueEngine.get().addGameListener(new GameEventAdapter() {
      @Override
      public void startGame(GameEvent e) {
        SwingUtilities.invokeLater(new Runnable() {

          @Override
          public void run() {
            StatusPanel.this.label.setText("Starting game ...");
          }
        });
      }
    });
  }
}
