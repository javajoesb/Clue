package clue.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class NorthPanel extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 20110117L;

  private final ClueFrame parent;

  private JSpinner spinner;

  public NorthPanel(ClueFrame parent) {
    this.parent = parent;
    spinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
    initGui();
  }

  private void initGui() {
    add(new JLabel("Players"));
    add(spinner);
  }
}
