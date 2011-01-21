package clue.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import clue.ClueEngine;
import clue.event.GameEventAdapter;
import clue.gui.model.EvidenceColumnModel;
import clue.gui.model.EvidenceTableModel;
import clue.model.Accusation;

public class EvidencePanel extends JPanel {

  private static final long serialVersionUID = 1L;
  private final ClueFrame clueFrame;
  private final Box box;
  private final EvidenceTableModel tableModel;
  private final JTable table;
  private final EvidenceColumnModel tableColumnModel;

  public EvidencePanel(ClueFrame clueFrame) {
    this.clueFrame = clueFrame;
    this.box = Box.createHorizontalBox();
    this.table = new JTable(tableModel = new EvidenceTableModel(), tableColumnModel = new EvidenceColumnModel());
    initGui();
    initListeners();
  }

  private void initGui() {
    this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    box.add(new JLabel("Evidence"));
    add(new JScrollPane(table));
  }

  private void initListeners() {
    ClueEngine.get().addGameListener(new GameEventAdapter() {
      @Override
      public void makeSuspcision(Accusation accusation) {
        tableModel.addSuspcision(accusation);
      }
    });
  }
}
