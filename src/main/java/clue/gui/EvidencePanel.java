package clue.gui;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import clue.ClueEngine;
import clue.event.GameEventAdapter;
import clue.gui.model.EvidenceTableModel;
import clue.model.Evidence;
import clue.model.Suspicion;

public class EvidencePanel extends JPanel {

  private static final long serialVersionUID = 1L;
  private final Box box;
  private final EvidenceTableModel tableModel;
  private final JTable table;

  public EvidencePanel() {
    this.box = Box.createVerticalBox();
    this.table = new JTable(tableModel = new EvidenceTableModel());
    initGui();
    initListeners();
  }

  private void initGui() {
    box.add(new JLabel("Evidence"));
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.getViewport().setSize(20, 20);
    box.add(scrollPane);
    add(box);
  }

  private void initListeners() {
    ClueEngine.get().addGameListener(new GameEventAdapter() {
      @Override
      public void makeSuspcision(Suspicion suspicion) {
        tableModel.addSuspcision(suspicion);
      }

      @Override
      public void addEvidence(Evidence evidence) {
        tableModel.addEvidence(evidence);
      }
    });
  }
}
