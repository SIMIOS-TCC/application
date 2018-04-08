package br.usp.poli;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.usp.poli.model.Simio;
import br.usp.poli.model.SimioDistance;
import br.usp.poli.service.SimioDistanceService;
import br.usp.poli.service.SimioService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class RepoTests {
	
	//@Autowired
	private SimioService simioService = new SimioService();
	//@Autowired
	private SimioDistanceService simioDistanceService;
	
	private Simio simio1 = new Simio();
	private Simio simio2 = new Simio();
	private SimioDistance simioDistance = new SimioDistance();
	
	@Before
	public void loadContext() {
		simio1.setDistances(new ArrayList<SimioDistance>());
		simio1.setTemperature(35);
		simio2.setTemperature(50);
	}
	
	@Test
	public void createSimioTest() {
		simioService.create(simio1);
		Optional<Simio> simio3 = simioService.readById(1L);
		Assert.assertEquals(simio1, simio3);
	}	
}
