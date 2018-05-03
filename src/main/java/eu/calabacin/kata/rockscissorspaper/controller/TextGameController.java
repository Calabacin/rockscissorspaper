package eu.calabacin.kata.rockscissorspaper.controller;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Optional;
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
				GameType gameType = readGameType(input, output).orElseThrow(UserWantsToQuitException::new);
				Integer numberOfPlayers = readNumberOfPlayers(input, output).orElseThrow(UserWantsToQuitException::new);
				Shape shapePlayer1 = readShape(input, output, gameType).orElseThrow(UserWantsToQuitException::new);
				Shape shapePlayer2;
				if (numberOfPlayers == 1) {
					shapePlayer2 = gameService.randomShape(gameType);
				} else {
					shapePlayer2 = readShape(input, output, gameType).orElseThrow(UserWantsToQuitException::new);
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

	private void showOptions(PrintStream output, String query, Object[] enums) {
		showOptions(output, query, enums, enums.length);
	}

	private void showOptions(PrintStream output, String query, Object[] enums, int maxOptions) {
		output.println("---------");
		output.println(query + ":");
		output.println();
		for (int i = 0; i < maxOptions; i++)
			output.printf("%d - %s\n", i + 1, enums[i]);
		output.println();
		output.println("0 (Zero) to quit.");
		output.println("---------");
	}

	Optional<GameType> readGameType(Scanner input, PrintStream output) {
		while (true) {
			try {
				showOptions(output, "Choose a game type", GameType.values());
				int gameTypeInt = input.nextInt();
				if (gameTypeInt == 0)
					return Optional.empty();
				if (--gameTypeInt < GameType.values().length)
					return Optional.of(GameType.values()[gameTypeInt]);
			} catch (InputMismatchException ignore) {
			}

		}
	}

	Optional<Integer> readNumberOfPlayers(Scanner input, PrintStream output) {
		while (true) {
			try {
				showOptions(output, "Enter number of players", new String[] { "Play vs Computer", "Play vs Human" });
				int numPlayers = input.nextInt();
				if (numPlayers == 0)
					return Optional.empty();
				if (numPlayers > 0 && numPlayers <= 2)
					return Optional.of(numPlayers);
			} catch (InputMismatchException ignore) {
			}

		}
	}

	Optional<Shape> readShape(Scanner input, PrintStream output, GameType gameType) {
		while (true) {
			try {
				showOptions(output, "Choose an option", Shape.values(), gameType.getNumShapes());
				int shapeInt = input.nextInt();
				if (shapeInt == 0)
					return Optional.empty();
				if (--shapeInt < Shape.values().length)
					return Optional.of(Shape.values()[shapeInt]);
			} catch (InputMismatchException ignore) {
			}
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
