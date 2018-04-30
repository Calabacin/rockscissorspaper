package eu.calabacin.kata.rockscissorspaper.controller;

public enum GameControllerType {
	TEXT(TextGameController.class), GRAPHIC(GraphicGameController.class), SERVER(RestGameController.class);

	private Class<? extends GameController> gameControllerClass;

	public String getText() {
		return gameControllerClass.getName();
	}

	GameControllerType(Class<? extends GameController> controllerClass) {
		this.gameControllerClass = controllerClass;
	}

	public Class<? extends GameController> getGameControllerClass() {
		return gameControllerClass;
	}

}
