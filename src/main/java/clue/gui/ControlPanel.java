package clue.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import clue.ClueEngine;
import clue.gui.model.SuspectComboBoxModel;
import clue.model.Suspect;
import clue.util.Prefs;

public class ControlPanel extends JPanel {

  private static final long serialVersionUID = 20110117L;

  private final JSpinner spinner;
  private final JButton startButton;
  private final JComboBox players;
  public ControlPanel() {
    this.startButton = new JButton();
    this.players = new JComboBox(new SuspectComboBoxModel(Suspect.values()));
    this.players.setSelectedIndex(Prefs.userNode(this.getClass()).getInt(
        "currentPlayerIndex", 0));
    int value = Prefs.userNode(ControlPanel.class).getInt("players", 1);
    this.spinner = new JSpinner(new SpinnerNumberModel(value, 1,
        Suspect.values().length - 1, 1));

    initGui();
    initListeners();
  }

  private void initGui() {
    add(new JLabel("Players"));
    add(spinner);
    add(new JLabel("An you are?"));
    add(players);
    startButton.setText("Start");
    add(startButton);
  }

  private void initListeners() {
    startButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        ClueEngine.get().startGame((Integer) spinner.getValue(),
            (Suspect) players.getSelectedItem());
        enableControls(false);
      }
    });
    spinner.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {
        Prefs.userNode(ControlPanel.class).putInt("players",
            (Integer) spinner.getValue());
      }
    });
    players.addItemListener(new ItemListener() {

      @Override
      public void itemStateChanged(ItemEvent e) {
        if (ItemEvent.SELECTED == e.getStateChange()) {
          JComboBox box = (JComboBox) e.getSource();
          Prefs.userNode(ControlPanel.class).putInt("currentPlayerIndex",
              box.getSelectedIndex());
        }
      }
    });
  }

  private void enableControls(boolean isEnabled) {
    spinner.setEnabled(isEnabled);
    startButton.setEnabled(isEnabled);
    players.setEnabled(isEnabled);
  }
}
