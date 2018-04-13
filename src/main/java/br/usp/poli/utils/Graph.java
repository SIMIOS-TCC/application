package br.usp.poli.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import br.usp.poli.model.Simio;
import br.usp.poli.model.SimioDistance;
import br.usp.poli.service.SimioDistanceService;

public class Graph {
	
	@Autowired
	private SimioDistanceService simioDistanceService;

	private Map<Long, Point> mapping;
	
	public Graph() {
		
	}
	
	//TODO: refactor
	public void createGraph(Simio simio) {
		Long mainSimioId = simio.getId();
		mapping.put(mainSimioId, new Point(0D, 0D));
		
		//filtrar lista de distancias do main simio
		List<SimioDistance> simioDistances = simioDistanceService.readBySimioId(mainSimioId);
		for(SimioDistance sd : simioDistances) {
			if(sd.getDistance() > ConstantsFile.MAX_RANGE/2) {
				simioDistances.remove(sd);
			}
		}
		List<Long> mainSimioIds = getSimioIds(mainSimioId, simioDistances);
		
		//get Reference
		Double a = simioDistanceService.readBySimioPair(mainSimioIds.get(0), mainSimioIds.get(1)).getDistance(); //TODO: check null
		Double b = simioDistanceService.readBySimioPair(mainSimioId, mainSimioIds.get(0)).getDistance();
		Double c = simioDistanceService.readBySimioPair(mainSimioId, mainSimioIds.get(1)).getDistance();
		List<Double> referenceSides = Arrays.asList(new Double[] {a, b, c});
		List<Point> reference = GraphUtil.getReferenceTriangle(referenceSides);
		
		mapping.put(mainSimioIds.get(0), reference.get(1));
		mapping.put(mainSimioIds.get(1), reference.get(2));
		
		//get each relative position
		for(int i = 2; i < mainSimioIds.size(); i++) {
			Long simioId = mainSimioIds.get(i);
			Double r1 = simioDistanceService.readBySimioPair(mainSimioId, simioId).getDistance();
			Double r2 = simioDistanceService.readBySimioPair(mainSimioIds.get(0), mainSimioIds.get(0)).getDistance();
			Double r3 = simioDistanceService.readBySimioPair(mainSimioIds.get(1), mainSimioIds.get(1)).getDistance();
			List<Double> distances = Arrays.asList(new Double[] {r1, r2, r3});
			Point position = GraphUtil.getRelativePosition(reference, distances);
			
			mapping.put(simioId, position);
		}
		
	}
	
	private List<Long> getSimioIds(Long mainSimioId, List<SimioDistance> simioDistances) {
		List<Long> otherSimioIds = new ArrayList<Long>();
		for(SimioDistance sd : simioDistances) {
			otherSimioIds.add(sd.getPairedSimioId(mainSimioId));
		}
		return otherSimioIds;
	}
	
}