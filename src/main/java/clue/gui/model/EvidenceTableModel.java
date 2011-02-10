package clue.gui.model;

import java.util.HashMap;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import clue.model.Evidence;
import clue.model.Suspicion;

public class EvidenceTableModel extends AbstractTableModel {

  private static final long serialVersionUID = 1L;
  private static final Logger logger = Logger.getLogger(EvidenceTableModel.class);
  private Map<Integer, Suspicion> suspicionStack;
  private Map<Integer, Evidence> evidenceStack;
  private int row;

  public EvidenceTableModel() {
    row = -1;
    suspicionStack = new HashMap<Integer, Suspicion>();
    evidenceStack = new HashMap<Integer, Evidence>();
  }

  public void addSuspcision(Suspicion suspicion) {
    row++;
    if (logger.isDebugEnabled()) {
      logger.debug(String.format("Adding suspicion %s: %s", row, ToStringBuilder.reflectionToString(suspicion)));
    }
    suspicionStack.put(row, suspicion);
    super.fireTableDataChanged();
  }

  public void addEvidence(Evidence evidence) {
    if (logger.isDebugEnabled()) {
      logger.debug(String.format("Adding evidence %s: %s", row, ToStringBuilder.reflectionToString(evidence)));
    }
    evidenceStack.put(row, evidence);
    super.fireTableDataChanged();
  }

  @Override
  public int getRowCount() {
    return row + 1;
  }

  @Override
  public int getColumnCount() {
    return 6;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {

    if (logger.isDebugEnabled()) {
      logger.debug(String.format("GetValue (%s,%s)", rowIndex, columnIndex));
    }
    switch (columnIndex) {
    case 0:
    case 1:
    case 2:
    case 3:
      return getSuspicionValue(rowIndex, columnIndex);
    case 4:
      return getEvidenceValue(rowIndex, columnIndex);
    }
    return String.format("? %s,%s", rowIndex, columnIndex);
  }

  private String getEvidenceValue(int rowIndex, int columnIndex) {
    Evidence evidence = evidenceStack.get(rowIndex);
    switch (columnIndex) {
    case 4:
      return evidence == null ? "" : evidence.whoShowed().getName();
    case 5:
      return evidence == null ? "" : evidence.whichCard().name();
    }
    return "";
  }

  private String getSuspicionValue(int rowIndex, int columnIndex) {
    Suspicion suspicion = suspicionStack.get(rowIndex);
    switch (columnIndex) {
    case 0:
      return suspicion == null ? "" : suspicion.getPlayer().getName();
    case 1:
      return suspicion == null ? "" : suspicion.getAccusation() == null ? "" : suspicion.getAccusation().getSuspect() == null ? "" : suspicion.getAccusation()
          .getSuspect().name();
    case 2:
      return suspicion == null ? "" : suspicion.getAccusation() == null ? "" : suspicion.getAccusation().getRoom() == null ? "" : suspicion.getAccusation()
          .getRoom().name();
    case 3:
      return suspicion == null ? "" : suspicion.getAccusation() == null ? "" : suspicion.getAccusation().getWeapon() == null ? "" : suspicion.getAccusation()
          .getWeapon().name();
    }
    return "";
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public Class getColumnClass(int c) {
    return String.class;
  }
}
