package clue.model;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class Accusation {

  private final Suspect suspect;
  private final Room room;
  private final Weapon weapon;

  public Accusation(Suspect suspect, Room room, Weapon weapon) {
    this.suspect = suspect;
    this.room = room;
    this.weapon = weapon;
  }

  @Override
  public boolean equals(Object other) {
    return Pojomatic.equals(this, other);
  }

  public Room getRoom() {
    return room;
  }

  public Suspect getSuspect() {
    return suspect;
  }

  public Weapon getWeapon() {
    return weapon;
  }

  @Override
  public int hashCode() {
    return Pojomatic.hashCode(this);
  }

  @Override
  public String toString() {
    return Pojomatic.toString(this);
  }

}
