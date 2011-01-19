package clue.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import clue.ClueEngine;
import clue.util.Prefs;

public class NorthPanel extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 20110117L;

  private JSpinner spinner;
  private JButton startButton;

  public NorthPanel(ClueFrame parent) {
    startButton = new JButton();
    int value = Prefs.userNode(NorthPanel.class).getInt("players", 1);

    spinner = new JSpinner(new SpinnerNumberModel(value, 1, 5, 1));
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
        ClueEngine.get().startGame((Integer) spinner.getValue());
      }
    });
    spinner.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {
        Prefs.userNode(NorthPanel.class).putInt("players", (Integer) spinner.getValue());
      }
    });
  }
}
