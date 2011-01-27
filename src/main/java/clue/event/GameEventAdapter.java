package clue.event;

import clue.model.Card;
import clue.model.Evidence;
import clue.model.Player;
import clue.model.Room;
import clue.model.Suspect;
import clue.model.Suspicion;

public class GameEventAdapter implements GameEventListener {

  @Override
  public void startGame(GameEvent e) {
  }

  @Override
  public void makeSuspcision(Suspicion suspicion) {
  }

  @Override
  public void movePlayer(Room room, Suspect suspect) {
  }

  @Override
  public void error(GameError gameError) {
  }

  @Override
  public boolean showCard(Card card) {
    return false;
  }

  @Override
  public void addEvidence(Evidence evidence) {
  }
}
