package br.usp.poli.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import br.usp.poli.entity.SimioEntity;
import br.usp.poli.model.Simio;
import br.usp.poli.service.SimioService;
import br.usp.poli.utils.Graph;
import br.usp.poli.utils.GraphUtil;

import static br.usp.poli.utils.ConstantsFile.SIMIO_SEARCH;
import static br.usp.poli.utils.ConstantsFile.SIMIO_REGISTER;
import static br.usp.poli.utils.ConstantsFile.SIMIO_GRAPH;

@Controller
@RequestMapping("/simio")
public class SimioController {
	
	@Autowired
	private SimioService simioService;
	
	@Autowired
	private Graph graph;
	@Autowired
	private GraphUtil graphUtil;
	
	@RequestMapping("/graph/{id}")
	public ModelAndView graph(@PathVariable("id") SimioEntity simioEntity) {
		ModelAndView mav = new ModelAndView(SIMIO_GRAPH);
		
		Gson gson = new Gson();
		graphUtil.createGraph(simioService.entityToModel(simioEntity));
		String json = gson.toJson(graph);
		mav.addObject("mapping", json);
		return mav;
	}
	
	@RequestMapping("/new")
	public ModelAndView insert() {
		ModelAndView mav = new ModelAndView(SIMIO_REGISTER);
		mav.addObject(new SimioEntity());
		return mav;
	}
	
	//TODO:implementar nome dos macacos e filtro por nome
	@RequestMapping("/search")
	public ModelAndView pesquisar(@RequestParam(defaultValue = "%") String name) {
		ModelAndView mav = new ModelAndView(SIMIO_SEARCH);
		List<Simio> allSimios = simioService.readByName(name);
		mav.addObject("allSimios",allSimios);
		return mav;
	}
	
	
	//---------------- CRUD -------------------------
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String create(@Valid Simio simio, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			System.out.println("Erro: " + result.toString());
			return SIMIO_REGISTER;
		}
		
		simioService.create(simio);
		
		attributes.addFlashAttribute("message", "Simio was successfully saved!");
		
		return "redirect:/simio/new";
	}
	
	@RequestMapping("/new/{id}")
	public ModelAndView update(@PathVariable("id") SimioEntity simio) {
		ModelAndView mav = new ModelAndView(SIMIO_REGISTER);
		mav.addObject(simio);
		return mav;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	public String delete(@PathVariable Long id, RedirectAttributes attributes) {
		simioService.delete(id);
		attributes.addFlashAttribute("mensagem", "Simio was successfully deleted.");
		return "redirect:/simio/search";
	}
}
