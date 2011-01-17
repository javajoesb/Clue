package clue.gui;

import java.awt.BorderLayout;

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
    //this.setLayout(new BorderLayout());
    this.getContentPane().add(new NorthPanel(this), BorderLayout.SOUTH);
  }
}