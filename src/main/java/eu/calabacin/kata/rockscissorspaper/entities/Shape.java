package eu.calabacin.kata.rockscissorspaper.entities;

public enum Shape {
	ROCK, SCISSORS, PAPER, LIZARD, SPOCK;

	public static Shape fromOrdinal(int ordinal) {
		switch (ordinal) {
		case 1:
			return Shape.ROCK;
		case 2:
			return Shape.SCISSORS;
		case 3:
			return Shape.PAPER;
		case 4:
			return Shape.LIZARD;
		case 5:
			return Shape.SPOCK;
		default:
			throw new IllegalArgumentException("Invalid shape ordinal: " + ordinal);
		}
	}
}
