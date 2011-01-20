package clue.gui.model;

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
    selectedRoom = null;
    listeners = new LinkedList<ListDataListener>();
    rooms = new LinkedList<Room>();
    for (Room room : roomArray) {
      if (!room.equals(Room.Cellar)) {
        rooms.add(room);
      }
    }
    if (roomArray.length > 0) {
      selectedRoom = roomArray[0];
    }
  }

  @Override
  public int getSize() {
    return rooms.size();
  }

  @Override
  public Object getElementAt(int index) {
    return rooms.get(index);
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
    if (o instanceof Room) {
      this.selectedRoom = (Room) o;
    }
  }

  @Override
  public Object getSelectedItem() {
    return selectedRoom;
  }

}
