//package br.usp.poli;
//
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import br.usp.poli.entity.SimioEntity;
//import br.usp.poli.model.SimioDistance;
//import br.usp.poli.repository.SimioDistanceRepository;
//import br.usp.poli.repository.SimioRepository;
//import br.usp.poli.service.SimioDistanceService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = SimiosApplication.class)
//@WebAppConfiguration
//public class SimioDistanceRepoTests {
//	
//	@Autowired
//	SimioDistanceService simioDistanceService;
//	@Autowired
//	SimioDistanceRepository simioDistanceRepository;
//	@Autowired
//	SimioRepository simioRepository;
//	
//	private SimioEntity simio1 = new SimioEntity();
//	private SimioEntity simio2 = new SimioEntity();
//	private SimioDistance simioDistance = new SimioDistance();
//
//	@Before
//	public void loadContext() {
//		simio1.setName("Simio1");
//		simioRepository.save(simio1);
//		
//		simio2.setName("Simio2");
//		simioRepository.save(simio2);
//		
//		simioDistance.setSimioId2(1L);
//		simioDistance.setSimioIds(simio1.getId(), simio2.getId());
//		simioDistance.setId(simioDistanceService.create(simioDistance));
//	}
//	
//	@After
//	public void cleanContext() {
//		simioDistanceRepository.deleteAll();
//		simioRepository.deleteAll();
//	}
//	
//	//Create
//	@Test
//	public void createSimioTest() {
//		//Assert.assertEquals(simioDistance, simioDistanceService.readById(simioDistance.getId()));
//	}
//	//Update
//	@Test
//	public void updateSimioTest() {
//		simioDistance.setDistance(20D);
//		simioDistanceService.update(simioDistance);		
//		//Assert.assertEquals(simioDistance, simioDistanceService.readById(simioDistance.getId()));
//	}
//	//Delete
//	@Test
//	public void deleteSimioTest() {
//		Long id = simioDistance.getId();
//		simioDistanceService.delete(id);		
//		Assert.assertNull(simioDistanceService.readById(id));
//	}
//}
