package clue.gui.model;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import clue.model.Accusation;

public class EvidenceTableModel extends DefaultTableModel implements TableModel {

  private static final long serialVersionUID = 1L;

  //
  // @Override
  // public int getRowCount() {
  // // TODO Auto-generated method stub
  // return 0;
  // }
  //
  // @Override
  // public int getColumnCount() {
  // // TODO Auto-generated method stub
  // return 0;
  // }
  //
  // @Override
  // public String getColumnName(int columnIndex) {
  // // TODO Auto-generated method stub
  // return null;
  // }
  //
  // @Override
  // public Class<?> getColumnClass(int columnIndex) {
  // // TODO Auto-generated method stub
  // return null;
  // }
  //
  // @Override
  // public boolean isCellEditable(int rowIndex, int columnIndex) {
  // // TODO Auto-generated method stub
  // return false;
  // }
  //
  // @Override
  // public Object getValueAt(int rowIndex, int columnIndex) {
  // // TODO Auto-generated method stub
  // return null;
  // }
  //
  // @Override
  // public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
  // // TODO Auto-generated method stub
  //
  // }
  //
  // @Override
  // public void addTableModelListener(TableModelListener l) {
  // // TODO Auto-generated method stub
  //
  // }
  //
  // @Override
  // public void removeTableModelListener(TableModelListener l) {
  // // TODO Auto-generated method stub
  //
  // }

  public void addSuspcision(Accusation accusation) {
    dataVector.add(accusation);
    super.fireTableDataChanged();
  }

}
