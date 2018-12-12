package br.usp.poli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.usp.poli.entity.AccessPointEntity;
import br.usp.poli.entity.SimioDistanceEntity;
import br.usp.poli.entity.SimioEntity;
import br.usp.poli.enums.Gender;
import br.usp.poli.repository.AccessPointRepository;
import br.usp.poli.repository.SimioDistanceRepository;
import br.usp.poli.repository.SimioRepository;
import br.usp.poli.utils.GraphUtil;
import br.usp.poli.utils.Point;
import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SimiosApplication.class)
@WebAppConfiguration
public class GraphTests {
		
	@Autowired
	SimioRepository simioRepository;
	@Autowired
	AccessPointRepository apRepository;
	@Autowired
	SimioDistanceRepository simioDistanceRepository;
	
	@Autowired
	private GraphUtil graphUtil;
	
	@Before
	public void loadContext() {
		List<AccessPointEntity> aps = readOrInsertAPs();
		
		//Simio 1
		SimioEntity simio1 = readOrInsertSimio("Nanica");
		
		Double d1 = Math.sqrt(10);
		Double d2 = Math.sqrt(8);
		Double d3 = Math.sqrt(4);
		Double d4 = Math.sqrt(1);
		
		List<Double> distances = Arrays.asList(new Double[] {d1, d2, d3, d4});
		
		insertDistances(aps, simio1, distances);
		
		//Simio 2
		SimioEntity simio2 = readOrInsertSimio("Prata");
		
		d1 = Math.sqrt(2);
		d2 = Math.sqrt(0);
		d3 = Math.sqrt(4);
		d4 = Math.sqrt(3);
		
		distances = Arrays.asList(new Double[] {d1, d2, d3, d4});
		
		insertDistances(aps, simio2, distances);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test() {
		Assert.assertTrue(true);
	}
	
	private void insertDistances(List<AccessPointEntity> aps, SimioEntity simio, List<Double> distances) {
		
		Date timestamp1 = new Date(118, 5, 3, 0, 16);
		Date timestamp2 = new Date(118, 5, 3, 0, 16);
		Date timestamp3 = new Date(118, 5, 3, 0, 16);
		Date timestamp4 = new Date(118, 5, 3, 0, 0);
		List<Date> timestamps = Arrays.asList(new Date[] {timestamp1, timestamp2, timestamp3, timestamp4});
		
		for(int i = 0; i < 4; i++) {
			int index = i;
			if(i == 3) { index = 0; }
			AccessPointEntity ap = aps.get(index);
			
			//Point simioPosition = new Point(0D,0D);
			
			
			Date timestamp = timestamps.get(i);
			
			SimioDistanceEntity d = SimioDistanceEntity.builder()
					.accessPointEntity(ap)
					.simioEntity(simio)
					.distance(distances.get(i))
					.timestamp(timestamp)
					.build();
			
			simioDistanceRepository.save(d);
		}
	}
	
	private List<AccessPointEntity> readOrInsertAPs() {
		List<AccessPointEntity> aps = apRepository.findAll();
		
		if(!aps.isEmpty()) {
			return aps;
		}
		
		List<Point> points = Arrays.asList(new Point[] {
				new Point(1D, 3D), new Point(2D, 2D), new Point(2D, 0D)
		});
		
		for(int i = 0; i < 3; i++) {
			AccessPointEntity ap = AccessPointEntity.builder()
					.x(points.get(i).x).y(points.get(i).y).build();
			aps.add(ap);
			apRepository.save(ap);
		}
		
		return aps;
	}
	
	private SimioEntity readOrInsertSimio(String name) {
		List<SimioEntity> simios = simioRepository.findByNameContaining(name);
		if(!simios.isEmpty()) {
			return simios.get(0);
		}
		
		SimioEntity simio = SimioEntity.builder()
				.name(name)
				.gender(Gender.FEMALE)
				.build();
		
		simio = simioRepository.saveAndFlush(simio);
		return simio;
	}
	
}