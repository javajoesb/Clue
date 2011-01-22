package clue.gui.model;

import java.util.Stack;

import javax.swing.table.AbstractTableModel;

import clue.model.Accusation;
import clue.model.Evidence;
import clue.model.Player;

public class EvidenceTableModel extends AbstractTableModel {

  private static final long serialVersionUID = 1L;
  private Stack<Evidence> stack;

  public EvidenceTableModel() {
    stack = new Stack<Evidence>();
  }

  public void addSuspcision(Player player, Accusation accusation) {
    stack.push(new Evidence(player, accusation));
    super.fireTableDataChanged();
  }

  @Override
  public int getRowCount() {
    return stack.size();
  }

  @Override
  public int getColumnCount() {
    return 4;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    Evidence evidence = stack.get(rowIndex);
    if (evidence == null) {
      return String.format("Uknown evidence at row %s", rowIndex);
    }
    switch (columnIndex) {
    case 0:
      return evidence.getPlayer().getName();
    case 1:
      return evidence.getAccusation().getRoom().name();
    case 2:
      return evidence.getAccusation().getSuspect().name();
    case 3:
      return evidence.getAccusation().getWeapon().name();
    default:
      return String.format("Unknown column %s", columnIndex);
    }
  }

  @Override
  public Class getColumnClass(int c) {
    return String.class;
  }
}
