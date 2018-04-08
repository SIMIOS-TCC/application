package br.usp.poli;

import java.util.Date;

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
import br.usp.poli.repository.SimioDistanceRepository;
import br.usp.poli.repository.SimioRepository;
import br.usp.poli.service.SimioDistanceService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SimiosApplication.class)
@WebAppConfiguration
public class SimioDistanceRepoTests {
	
	@Autowired
	SimioDistanceService simioDistanceService;
	@Autowired
	SimioDistanceRepository simioDistanceRepository;
	@Autowired
	SimioRepository simioRepository;
	
	private Simio simio = new Simio();
	private SimioDistance simioDistance = new SimioDistance();

	@Before
	public void loadContext() {
		
		simioRepository.save(simio);
		
		simioDistance.setSimio(simio);
		simioDistance.setDistance(1D);
		simioDistance.setSimio2_id(2L);
		simioDistance.setTimestamp(new Date());
		
		simioDistanceService.create(simioDistance);
	}
	
	@After
	public void cleanContext() {
		simioDistanceRepository.delete(simioDistance);
		simioRepository.delete(simio);
	}
	
	//Create
	@Test
	public void createSimioTest() {
		Assert.assertEquals(simioDistance, simioDistanceRepository.findOne(simioDistance.getId()));
	}
	//Update
	@Test
	public void updateSimioTest() {
		simioDistance.setDistance(2D);
		simioDistanceService.update(simioDistance);		
		Assert.assertEquals(simioDistance, simioDistanceRepository.findOne(simioDistance.getId()));
	}
	//Read
	@Test
	public void readSimioTest() {	
		Assert.assertEquals(simioDistanceService.readById(simioDistance.getId()), simioDistanceRepository.findOne(simioDistance.getId()));
	}
	//Delete
	@Test
	public void deleteSimioTest() {
		Long id = simioDistance.getId();
		simioDistanceService.delete(id);		
		Assert.assertNull(simioDistanceRepository.findOne(id));
	}
}
