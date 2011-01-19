package clue.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import clue.model.Weapon;

public class WeaponPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  private final Weapon weapon;

  public WeaponPanel(Weapon weapon) {
    this.weapon = weapon;
    this.add(new JLabel(this.weapon.name()));
  }
}
