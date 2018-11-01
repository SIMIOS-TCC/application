package br.usp.poli.controller;

import static br.usp.poli.utils.ConstantsFile.SIMIO_SEARCH;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.usp.poli.entity.SimioEntity;
import br.usp.poli.enums.Gender;
import br.usp.poli.exception.UnsuitedBirthYearException;
import br.usp.poli.model.Simio;
import br.usp.poli.service.SimioService;
import br.usp.poli.utils.Graph;

@Controller
@RequestMapping("/simio")
public class SimioController {
	
	@Autowired
	private SimioService simioService;
	
	@Autowired
	private Graph graph;
//	@Autowired
//	private GraphUtil graphUtil;
//	
//	@RequestMapping("/graph/{id}")
//	public ModelAndView graph(@PathVariable("id") SimioEntity simioEntity) {
//		ModelAndView mav = new ModelAndView(SIMIO_GRAPH);
//		
//		Gson gson = new Gson();
//		graphUtil.createGraph(simioService.entityToModel(simioEntity));
//		String json = gson.toJson(graph);
//		mav.addObject("mapping", json);
//		return mav;
//	}
	
	//TODO:implementar nome dos macacos e filtro por nome
	@RequestMapping("/search")
	public ModelAndView search(Simio simio) {
		ModelAndView mav = new ModelAndView(SIMIO_SEARCH);
		List<Simio> allSimios = simioService.readAll();
		mav.addObject("allSimios",allSimios);
		
		if(simio == null) {
			simio = new Simio();
		}
		mav.addObject("simio", simio);
		return mav;
	}
	
	
	//---------------- CRUD -------------------------
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String create(@Valid Simio simio, BindingResult result, RedirectAttributes attributes, Model model) {
		if(result.hasErrors()) {
			resultHasErrorsModel(simio, model);
			return SIMIO_SEARCH;
		}
		
		try {
			simioService.save(simio);
		} catch (UnsuitedBirthYearException e) {
			result.rejectValue("birthDate", e.getMessage(), e.getMessage());
			
			resultHasErrorsModel(simio, model);
			return SIMIO_SEARCH;
		}
		
		attributes.addFlashAttribute("message", "Simio was successfully saved!");
		
		return "redirect:/simio/search";
	}
	
	private void resultHasErrorsModel(Simio simio, Model model) {
		model.addAttribute("simio", simio);
		
		List<Simio> allSimios = simioService.readAll();
		model.addAttribute("allSimios",allSimios);
	}
	
	@RequestMapping("/new/{id}")
	public ModelAndView update(@PathVariable("id") SimioEntity simio) {
		ModelAndView mav = new ModelAndView(SIMIO_SEARCH);
		mav.addObject("simio", simio);
		
		List<Simio> allSimios = simioService.readAll();
		mav.addObject("allSimios",allSimios);
		
		mav.addObject("isEdit", true);
		return mav;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public String delete(@PathVariable Long id, RedirectAttributes attributes) {
		simioService.delete(id);
		attributes.addFlashAttribute("mensagem", "Simio was successfully deleted.");
		return "redirect:/simio/search";
	}
	
	@ModelAttribute("genders")
	public List<Gender> todosStatusTitulo(){
		return Arrays.asList(Gender.values());
	}
	
}
