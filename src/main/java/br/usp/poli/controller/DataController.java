package br.usp.poli.controller;

import static br.usp.poli.utils.ConstantsFile.SIMIO_SEARCH;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/data")
public class DataController {
	
	@Value("${projeto.versao}")
	private String projetoVersao;
	
	@RequestMapping()
	public ModelAndView get() {
		ModelAndView mav = new ModelAndView(SIMIO_SEARCH);
		return mav;
	}
	
	@ModelAttribute("projetoVersao")
	public String getProjetoVersao() {
		return "Versão " + projetoVersao;
	}
	
}
