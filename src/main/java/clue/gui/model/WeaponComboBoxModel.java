package clue.gui.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import clue.model.Weapon;

public class WeaponComboBoxModel implements ComboBoxModel {
  private Weapon selectedWeapon;
  private List<Weapon> weapons;
  private List<ListDataListener> listeners;

  public WeaponComboBoxModel(Weapon[] weaponArray) {
    listeners = new LinkedList<ListDataListener>();
    weapons = new LinkedList<Weapon>();
    Arrays.sort(weaponArray, new Comparator<Weapon>() {

      @Override
      public int compare(Weapon lhs, Weapon rhs) {
        return lhs.name().compareTo(rhs.name());
      }
    });
    for (Weapon weapon : weaponArray) {
      if (!Weapon.None.equals(weapon)) {
        weapons.add(weapon);
      }

    }
    if (!this.weapons.isEmpty()) {
      setSelectedItem(this.weapons.get(0));
    }
  }

  @Override
  public int getSize() {
    return weapons.size();
  }

  @Override
  public Object getElementAt(int index) {
    return weapons.get(index);
  }

  @Override
  public void addListDataListener(ListDataListener l) {
    listeners.add(l);
  }

  @Override
  public void removeListDataListener(ListDataListener l) {
    listeners.remove(l);
  }

  @Override
  public void setSelectedItem(Object o) {
    if (o instanceof Weapon) {
      this.selectedWeapon = (Weapon) o;
    }
  }

  @Override
  public Object getSelectedItem() {
    return selectedWeapon;
  }

}
