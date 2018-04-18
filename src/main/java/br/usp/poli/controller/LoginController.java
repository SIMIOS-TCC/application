package br.usp.poli.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView("Login");
		return modelAndView;
	}
	
	@RequestMapping(value="/acess-denied", method= RequestMethod.GET)
	public String acessoNegado(){
 
		return "access-denied";
	}
}
