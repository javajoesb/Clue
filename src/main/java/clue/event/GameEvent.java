package clue.event;

public class GameEvent {

  private final GameState gameState;

  public GameEvent(GameState gameState) {
    this.gameState = gameState;
  }

  public GameState getGameState() {
    return gameState;
  }
}
