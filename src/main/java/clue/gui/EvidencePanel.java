package clue.gui;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import clue.ClueEngine;
import clue.event.GameEventAdapter;
import clue.gui.model.EvidenceColumnModel;
import clue.gui.model.EvidenceTableModel;
import clue.model.Accusation;
import clue.model.Player;

public class EvidencePanel extends JPanel {

  private static final long serialVersionUID = 1L;
  private final ClueFrame clueFrame;
  private final Box box;
  private final EvidenceTableModel tableModel;
  private final JTable table;
  public EvidencePanel(ClueFrame clueFrame) {
    this.clueFrame = clueFrame;
    this.box = Box.createVerticalBox();
    this.table = new JTable(tableModel = new EvidenceTableModel());
    initGui();
    initListeners();
  }

  private void initGui() {
    
    box.add(new JLabel("Evidence"));
    box.add(new JScrollPane(table));
    add(box);
  }

  private void initListeners() {
    ClueEngine.get().addGameListener(new GameEventAdapter() {
      @Override
      public void makeSuspcision(Player player, Accusation accusation) {
        tableModel.addSuspcision(player, accusation);
      }
    });
  }
}
