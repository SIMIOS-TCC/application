//package br.usp.poli;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import br.usp.poli.model.Simio;
//import br.usp.poli.model.SimioDistance;
//import br.usp.poli.extended.AssertExtended;
//import br.usp.poli.repository.SimioDistanceRepository;
//import br.usp.poli.repository.SimioRepository;
//import br.usp.poli.service.SimioDistanceService;
//import br.usp.poli.service.SimioService;
//import br.usp.poli.utils.GraphUtil;
//import br.usp.poli.utils.Point;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = SimiosApplication.class)
//@WebAppConfiguration
//public class GraphTests {
//		
//	@Autowired
//	SimioService simioService;
//	@Autowired
//	SimioDistanceService simioDistanceService;
//	@Autowired
//	SimioRepository simioRepository;
//	@Autowired
//	SimioDistanceRepository simioDistanceRepository;
//	
//	@Autowired
//	private GraphUtil graphUtil;
//	
//	private Map<Long, Point> mapExpected;
//	
//	private Long mainSimioId;
//	
//	@Before
//	public void loadContext() {
//		//insere os simios no banco
//		for(int i = 1; i < 5; i++) {
//			Simio simio = new Simio();
//			simio.setName("Simio" + i);
//			simio.setTemperature(35);
//			simio.setId(simioService.create(simio));
//			if(i == 1) mainSimioId = simio.getId();
//		}
//		
//		mapExpected = new HashMap<Long, Point>();
//		mapExpected.put(mainSimioId, new Point(0D, 0D));
//		mapExpected.put(mainSimioId + 3L, new Point(10D, 0D));
//		mapExpected.put(mainSimioId + 2L, new Point(10D, 10D));
//		mapExpected.put(mainSimioId + 1L, new Point(0D, 10D));
//		//mapExpected.put(5L, new Point(20D, 0D));
//		
//		//Insere os simio distances no banco
//		for(Long i = mainSimioId; i < mainSimioId + 4L; i++) {
//			for (Long j = mainSimioId + 3L; j > i; j--) {
//				SimioDistance sd = new SimioDistance();
//				sd.setDistance(GraphUtil.getDistance(mapExpected.get(i), mapExpected.get(j)));
//				sd.setSimioIds(i, j);
//				simioDistanceService.create(sd);
//			}
//		}
//	}
//	
//	@After
//	public void cleanContext() {
//		//simioDistanceRepository.deleteAll();
//		//simioRepository.deleteAll();
//	}
//	
//	@Test
//	public void getGraph() {
//		graphUtil.createGraph(simioService.readById(mainSimioId));
//		Map<Long, Point> mapActuals = graphUtil.mapping;
//		AssertExtended.assertMappingEquals(mapExpected, mapActuals);
//	}
//	
//}