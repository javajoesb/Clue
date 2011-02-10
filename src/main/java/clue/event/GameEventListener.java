package clue.event;

import clue.model.Card;
import clue.model.Evidence;
import clue.model.Room;
import clue.model.Suspect;
import clue.model.Suspicion;

public interface GameEventListener {

  public void addEvidence(Evidence evidence);

  public void error(GameError gameError);

  public void makeSuspcision(Suspicion suspicion);

  public void movePlayer(Room room, Suspect suspect);

  public boolean showCard(Card card);

  public void startGame(GameEvent e);

}
