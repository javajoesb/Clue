package clue.gui.model;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class EvidenceTableColumn extends TableColumn {

  private static final long serialVersionUID = 1L;

  public EvidenceTableColumn(int columnIndex) {
    super.setCellRenderer(new TableCellRenderer() {

      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        System.out.println(value);
        return new JLabel("?");
      }
    });
  }
}
