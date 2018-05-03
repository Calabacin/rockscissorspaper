package eu.calabacin.kata.rockscissorspaper.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Service;

import eu.calabacin.kata.rockscissorspaper.entities.GameType;
import eu.calabacin.kata.rockscissorspaper.entities.Shape;
import eu.calabacin.kata.rockscissorspaper.entities.Winner;

@Service
public class GameService {
	private static final Random RANDOM = new Random();
	private static final List<Shape> SHAPE_VALUES = Collections.unmodifiableList(Arrays.asList(Shape.values()));

	private static GameType DEFAULT_GAME_TYPE = GameType.STANDARD;
	private static GameService INSTANCE = null;

	private Map<Shape, Set<Shape>> winningHands;

	public static GameService getInstance() {
		if (INSTANCE == null) {
			synchronized (RANDOM) {
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

	public Map<Shape, Set<Shape>> getwinningHands() {
		return winningHands;
	}

	public GameService() {
		initializeWinningHands();
	}

	private void initializeWinningHands() {
		Map<Shape, Set<Shape>> winningHands = new HashMap<>();
		winningHands.put(Shape.ROCK, Set.of(Shape.SCISSORS, Shape.LIZARD));
		winningHands.put(Shape.SCISSORS, Set.of(Shape.PAPER, Shape.LIZARD));
		winningHands.put(Shape.PAPER, Set.of(Shape.ROCK, Shape.SPOCK));
		winningHands.put(Shape.LIZARD, Set.of(Shape.SPOCK, Shape.PAPER));
		winningHands.put(Shape.SPOCK, Set.of(Shape.SCISSORS, Shape.ROCK));
		this.winningHands = Collections.unmodifiableMap(winningHands);
	}

	public Winner findWinner(Shape shapePlayer1, Shape shapePlayer2) {
		if (shapePlayer1 == shapePlayer2)
			return Winner.TIE;
		if (winningHands.get(shapePlayer1).contains(shapePlayer2))
			return Winner.PLAYER1;
		if (winningHands.get(shapePlayer2).contains(shapePlayer1))
			return Winner.PLAYER2;
		return Winner.TIE;
	}

	public Optional<Shape> findWinnerShape(Shape shape1, Shape shape2) {
		Winner result = findWinner(shape1, shape2);
		if (result == Winner.TIE)
			return Optional.empty();
		return result == Winner.PLAYER1 ? Optional.of(shape1) : Optional.of(shape2);
	}

	public Shape randomShape() {
		return randomShape(DEFAULT_GAME_TYPE);
	}

	public Shape randomShape(GameType gameType) {
		return SHAPE_VALUES.get(RANDOM.nextInt(gameType.getNumShapes()));
	}
}
