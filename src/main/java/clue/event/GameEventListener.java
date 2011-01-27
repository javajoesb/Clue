package clue.event;

import clue.model.Card;
import clue.model.Evidence;
import clue.model.Room;
import clue.model.Suspect;
import clue.model.Suspicion;

public interface GameEventListener {

  public void startGame(GameEvent e);

  public void makeSuspcision(Suspicion suspicion);

  public void addEvidence(Evidence evidence);

  public void movePlayer(Room room, Suspect suspect);

  public void error(GameError gameError);

  public boolean showCard(Card card);

}
