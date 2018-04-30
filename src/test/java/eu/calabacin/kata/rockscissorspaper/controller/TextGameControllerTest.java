package eu.calabacin.kata.rockscissorspaper.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eu.calabacin.kata.rockscissorspaper.service.GameService;

public class TextGameControllerTest {
	private TextGameController textGameController;
	private GameService contestService;

	public TextGameControllerTest() {
		textGameController = new TextGameController(contestService);
	}

	private boolean isBetweenNumbers(int number, int min, int max) {
		return number >= min && number <= max;
	}

	@Test
	public void checkValidIntsFromText() {
		int number;
		int limit = 5;
		number = textGameController.findIntFromText("1", limit);
		assertTrue(isBetweenNumbers(number, 1, limit));
		number = textGameController.findIntFromText("2", limit);
		assertTrue(isBetweenNumbers(number, 1, limit));
		number = textGameController.findIntFromText("3", limit);
		assertTrue(isBetweenNumbers(number, 1, limit));
		number = textGameController.findIntFromText("4", limit);
		assertTrue(isBetweenNumbers(number, 1, limit));
		number = textGameController.findIntFromText("5", limit);
		assertTrue(isBetweenNumbers(number, 1, limit));

		limit = 3;
		number = textGameController.findIntFromText("1", limit);
		assertTrue(isBetweenNumbers(number, 1, limit));
		number = textGameController.findIntFromText("2", limit);
		assertTrue(isBetweenNumbers(number, 1, limit));
		number = textGameController.findIntFromText("3", limit);
		assertTrue(isBetweenNumbers(number, 1, limit));
	}

	@Test(expected = NumberFormatException.class)
	public void checkInvalidIntsTooBig() {
		textGameController.findIntFromText("6", 5);
	}

	@Test(expected = NumberFormatException.class)
	public void checkInvalidIntsTooBigForStandardGame() {
		textGameController.findIntFromText("4", 3);
	}

	@Test(expected = NumberFormatException.class)
	public void checkInvalidIntsTooSmall() {
		textGameController.findIntFromText("-1", 5);
	}

}
