package br.usp.poli.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	@RequestMapping(value={"/", "/home"})
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView("Home");
		return modelAndView;
	}
	
	@RequestMapping(value="/menu")
	public ModelAndView menu(){
		ModelAndView modelAndView = new ModelAndView("Menu");
		return modelAndView;
	}
	
	@RequestMapping(value="/login")
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView("Login");
		return modelAndView;
	}
	
	@RequestMapping(value="/acess-denied")
	public String acessoNegado(){
 
		return "access-denied";
	}
}
