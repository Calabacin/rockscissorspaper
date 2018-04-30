package eu.calabacin.kata.rockscissorspaper.controller;

import java.util.function.Supplier;

public class UserWantsToQuitException extends Exception implements Supplier<UserWantsToQuitException> {

	private static final long serialVersionUID = -7836170226888320444L;

	@Override
	public UserWantsToQuitException get() {
		return this;
	}

}
