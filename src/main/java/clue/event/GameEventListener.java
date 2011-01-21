package clue.event;

import clue.model.Accusation;
import clue.model.Room;
import clue.model.Suspect;

public interface GameEventListener {

  public void startGame(GameEvent e);

  public void makeSuspcision(Accusation accusation);
  
  public void movePlayer(Room room, Suspect suspect);

  public void error(GameError gameError);
}
