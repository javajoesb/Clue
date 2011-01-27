package clue.gui;

import javax.swing.Box;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {

  private static final long serialVersionUID = 1L;

  public InfoPanel(ClueFrame parent) {
    Box box = Box.createVerticalBox();
    box.add(new SuspicionPanel(parent));
    this.add(box);
  }
}
