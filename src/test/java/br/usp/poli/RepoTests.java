package br.usp.poli;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


import br.usp.poli.model.Simio;
import br.usp.poli.model.SimioDistance;
import br.usp.poli.repository.SimioRepository;
import br.usp.poli.service.SimioDistanceService;
import br.usp.poli.service.SimioService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SimiosApplication.class)
@WebAppConfiguration
public class RepoTests {
	
	@Autowired
	SimioService simioService;
	@Autowired
	SimioDistanceService simioDistanceService;
	
	private Simio simio1 = new Simio();
	private Simio simio2 = new Simio();
	private SimioDistance simioDistance = new SimioDistance();

	@Before
	public void loadContext() {
		//simioService = ApplicationContextHolder.getContext().getBean(SimioService.class);
		simio1.setDistances(new ArrayList<SimioDistance>());
		simio1.setTemperature(35);
		simio2.setTemperature(50);
	}
	
	@Test
	public void createSimioTest() {
		simioService.create(simio1);
		Assert.assertEquals(simio1, simioService.readById(simio1.getId()));
	}	
}
