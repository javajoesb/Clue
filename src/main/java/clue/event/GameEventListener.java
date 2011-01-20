package clue.event;

import clue.model.Accusation;

public interface GameEventListener {

  public void startGame(GameEvent e);

  public void makeSuspcision(Accusation accusation);
}
