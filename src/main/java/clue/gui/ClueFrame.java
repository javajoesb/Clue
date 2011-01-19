package clue.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import clue.ClueEngine;
import clue.event.ClueFrameComponentAdapter;

public class ClueFrame extends JFrame {

  private static final long serialVersionUID = 20110117L;

  public ClueFrame() {
    super("Clue Tool");
    initGui();
    new ClueFrameComponentAdapter(this);
  }

  private void initGui() {
    this.getContentPane().add(new NorthPanel(this), BorderLayout.NORTH);
    GamePanel gamePanel = new GamePanel(this);
    ClueEngine.get().addGameListener(gamePanel);
    this.add(gamePanel, BorderLayout.CENTER);
    StatusPanel statusPanel = new StatusPanel(this);
    ClueEngine.get().addGameListener(statusPanel);
    this.add(statusPanel, BorderLayout.SOUTH);
  }
}
