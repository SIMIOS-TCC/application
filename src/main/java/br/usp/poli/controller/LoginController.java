package br.usp.poli.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ModelAndView login(@AuthenticationPrincipal User user, @RequestParam(required=false) String error){
		
		if(user!=null) {
			return new ModelAndView("redirect:/menu");
		}
		
		ModelAndView mav = new ModelAndView("Login");
		
		if(error != null) {
			mav.addObject("errorMessage", "Invalid credentials");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/acess-denied")
	public String acessoNegado(){
 
		return "access-denied";
	}
}
