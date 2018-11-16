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
	
	private boolean isNewDB = true;
	
	@Before
	public void loadContext() {
		SimioEntity simio = readOrInsertSimio();
		
		if(isNewDB) {
			List<AccessPointEntity> aps = readOrInsertAPs();
			readOrInsertDistances(aps, simio);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test() {
		Assert.assertTrue(true);
	}
	
	private void readOrInsertDistances(List<AccessPointEntity> aps, SimioEntity simio) {
		Double d1 = Math.sqrt(10);
		Double d2 = Math.sqrt(8);
		Double d3 = Math.sqrt(4);
		
		List<Double> distances = Arrays.asList(new Double[] {d1, d2, d3});
		
		for(int i = 0; i < 3; i++) {
			AccessPointEntity ap = aps.get(i);
			
			//Point simioPosition = new Point(0D,0D);
			
			Date timestamp = new Date(118, 4, 3);
			
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
		List<AccessPointEntity> aps = new ArrayList<AccessPointEntity>();
		
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
	
	private SimioEntity readOrInsertSimio() {
		List<SimioEntity> simios = simioRepository.findByNameContaining("Nanica");
		if(!simios.isEmpty()) {
			isNewDB = false;
			return simios.get(0);
		}
		
		SimioEntity simio = SimioEntity.builder()
				.name("Nanica")
				.gender(Gender.FEMALE)
				.build();
		
		simio = simioRepository.saveAndFlush(simio);
		return simio;
	}
	
}