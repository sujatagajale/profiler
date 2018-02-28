package com.profile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
public class HelloController {

	private static final Logger logger = LogManager.getLogger(HelloController.class);

	@GetMapping("/hello")
	public String hello(Model model) {
		logger.trace("Entering application...");

		logger.info("Hello Log4j2...");
		

		model.addAttribute("name", "Sanjay Kokane");

		return "welcome";
	}
}
