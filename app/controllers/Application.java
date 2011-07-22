package controllers;

import play.*;
import play.mvc.*;
import play.Play;
import play.data.validation.*;

import java.util.*;

import models.*;

public class Application extends Controller {

	@Before
	static void addDefaults() {
		renderArgs.put("transcoderTitle", Play.configuration.getProperty("transcoder.title"));
		renderArgs.put("transcoderBaseline", Play.configuration.getProperty("transcoder.baseline"));
	}

	public static void index() {
		List <Transcodification> lastTranscos = Transcodification.find(
			"order by postedAt desc"
		).from(0).fetch(10);
		render(lastTranscos);
	}

	public static void show(Long id) {
		Transcodification transco = Transcodification.findById(id);
		render(transco);
	}

	public static void form() {
		render();
	}


	public static void save(@Valid User user) {
		if (validation.hasErrors()) {
			params.flash();
			validation.keep();
			form();
		}
		user.save();
		index();
	}
}
