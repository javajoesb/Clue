package clue.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import clue.Clue;

public class NorthPanel extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 20110117L;

  private final ClueFrame parent;

  private JSpinner spinner;
  private JButton startButton;

  public NorthPanel(ClueFrame parent) {
    this.parent = parent;
    startButton = new JButton();
    spinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
    initGui();
    initListeners();
  }

  private void initGui() {
    add(new JLabel("Players"));
    add(spinner);
    startButton.setText("Start");
    add(startButton);
  }

  private void initListeners() {
    startButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        Clue.getEngine().startGame((Integer)spinner.getValue());
      }
    });
  }
}
