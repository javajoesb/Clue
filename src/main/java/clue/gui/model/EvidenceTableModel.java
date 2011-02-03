package clue.gui.model;

import java.util.Stack;

import javax.swing.table.AbstractTableModel;

import clue.model.Evidence;
import clue.model.Suspicion;

public class EvidenceTableModel extends AbstractTableModel {

  private static final long serialVersionUID = 1L;
  private Stack<Suspicion> suspicionStack;
  private Stack<Evidence> evidenceStack;

  public EvidenceTableModel() {
    suspicionStack = new Stack<Suspicion>();
    evidenceStack = new Stack<Evidence>();
  }

  public void addSuspcision(Suspicion suspicion) {
    suspicionStack.push(suspicion);
    super.fireTableDataChanged();
  }

  public void addEvidence(Evidence evidence) {
    evidenceStack.push(evidence);
    super.fireTableDataChanged();
  }

  @Override
  public int getRowCount() {
    return Math.max(evidenceStack.size(), suspicionStack.size());
  }

  @Override
  public int getColumnCount() {
    return 5;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {

    Suspicion suspicion = null;
    try {
      suspicion = suspicionStack.get(rowIndex);
    } catch (ArrayIndexOutOfBoundsException e) {
      // ignore
    }
    switch (columnIndex) {
    case 0:
      return suspicion == null ? "" : suspicion.getPlayer().getName();
    case 1:
      return suspicion == null ? "" : suspicion.getAccusation() == null ? ""
          : suspicion.getAccusation().getSuspect();
    case 2:
      return suspicion == null ? "" : suspicion.getAccusation() == null ? ""
          : suspicion.getAccusation().getRoom();
    case 3:
      return suspicion == null ? "" : suspicion.getAccusation() == null ? ""
          : suspicion.getAccusation().getWeapon();
      // additional columns below
    }

    Evidence evidence = null;
    try {
      evidence = evidenceStack.get(rowIndex);
    } catch (ArrayIndexOutOfBoundsException ex) {
      // ignore
    }
    switch (columnIndex) {
    case 4:
      return evidence == null ? "" : evidence.whoShowed().getName();
    case 5:
      return evidence == null ? "" : evidence.whichCard().name();
    }
    return String.format("? %s,%s", rowIndex, columnIndex);
  }

  @Override
  public Class getColumnClass(int c) {
    return String.class;
  }
}
