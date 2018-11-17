package br.usp.poli.controller;

import static br.usp.poli.utils.ConstantsFile.GRAPH_MAIN;
import static br.usp.poli.utils.ConstantsFile.SIMIO_SEARCH;

import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import br.usp.poli.entity.SimioEntity;
import br.usp.poli.enums.Gender;
import br.usp.poli.exception.UnsuitedBirthYearException;
import br.usp.poli.model.Simio;
import br.usp.poli.model.SimioDistance;
import br.usp.poli.service.SimioDistanceService;
import br.usp.poli.service.SimioService;
import br.usp.poli.utils.GraphUtil;
import br.usp.poli.utils.Point;

@Controller
@RequestMapping("/simio")
public class SimioController {
	
	@Value("${projeto.versao}")
	private String projetoVersao;
	
	@Autowired
	private SimioService simioService;
	@Autowired
	SimioDistanceService simioDistanceService;
	
	@Autowired
	private GraphUtil graphUtil;
	
	private Gson gson = new Gson();
	
	@RequestMapping("/graph")
	public ModelAndView graph(@RequestParam(required=false) String id) {
		
		ModelAndView mav = new ModelAndView(GRAPH_MAIN);
		
		Simio simio = new Simio();
		if(id != null) {
			simio = simioService.readById(Long.valueOf(id));
		}
		
		mav.addObject("simio", simio);
		
		List<Simio> allSimios = simioService.readAll();
		List<SimioDistance> allDistances = simioDistanceService.readByNewestTimestamp();

		List<Simio> graph = graphUtil.createGraph(allSimios, allDistances);
		String json = gson.toJson(graph);
		mav.addObject("mapping", json);
		
		Point reference = new Point(0D, 0D);
		if(id != null) {
			for(int i = 0; i < graph.size(); i++) {
				Simio s = graph.get(i);
				if(s.getId() == Long.valueOf(id)) {
					reference = s.getPosition().getPoint();
					continue;
				}
			}
		}
		mav.addObject("reference", gson.toJson(reference));
		
		Double scale = 1D;
		for(int i = 0; i < graph.size(); i++) {
			Double distance = GraphUtil.getDistance(reference, graph.get(i).getPosition().getPoint());
			if(distance > scale) {
				scale = distance;
			}
		}
		scale *= 1.1;
		mav.addObject("scale", scale);
		
		return mav;
	}
	
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
	
	@ModelAttribute("projetoVersao")
	public String getProjetoVersao() {
		return "Versão " + projetoVersao;
	}
	
}
