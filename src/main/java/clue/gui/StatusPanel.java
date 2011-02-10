package clue.gui;

import static clue.ClueEngine.addGameListener;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import clue.event.GameEvent;
import clue.event.GameEventAdapter;

public class StatusPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  // private final ClueFrame parent;
  private final JLabel label;
  private final ClueFrame parent;

  public StatusPanel(ClueFrame parent) {
    this.parent = parent;
    this.label = new JLabel();
    initGui();
    initListeners();
  }

  private void initGui() {
    final Box box = Box.createVerticalBox();
    // this.parent = parent;
    this.label.setPreferredSize(new Dimension(parent.getWidth() - 10, 20));
    this.label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    box.add(label);
    add(box);
  }

  private void initListeners() {
    addGameListener(new GameEventAdapter() {
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
