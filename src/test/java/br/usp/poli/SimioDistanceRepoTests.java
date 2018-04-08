package br.usp.poli;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.After;
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
public class SimioDistanceRepoTests {
	
	@Autowired
	SimioService simioService;
	@Autowired
	SimioRepository simioRepository;
	
	@Autowired
	SimioDistanceService simioDistanceService;
	
	private Simio simio1 = new Simio();
	private Simio simio2 = new Simio();
	private SimioDistance simioDistance = new SimioDistance();

	@Before
	public void loadContext() {
		simio1.setDistances(new ArrayList<SimioDistance>());
		simio1.setTemperature(35);
		simioService.create(simio1);
	}
	
	@After
	public void cleanContext() {
		simioRepository.delete(simio1);
	}
	
	//Create
	@Test
	public void createSimioTest() {
		Assert.assertEquals(simio1, simioRepository.findOne(simio1.getId()));
	}
	//Update
	@Test
	public void updateSimioTest() {
		simio1.setTemperature(40);
		simioService.update(simio1);		
		Assert.assertEquals(simio1, simioRepository.findOne(simio1.getId()));
	}
	//Read
	@Test
	public void readSimioTest() {	
		Assert.assertEquals(simioService.readById(simio1.getId()), simioRepository.findOne(simio1.getId()));
	}
	@Test
	public void readTemperatureSimioTest() {	
		int temperature = simio1.getTemperature()-1;
		List<Simio> expectedSimios = new ArrayList<Simio>();
		expectedSimios.add(simio1);
		Assert.assertEquals(expectedSimios, simioService.readTemperatureGreaterThan(temperature));
	}
	//Delete
	@Test
	public void deleteSimioTest() {
		Long id = simio1.getId();
		simioService.delete(id);		
		Assert.assertNull(simioRepository.findOne(id));
	}
}
