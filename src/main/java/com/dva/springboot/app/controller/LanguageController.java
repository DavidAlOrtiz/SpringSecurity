package com.dva.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LanguageController {
	
	@GetMapping("/locale")
	public String changeLanguage(HttpServletRequest req) {
		String ultimoURL = req.getHeader("referer");
		return "redirect:".concat(ultimoURL);
	}
}
