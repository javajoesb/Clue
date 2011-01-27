package clue.gui;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JFrame;

import clue.event.ClueFrameComponentAdapter;

public class ClueFrame extends JFrame {

  private static final long serialVersionUID = 20110117L;

  public ClueFrame() {
    super("Clue Tool");
    initGui();
    new ClueFrameComponentAdapter(this);
  }

  private void initGui() {
    this.getContentPane().add(new ControlPanel(this), BorderLayout.NORTH);
    this.add(new Board(this), BorderLayout.CENTER);
    this.add(new StatusPanel(this), BorderLayout.SOUTH);
    this.add(new InfoPanel(this), BorderLayout.WEST);
  }
}
