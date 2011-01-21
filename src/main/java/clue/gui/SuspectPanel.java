package clue.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import clue.model.Suspect;

public class SuspectPanel extends JPanel {
  private static final long serialVersionUID = 1L;
  private final Suspect suspect;

  public SuspectPanel(Suspect suspect) {
    this.suspect = suspect;
    add(new JLabel(this.suspect.name()));
    setBackground(SuspectColor.getColor(suspect));
    setForeground(SuspectColor.getColor(suspect));
  }
}
