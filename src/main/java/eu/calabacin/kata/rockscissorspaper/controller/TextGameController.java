package eu.calabacin.kata.rockscissorspaper.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.calabacin.kata.rockscissorspaper.entities.GameType;
import eu.calabacin.kata.rockscissorspaper.entities.Shape;
import eu.calabacin.kata.rockscissorspaper.entities.Winner;
import eu.calabacin.kata.rockscissorspaper.service.GameService;

@Component
public class TextGameController extends GameController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TextGameController.class);

	static {
		NAME = "text";
	}

	@Autowired
	private GameService gameService;

	public TextGameController(GameService contestService) {
		this.gameService = contestService;
	}

	@Override
	public void play() throws Throwable {
		play(new BufferedReader(new InputStreamReader(System.in)), System.out);
	}

	void play(BufferedReader input, PrintStream output) throws Throwable {
		Integer numberOfPlayers;
		Shape shapePlayer1;
		Shape shapePlayer2;
		Winner winner;
		try {
			while (true) {
				GameType gameType = readGameType(input, output);
				numberOfPlayers = readNumberOfPlayers(input, output);
				shapePlayer1 = readShape(input, output, gameType);
				if (numberOfPlayers == 1) {
					shapePlayer2 = gameService.computerPlayerChooseShape(gameType);
				} else {
					shapePlayer2 = readShape(input, output, gameType);
				}
				winner = gameService.findWinner(shapePlayer1, shapePlayer2);
				showResult(winner, shapePlayer1, shapePlayer2, output);
			}
		} catch (UserWantsToQuitException e) {
		} catch (Throwable e) {
			LOGGER.error("Error in execution. Program ending", e);
			throw e;
		} finally {
			quit(input, output);
		}
	}

	GameType readGameType(BufferedReader input, PrintStream output) throws IOException, UserWantsToQuitException {
		GameType gameType = null;
		while (gameType == null) {
			showGameTypeQuestion(output);
			String line = input.readLine();
			int shapeInt = findIntFromText(line, 2);
			if (shapeInt == 0)
				throw new UserWantsToQuitException();
			if (shapeInt == 1)
				return GameType.STANDARD;
			if (shapeInt == 2)
				return GameType.LEGENDARY;
		}
		throw new RuntimeException("This code should never be executed");
	}

	private void showGameTypeQuestion(PrintStream output) {
		output.println("---------");
		output.println("Choose a game type:");
		output.println();
		output.println("1 - Standard");
		output.println("2 - Legendary");
		output.println("0 (Zero) to quit.");
		output.println("---------");
	}

	private void showResult(Winner winner, Shape shapePlayer1, Shape shapePlayer2, PrintStream output) {
		output.println();
		output.println("-------------------");
		if (winner == Winner.TIE) {
			output.println("It's a tie!!!");
			output.println("Both players played " + shapePlayer1);
		} else {
			output.println(winner.name() + " has won!!!");
			if (winner == Winner.PLAYER1)
				output.println(shapePlayer1.name() + " defeats " + shapePlayer2);
			else
				output.println(shapePlayer2.name() + " defeats " + shapePlayer1);
		}
		output.println("-------------------");
	}

	private void showNumberOfPlayers(PrintStream output) {
		output.println("Enter number of players (1-2), or enter zero (0) to quit.");
	}

	Integer readNumberOfPlayers(BufferedReader input, PrintStream output) throws IOException, UserWantsToQuitException {
		Integer numPlayers = null;
		while (numPlayers == null) {
			showNumberOfPlayers(output);
			String line = input.readLine();
			numPlayers = findIntFromText(line, 2);
			if (numPlayers == 0)
				throw new UserWantsToQuitException();
		}
		return numPlayers;
	}

	private void showOptions(PrintStream output, GameType gameType) {
		output.println("---------");
		output.println("Choose an option:");
		output.println();
		output.println("1 - Rock");
		output.println("2 - Scissors");
		output.println("3 - Paper");
		if (gameType == GameType.LEGENDARY) {
			output.println("4 - Lizard");
			output.println("5 - Spock");
		}
		output.println("0 (Zero) to quit.");
		output.println("---------");
	}

	Shape readShape(BufferedReader input, PrintStream output, GameType gameType) throws IOException {
		Shape shape = null;
		while (shape == null) {
			try {
				showOptions(output, gameType);
				String line = input.readLine();
				int shapeInt = findIntFromText(line, gameType.getNumShapes());
				shape = Shape.fromOrdinal(shapeInt);
			} catch (NumberFormatException e) {
			}
		}
		return shape;
	}

	int findIntFromText(String line, int maxInt) throws NumberFormatException {
		int number = Integer.parseInt(line);
		if (number < 0 || number > maxInt)
			throw new NumberFormatException("Number must be between 1 and " + maxInt);
		return number;
	}

	void quit(BufferedReader input, PrintStream output) {
		LOGGER.info("Exiting");
		output.println("Thank you for playing!");
		try {
			input.close();
		} catch (Exception ignored) {
			LOGGER.info("Exception closing input");
		}
		try {
			output.close();
		} catch (Exception ignored) {
			LOGGER.info("Exception closing output");
		}
	}

}
