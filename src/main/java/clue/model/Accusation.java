package clue.model;

public class Accusation {

  private final Suspect suspect;
  private final Room room;
  private final Weapon weapon;

  public Accusation(Suspect suspect, Room room, Weapon weapon) {
    this.suspect = suspect;
    this.room = room;
    this.weapon = weapon;
  }

  public Suspect getSuspect() {
    return suspect;
  }

  public Room getRoom() {
    return room;
  }

  public Weapon getWeapon() {
    return weapon;
  }
}
