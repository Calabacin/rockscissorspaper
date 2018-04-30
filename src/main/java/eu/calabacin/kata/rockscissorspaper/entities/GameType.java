package eu.calabacin.kata.rockscissorspaper.entities;

public enum GameType {
	STANDARD(3), LEGENDARY(5);
	private int numShapes;

	private GameType(int numShapes) {
		this.numShapes = numShapes;
	}

	public int getNumShapes() {
		return numShapes;
	}
}
