package clue;

import javax.swing.JFrame;

import clue.gui.ClueFrame;

public class Clue {

  private static final ClueEngine clueEngine;
  static {
    clueEngine = new ClueEngine();
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    ClueFrame clueFrame = new ClueFrame();
    clueFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    clueFrame.setVisible(true);
  }

  public static final ClueEngine getEngine() {
    return clueEngine;
  }
}
