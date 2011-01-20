package clue.util;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JComponent;

public class BoxUtil {
  public static final Component createHorizontalBox(JComponent label, JComponent component) {
    Box box = Box.createHorizontalBox();
    box.add(label);
    box.add(component);
    return box;
  }
}
