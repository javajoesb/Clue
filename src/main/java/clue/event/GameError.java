package clue.event;

public class GameError {

  private final String message;

  public GameError(String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }

}
