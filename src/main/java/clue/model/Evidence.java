package clue.model;

public class Evidence {

	public Accusation getAccusation() {
		return accusation;
	}

	public Player getPlayer() {
		return player;
	}

	private final Accusation accusation;
	private final Player player;

	public Evidence(Player player, Accusation accusation) {
		this.player = player;
		this.accusation = accusation;

	}
}
