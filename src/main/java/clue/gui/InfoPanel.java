package clue.gui;

import javax.swing.Box;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {

  private static final long serialVersionUID = 1L;

  public InfoPanel() {
    final Box box = Box.createVerticalBox();
    box.add(new SuspicionPanel());
    this.add(box);
  }
}
