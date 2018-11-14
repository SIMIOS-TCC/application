package br.usp.poli.controller;

import static br.usp.poli.utils.ConstantsFile.AP_SEARCH;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.usp.poli.entity.AccessPointEntity;
import br.usp.poli.model.AccessPoint;
import br.usp.poli.service.AccessPointService;

@Controller
@RequestMapping("/ap")
public class AccessPointController {
	
	@Value("${projeto.versao}")
	private String projetoVersao;
	
	@Autowired
	private AccessPointService apService;
	
	@RequestMapping("/search")
	public ModelAndView search(AccessPoint ap) {
		ModelAndView mav = new ModelAndView(AP_SEARCH);
		List<AccessPoint> allAPs = apService.readAll();
		mav.addObject("allAPs",allAPs);
		
		if(ap == null) {
			ap = new AccessPoint();
		}
		mav.addObject("ap", ap);
		return mav;
	}
	
	
	//---------------- CRUD -------------------------
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String create(@Valid AccessPoint ap, BindingResult result, RedirectAttributes attributes, Model model) {
		if(result.hasErrors()) {
			resultHasErrorsModel(ap, model, result);
			return AP_SEARCH;
		}
		
		apService.save(ap);
		
		attributes.addFlashAttribute("message", "Access Point was successfully saved!");
		
		return "redirect:/ap/search";
	}
	
	private void resultHasErrorsModel(AccessPoint ap, Model model, BindingResult result) {
		List<String> errorMessages = new ArrayList<String>();
		result.getAllErrors().forEach(e -> {
			errorMessages.add(e.getDefaultMessage());
		});
		model.addAttribute("errorMessages", errorMessages);
		
		model.addAttribute("ap", ap);
		
		List<AccessPoint> allAPs = apService.readAll();
		model.addAttribute("allAPs",allAPs);
	}
	
	@RequestMapping("/new/{id}")
	public ModelAndView update(@PathVariable("id") AccessPointEntity ap) {
		ModelAndView mav = new ModelAndView(AP_SEARCH);
		mav.addObject("ap", ap);
		
		List<AccessPoint> allAPs = apService.readAll();
		mav.addObject("allAPs",allAPs);
		
		mav.addObject("isEdit", true);
		return mav;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public String delete(@PathVariable Long id, RedirectAttributes attributes) {
		apService.delete(id);
		attributes.addFlashAttribute("mensagem", "Access Point was successfully deleted.");
		return "redirect:/ap/search";
	}
	
	@ModelAttribute("projetoVersao")
	public String getProjetoVersao() {
		return "Versão " + projetoVersao;
	}
	
}
