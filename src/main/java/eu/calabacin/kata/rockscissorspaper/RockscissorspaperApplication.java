package eu.calabacin.kata.rockscissorspaper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import eu.calabacin.kata.rockscissorspaper.controller.TextGameController;

/*
 * Using Spring Boot is excessive for this kind of project, but I wanted to try a few things so I used it.
 * 
 * The idea is that it will receive a parameter when run and that will decide which game client should be used.
 * 
 * This application and the whole controller package could be moved to another project,
 * leaving the game logic and entities available as a library (perhaps removing Spring functionality from everywhere in it). 
 */
@SpringBootApplication
public class RockscissorspaperApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(RockscissorspaperApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(RockscissorspaperApplication.class, args);
		runFrontend(context, args);
		endApplication(context);
	}

	private static void runFrontend(ConfigurableApplicationContext context, String[] args) {
		try {
			context.getBean(TextGameController.class).play();
		} catch (Throwable e) {
			LOGGER.error("An error ocurred!", e);
		}
	}

	public static void endApplication(ConfigurableApplicationContext context) {
		context.close();
	}
}
