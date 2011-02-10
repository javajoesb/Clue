package clue.gui.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import clue.model.Suspect;

public class SuspectComboBoxModel implements ComboBoxModel {

  private final List<Suspect> suspects;
  private final List<ListDataListener> listners;
  private Suspect selected;

  public SuspectComboBoxModel(Suspect[] suspectArray) {
    this.listners = new LinkedList<ListDataListener>();
    this.suspects = new LinkedList<Suspect>();
    Arrays.sort(suspectArray, new Comparator<Suspect>() {

      @Override
      public int compare(Suspect lhs, Suspect rhs) {
        return lhs.name().compareTo(rhs.name());
      }
    });
    for (Suspect suspect : suspectArray) {
      this.suspects.add(suspect);
    }
    if (!this.suspects.isEmpty()) {
      setSelectedItem(this.suspects.get(0));
    }
  }

  @Override
  public int getSize() {
    return suspects.size();
  }

  @Override
  public Object getElementAt(int index) {
    return suspects.get(index);
  }

  @Override
  public void addListDataListener(ListDataListener l) {
    listners.add(l);
  }

  @Override
  public void removeListDataListener(ListDataListener l) {
    listners.remove(l);

  }

  @Override
  public void setSelectedItem(Object anItem) {
    if (anItem instanceof Suspect) {
      selected = (Suspect) anItem;
    } else {
      throw new ClassCastException(String.format("Expected %s, not %s", Suspect.class.getSimpleName(), anItem == null ? "?NULL" : anItem.getClass()
          .getCanonicalName()));
    }
  }

  @Override
  public Object getSelectedItem() {
    return selected;
  }

}
