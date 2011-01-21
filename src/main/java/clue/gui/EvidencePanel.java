package clue.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clue.ClueEngine;
import clue.event.GameEvent;
import clue.event.GameEventAdapter;
import clue.model.Accusation;

public class EvidencePanel extends JPanel {

  private static final long serialVersionUID = 1L;
  private final ClueFrame clueFrame;
  private final Box box;

  public EvidencePanel(ClueFrame clueFrame) {
    this.clueFrame = clueFrame;
    this.box = Box.createHorizontalBox();
    initGui();
    initListeners();
  }

  private void initGui() {
    this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    box.add(new JLabel("Evidence"));
    add(box);
  }

  private void initListeners() {
    ClueEngine.get().addGameListener(new GameEventAdapter() {
      @Override
      public void startGame(GameEvent e) {
      }

      @Override
      public void makeSuspcision(Accusation accusation) {
      }
    });
  }
}
