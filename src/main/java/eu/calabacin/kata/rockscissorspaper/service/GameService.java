package eu.calabacin.kata.rockscissorspaper.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import eu.calabacin.kata.rockscissorspaper.entities.GameType;
import eu.calabacin.kata.rockscissorspaper.entities.Shape;
import eu.calabacin.kata.rockscissorspaper.entities.Winner;

@Service
public class GameService {
	private static final Random random = new Random();

	private static GameType DEFAULT_GAME_TYPE = GameType.STANDARD;
	private static GameService INSTANCE = null;

	/*
	 * For use outside of Spring as a library
	 */
	public static GameService getInstance() {
		if (INSTANCE == null) {
			synchronized (random) {
				if (INSTANCE == null) {
					INSTANCE = new GameService();
				}
			}
		}
		return INSTANCE;
	}

	public static void setDefaultGameType(GameType gameType) {
		DEFAULT_GAME_TYPE = gameType;
	}

	/*
	 * This method of the service changed when moving to legendary.
	 * 
	 * It used to be in the Shape Enum, but I didn't like logic outside of business
	 * logic objects
	 */
	public Winner findWinner(Shape shapePlayer1, Shape shapePlayer2) {
		if (shapePlayer1 == Shape.ROCK) {
			if (shapePlayer2 == Shape.ROCK)
				return Winner.TIE;
			return shapePlayer2 == Shape.SCISSORS || shapePlayer2 == Shape.LIZARD ? Winner.PLAYER1 : Winner.PLAYER2;
		}
		if (shapePlayer1 == Shape.SCISSORS) {
			if (shapePlayer2 == Shape.SCISSORS)
				return Winner.TIE;
			return shapePlayer2 == Shape.PAPER || shapePlayer2 == Shape.LIZARD ? Winner.PLAYER1 : Winner.PLAYER2;
		}
		if (shapePlayer1 == Shape.PAPER) {
			if (shapePlayer2 == Shape.PAPER)
				return Winner.TIE;
			return shapePlayer2 == Shape.ROCK || shapePlayer2 == Shape.SPOCK ? Winner.PLAYER1 : Winner.PLAYER2;
		}
		if (shapePlayer1 == Shape.LIZARD) {
			if (shapePlayer2 == Shape.LIZARD)
				return Winner.TIE;
			return shapePlayer2 == Shape.SPOCK || shapePlayer2 == Shape.PAPER ? Winner.PLAYER1 : Winner.PLAYER2;
		}
		if (shapePlayer1 == Shape.SPOCK) {
			if (shapePlayer2 == Shape.SPOCK)
				return Winner.TIE;
			return shapePlayer2 == Shape.SCISSORS || shapePlayer2 == Shape.ROCK ? Winner.PLAYER1 : Winner.PLAYER2;
		}
		return Winner.TIE;
	}

	public Optional<Shape> findWinnerShape(Shape shape1, Shape shape2) {
		Winner result = findWinner(shape1, shape2);
		if (result == Winner.TIE)
			return Optional.empty();
		return result == Winner.PLAYER1 ? Optional.of(shape1) : Optional.of(shape2);
	}

	public Shape computerPlayerChooseShape() {
		return computerPlayerChooseShape(DEFAULT_GAME_TYPE);
	}

	/*
	 * This method of the service was added when moving to legendary.
	 * 
	 * At first I only had computerPlayerChooseShape() with the random number code
	 */
	public Shape computerPlayerChooseShape(GameType gameType) {
		int nMax = gameType == GameType.STANDARD ? 3 : 5;
		return Shape.fromOrdinal(1 + random.nextInt(nMax));
	}
}
