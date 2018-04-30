package eu.calabacin.kata.rockscissorspaper.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eu.calabacin.kata.rockscissorspaper.entities.Shape;
import eu.calabacin.kata.rockscissorspaper.entities.Winner;

public class GameServiceTest {
	GameService rockScissorsPaperService;

	public GameServiceTest() {
		rockScissorsPaperService = new GameService();
	}

	@Test
	public void checkEqualShapesTie() {
		assertTrue(rockScissorsPaperService.findWinner(Shape.ROCK, Shape.ROCK) == Winner.TIE);
		assertTrue(rockScissorsPaperService.findWinner(Shape.PAPER, Shape.PAPER) == Winner.TIE);
		assertTrue(rockScissorsPaperService.findWinner(Shape.SCISSORS, Shape.SCISSORS) == Winner.TIE);
		assertTrue(rockScissorsPaperService.findWinner(Shape.LIZARD, Shape.LIZARD) == Winner.TIE);
		assertTrue(rockScissorsPaperService.findWinner(Shape.SPOCK, Shape.SPOCK) == Winner.TIE);
	}

	@Test
	public void checkRockWins() {
		assertTrue(rockScissorsPaperService.findWinner(Shape.ROCK, Shape.SCISSORS) == Winner.PLAYER1);
		assertTrue(rockScissorsPaperService.findWinner(Shape.SCISSORS, Shape.ROCK) == Winner.PLAYER2);

		assertTrue(rockScissorsPaperService.findWinner(Shape.ROCK, Shape.LIZARD) == Winner.PLAYER1);
		assertTrue(rockScissorsPaperService.findWinner(Shape.LIZARD, Shape.ROCK) == Winner.PLAYER2);
	}

	@Test
	public void checkScissorsWins() {
		assertTrue(rockScissorsPaperService.findWinner(Shape.SCISSORS, Shape.PAPER) == Winner.PLAYER1);
		assertTrue(rockScissorsPaperService.findWinner(Shape.PAPER, Shape.SCISSORS) == Winner.PLAYER2);

		assertTrue(rockScissorsPaperService.findWinner(Shape.SCISSORS, Shape.LIZARD) == Winner.PLAYER1);
		assertTrue(rockScissorsPaperService.findWinner(Shape.LIZARD, Shape.SCISSORS) == Winner.PLAYER2);
	}

	@Test
	public void checkPaperWins() {
		assertTrue(rockScissorsPaperService.findWinner(Shape.PAPER, Shape.ROCK) == Winner.PLAYER1);
		assertTrue(rockScissorsPaperService.findWinner(Shape.ROCK, Shape.PAPER) == Winner.PLAYER2);

		assertTrue(rockScissorsPaperService.findWinner(Shape.PAPER, Shape.SPOCK) == Winner.PLAYER1);
		assertTrue(rockScissorsPaperService.findWinner(Shape.SPOCK, Shape.PAPER) == Winner.PLAYER2);
	}

	@Test
	public void checkLizardWins() {
		assertTrue(rockScissorsPaperService.findWinner(Shape.LIZARD, Shape.SPOCK) == Winner.PLAYER1);
		assertTrue(rockScissorsPaperService.findWinner(Shape.SPOCK, Shape.LIZARD) == Winner.PLAYER2);

		assertTrue(rockScissorsPaperService.findWinner(Shape.LIZARD, Shape.PAPER) == Winner.PLAYER1);
		assertTrue(rockScissorsPaperService.findWinner(Shape.PAPER, Shape.LIZARD) == Winner.PLAYER2);
	}

	@Test
	public void checkSpockWins() {
		assertTrue(rockScissorsPaperService.findWinner(Shape.SPOCK, Shape.SCISSORS) == Winner.PLAYER1);
		assertTrue(rockScissorsPaperService.findWinner(Shape.SCISSORS, Shape.SPOCK) == Winner.PLAYER2);

		assertTrue(rockScissorsPaperService.findWinner(Shape.SPOCK, Shape.ROCK) == Winner.PLAYER1);
		assertTrue(rockScissorsPaperService.findWinner(Shape.ROCK, Shape.SPOCK) == Winner.PLAYER2);
	}

}
