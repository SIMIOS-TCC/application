package br.usp.poli;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
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
import br.usp.poli.service.SimioService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SimiosApplication.class)
@WebAppConfiguration
public class SimioRepoTests {
	
	@Autowired
	SimioService simioService;
	@Autowired
	SimioRepository simioRepository;
	
	private Simio simio = new Simio();

	@Before
	public void loadContext() {
		simio.setDistances(new ArrayList<SimioDistance>());
		simio.setTemperature(35);
		simioService.create(simio);
	}
	
	@After
	public void cleanContext() {
		simioRepository.delete(simio);
	}
	
	//Create
	@Test
	public void createSimioTest() {
		Assert.assertEquals(simio, simioRepository.findOne(simio.getId()));
	}
	//Update
	@Test
	public void updateSimioTest() {
		simio.setTemperature(40);
		simioService.update(simio);		
		Assert.assertEquals(simio, simioRepository.findOne(simio.getId()));
	}
	//Read
	@Test
	public void readSimioTest() {	
		Assert.assertEquals(simioService.readById(simio.getId()), simioRepository.findOne(simio.getId()));
	}
	@Test
	public void readTemperatureSimioTest() {	
		int temperature = simio.getTemperature()-1;
		List<Simio> expectedSimios = new ArrayList<Simio>();
		expectedSimios.add(simio);
		Assert.assertEquals(expectedSimios, simioService.readTemperatureGreaterThan(temperature));
	}
	//Delete
	@Test
	public void deleteSimioTest() {
		Long id = simio.getId();
		simioService.delete(id);		
		Assert.assertNull(simioRepository.findOne(id));
	}
}
