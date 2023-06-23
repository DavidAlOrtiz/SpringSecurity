package com.dva.springboot.app.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String login(@RequestParam(name = "error", required = false) String error,
			Model model, Principal principal, RedirectAttributes flash ) {
		if(principal != null) {
			flash.addFlashAttribute("info", "Ya has iniciado sesion");
			return "redirect:/usuario/";
		}
		
		if(error != null) {
			model.addAttribute("error", "usuario o contrasenia invalida");
		}
		
		return "loginA";
	}
}
