package br.usp.poli.controller;

import static br.usp.poli.utils.ConstantsFile.USER_REGISTER;
import static br.usp.poli.utils.ConstantsFile.USER_SEARCH;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.usp.poli.exception.NoRolesUserException;
import br.usp.poli.model.Role;
import br.usp.poli.model.UserModel;
import br.usp.poli.service.RoleService;
import br.usp.poli.service.UserService;
 
@Controller
@RequestMapping("/user") 
public class UserController {
 
	@Value("${projeto.versao}")
	private String projetoVersao;
	
	@Autowired
	private RoleService roleService;
 
	@Autowired 
	private UserService userService;
	
	//TODO: inserir dois campos de password com verificação de match em JS
	@RequestMapping(value="/new", method= RequestMethod.GET)	
	public ModelAndView newUser(UserModel user) {
		ModelAndView mav = new ModelAndView(USER_REGISTER);
		mav.addObject("user", user);
		
		List<Role> roles = roleService.readAll();
		
		mav.addObject("roles", roles);
	    return mav;
	}
	
	@RequestMapping(value="/search", method= RequestMethod.GET)	
	public ModelAndView search(Model model) {
		model.addAttribute("allUsers", this.userService.readAll());
 
	    return new ModelAndView(USER_SEARCH);
	}
	
	@RequestMapping(value="/{id}/activate", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long id) {
		return "" + userService.toggleActive(id);
	}
	
	//---------------- CRUD -------------------------
	@RequestMapping(value="/new", method= RequestMethod.POST)
	public String save(@Valid UserModel user, final BindingResult result, RedirectAttributes attributes, Model model){
		if(result.hasErrors()){
			setRejectModelAndView(model, user, result);
			return USER_REGISTER;	
		}
		
		try {
			userService.create(user);
		} catch (NoRolesUserException e) {
			result.rejectValue("roles", e.getMessage(), e.getMessage());
			setRejectModelAndView(model, user, result);
			return USER_REGISTER;
		}
 
		attributes.addFlashAttribute("message", "User was successfully registered!");
		
		return "redirect:/user/search";
	}
	
	@RequestMapping(value="/new/{id}", method= RequestMethod.GET)		
	public ModelAndView update(@PathVariable Long id, Model model) {
 
		UserModel user;
		
		try {
			user = this.userService.readById(id);
		} catch (BadCredentialsException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return search(model);
		} catch (DisabledException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return search(model);
		}
 
		List<Role> roles = roleService.readAll();			
		roles.forEach(role ->{
			if(user.getRoles() != null && user.getRoles().size() > 0){
				user.getRoles().forEach(selectedRole->{
					if(selectedRole!= null){
						if(role.getId().equals(selectedRole.getId()))
							role.setChecked(true);
					}					
				});				
			}
		});
		
		ModelAndView mav = new ModelAndView(USER_REGISTER);
		mav.addObject("user", user);
		
		mav.addObject("roles", roles);
	    return mav;
	 }
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public String delete(@PathVariable Long id, RedirectAttributes attributes) {
		userService.delete(id);
		attributes.addFlashAttribute("message", "User was successfully deleted.");
		return "redirect:/user/search";
	}
	
	private Model setRejectModelAndView(Model model, UserModel user, BindingResult result) {
		List<Role> roles = roleService.readAll();			
		roles.forEach(role ->{
			if(user.getRoles() != null && user.getRoles().size() > 0){
				user.getRoles().forEach(selectedRole->{
					if(selectedRole!= null){
						if(role.getId().equals(selectedRole.getId()))
							role.setChecked(true);
					}					
				});				
			}
		});
		
		model.addAttribute("user", user);
		model.addAttribute("roles", roles);
		
		List<String> errorMessages = new ArrayList<String>();
		result.getAllErrors().forEach(e -> {
			errorMessages.add(e.getDefaultMessage());
		});
		model.addAttribute("errorMessages", errorMessages);
		return model;
	}
	
	@ModelAttribute("projetoVersao")
	public String getProjetoVersao() {
		return "Versão " + projetoVersao;
	}
	
}