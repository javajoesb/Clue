package clue.gui;

import java.awt.Color;

import clue.model.Suspect;

public class SuspectColor {

  public static final Color getColor(Suspect suspect) {
    if (Suspect.Mustard.equals(suspect)) {
      return Color.yellow;
    }
    if (Suspect.Plumb.equals(suspect)) {
      return new Color(12, 00, 24);
    }
    if (Suspect.White.equals(suspect)) {
      return Color.WHITE;
    }
    if (Suspect.Scarlett.equals(suspect)) {
      return Color.RED;
    }
    if (Suspect.Green.equals(suspect)) {
      return Color.GREEN;
    }
    if (Suspect.Peacock.equals(suspect)) {
      return Color.BLUE;
    }
    if (Suspect.None.equals(suspect)) {
      return Color.BLACK;
    }
    throw new RuntimeException(String.format("Not shure what color you want for suspect: %s", suspect.name()));
  }

}
