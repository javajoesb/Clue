package clue.gui.model;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.TableCellRenderer;

public class EvidenceTableCellRenderer extends JLabel implements TableCellRenderer {
  private static final long serialVersionUID = 20110209L;

  public EvidenceTableCellRenderer() {
    this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
  }

  private void apply(String text, String toolTip) {
    setText(text);
    setToolTipText(toolTip);
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    // rowIndex is always -1
    // isSelected is always false
    // hasFocus is always false

    switch (column) {
    case 0:
      apply("Player", "Player who made the rumor");
      return this;
    case 1:
      apply("Suspect", "Suspect of the rumor");
      return this;
    case 2:
      apply("Room", "Room of the rumor");
      return this;
    case 3:
      apply("Weapon", "Weapon of the rumor");
      return this;
    case 4:
      apply("Who", "Which player showed the card");
      return this;
    case 5:
      apply("What", "What card was shown");
      return this;
    default:
      apply(String.format("%s", column), "What do do?");
      return this;
    }
  }

}
