package eu.calabacin.kata.rockscissorspaper.controller;

import java.util.Optional;

public abstract class GameController {
	protected static String NAME;

	public abstract void play() throws Throwable;

	public static String getName() {
		return NAME;
	}

	public static Optional<GameControllerType> getByText(String string) {
		if (string == null || string.length() == 0)
			return Optional.empty();
		if (string.equalsIgnoreCase(TextGameController.getName())
				|| string.equalsIgnoreCase(GameControllerType.TEXT.getText()))
			return Optional.of(GameControllerType.TEXT);
		if (string.equalsIgnoreCase(GraphicGameController.getName())
				|| string.equalsIgnoreCase(GameControllerType.GRAPHIC.getText()))
			return Optional.of(GameControllerType.GRAPHIC);
		return Optional.empty();
	}

}
