package clue;

import javax.swing.JFrame;

import clue.gui.ClueFrame;

public class Clue {

  /**
   * @param args
   */
  public static void main(String[] args) {
    ClueFrame clueFrame = new ClueFrame();
    clueFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    clueFrame.setVisible(true);
  }
}
