package clue.event;

import clue.model.Accusation;
import clue.model.Room;
import clue.model.Suspect;

public class GameEventAdapter implements GameEventListener {

  @Override
  public void startGame(GameEvent e) {
  }

  @Override
  public void makeSuspcision(Accusation accusation) {
  }

  @Override
  public void movePlayer(Room room, Suspect suspect) {
  }

  @Override
  public void error(GameError gameError) {
  }
}
