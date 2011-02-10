package clue.gui.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import clue.model.Room;

public class RoomComboBoxModel implements ComboBoxModel {

  private Room selectedRoom;
  private List<Room> rooms;
  private List<ListDataListener> listeners;

  public RoomComboBoxModel(Room[] roomArray) {
    listeners = new LinkedList<ListDataListener>();
    rooms = new LinkedList<Room>();
    Arrays.sort(roomArray, new Comparator<Room>() {

      @Override
      public int compare(Room lhs, Room rhs) {
        return lhs.name().compareTo(rhs.name());
      }
    });
    for (final Room room : roomArray) {
      rooms.add(room);
    }
    if (!this.rooms.isEmpty()) {
      setSelectedItem(this.rooms.get(0));
    }
  }

  @Override
  public void addListDataListener(ListDataListener l) {
    listeners.add(l);
  }

  @Override
  public Object getElementAt(int index) {
    return rooms.get(index);
  }

  @Override
  public Object getSelectedItem() {
    return selectedRoom;
  }

  @Override
  public int getSize() {
    return rooms.size();
  }

  @Override
  public void removeListDataListener(ListDataListener l) {
    listeners.remove(l);
  }

  @Override
  public void setSelectedItem(Object o) {
    if (o instanceof Room) {
      this.selectedRoom = (Room) o;
    }
  }
}
