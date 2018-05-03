package eu.calabacin.kata.rockscissorspaper.controller;

import java.io.IOException;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.calabacin.kata.rockscissorspaper.entities.GameType;
import eu.calabacin.kata.rockscissorspaper.entities.Shape;
import eu.calabacin.kata.rockscissorspaper.entities.Winner;
import eu.calabacin.kata.rockscissorspaper.service.GameService;

@Component
public class TextGameController extends GameController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TextGameController.class);

	private GameService gameService;

	public TextGameController(GameService contestService) {
		this.gameService = contestService;
	}

	@Override
	public void play() throws Throwable {
		play(new Scanner(System.in), System.out);
	}

	void play(Scanner input, PrintStream output) throws Throwable {
		try {
			while (true) {
				GameType gameType = readGameType(input, output);
				Integer numberOfPlayers = readNumberOfPlayers(input, output);
				Shape shapePlayer1 = readShape(input, output, gameType);
				Shape shapePlayer2;
				if (numberOfPlayers == 1) {
					shapePlayer2 = gameService.randomShape(gameType);
				} else {
					shapePlayer2 = readShape(input, output, gameType);
				}
				Winner winner = gameService.findWinner(shapePlayer1, shapePlayer2);
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

	GameType readGameType(Scanner input, PrintStream output) throws IOException, UserWantsToQuitException {
		while (true) {
			showOptions(output, "Choose a game type", GameType.values());
			int gameTypeInt = input.nextInt();
			if (gameTypeInt == 0)
				throw new UserWantsToQuitException();
			if (--gameTypeInt < GameType.values().length)
				return GameType.values()[gameTypeInt];
		}
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

	Integer readNumberOfPlayers(Scanner input, PrintStream output) throws IOException, UserWantsToQuitException {
		Integer numPlayers = null;
		while (numPlayers == null) {
			showNumberOfPlayers(output);
			int option = input.nextInt();
			if (option == 0)
				throw new UserWantsToQuitException();
			if (option > 0 && option <= 2)
				numPlayers = option;
		}
		return numPlayers;
	}

	private void showOptions(PrintStream output, String query, Object[] enums) {
		showOptions(output, query, enums, enums.length);
	}

	private void showOptions(PrintStream output, String query, Object[] enums, int maxOptions) {
		output.println("---------");
		output.println(query + ":");
		output.println();
		for (int i = 0; i < maxOptions; i++)
			output.printf("%d - %s\n", i + 1, enums[i]);
		output.println("0 (Zero) to quit.");
		output.println("---------");
	}

	Shape readShape(Scanner input, PrintStream output, GameType gameType) throws UserWantsToQuitException {
		while (true) {
			try {
				showOptions(output, "Choose an option", Shape.values(), gameType.getNumShapes());
				int shapeInt = input.nextInt();
				if (shapeInt == 0)
					throw new UserWantsToQuitException();
				if (--shapeInt < Shape.values().length)
					return Shape.values()[shapeInt];
			} catch (InputMismatchException e) {
			}
		}
	}

	void quit(Scanner input, PrintStream output) {
		output.println("Thank you for playing!");
		LOGGER.info("Exiting");
		try {
			input.close();
		} catch (Exception ignored) {
			LOGGER.error("Exception closing input", ignored);
		}
		try {
			output.close();
		} catch (Exception ignored) {
			LOGGER.error("Exception closing output", ignored);
		}
	}

}
