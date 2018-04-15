package br.usp.poli.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.usp.poli.model.Simio;
import br.usp.poli.model.SimioDistance;
import br.usp.poli.service.SimioDistanceService;
import br.usp.poli.service.SimioService;

@Component
public class GraphUtil {
	
	@Autowired
	SimioService simioService;
	@Autowired
	SimioDistanceService simioDistanceService;
	
	@Autowired
	Graph graph;
	
	public Map<Long, Point> mapping;

	public GraphUtil() {
		super();
	}
	
	//TODO: refactor
	public void createGraph(Simio simio) {
		mapping = new HashMap<Long, Point>();
		
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
		//TODO: check null
		Double a = simioDistanceService.readBySimioPair(mainSimioIds.get(0), mainSimioIds.get(1)).getDistance(); 
		Double b = simioDistanceService.readBySimioPair(mainSimioId, mainSimioIds.get(0)).getDistance();
		Double c = simioDistanceService.readBySimioPair(mainSimioId, mainSimioIds.get(1)).getDistance();
		List<Double> referenceSides = Arrays.asList(new Double[] {a, b, c});
		List<Point> reference = GraphUtil.getReferenceTriangle(referenceSides);
		
		mapping.put(mainSimioIds.get(0), reference.get(1).trimPointToPrecision());
		mapping.put(mainSimioIds.get(1), reference.get(2).trimPointToPrecision());
		
		//get each relative position
		for(int i = 2; i < mainSimioIds.size(); i++) {
			Long simioId = mainSimioIds.get(i);
			Double r1 = simioDistanceService.readBySimioPair(mainSimioId, simioId).getDistance();
			Double r2 = simioDistanceService.readBySimioPair(mainSimioIds.get(0), simioId).getDistance();
			Double r3 = simioDistanceService.readBySimioPair(mainSimioIds.get(1), simioId).getDistance();
			List<Double> distances = Arrays.asList(new Double[] {r1, r2, r3});
			Point position = GraphUtil.getRelativePosition(reference, distances);
			
			mapping.put(simioId, position.trimPointToPrecision());
		}
		
		List<Simio> simios = new ArrayList<Simio>();
		for(Long id : mapping.keySet()) {
			simios.add(simioService.readById(id));
		}
		graph.setSimios(simios);

		List<Point> positions = new ArrayList<Point>();
		for(Long id : mapping.keySet()) {
			positions.add(mapping.get(id));
		}
		graph.setPositions(positions);
	}
	
	public List<Long> getSimioIds(Long mainSimioId, List<SimioDistance> simioDistances) {
		List<Long> otherSimioIds = new ArrayList<Long>();
		for(SimioDistance sd : simioDistances) {
			otherSimioIds.add(sd.getPairedSimioId(mainSimioId));
		}
		return otherSimioIds;
	}

	public static List<Point> getReferenceTriangle(List<Double> distances) {
		List<Point> referenceTriangle = new ArrayList<Point>();
		
		Double a = distances.get(0);
		Double b = distances.get(1);
		Double c = distances.get(2);
		
		//c² = a² + b² - 2 * a * b * sin (alpha)
		Double pitagorasRest = Math.pow(b,2) + Math.pow(a,2) - Math.pow(c,2);
		Double sinAlpha = pitagorasRest/(2*a*b);
		Double alpha = Math.asin(sinAlpha);
		
		Double x = b - a*sinAlpha;
		Double y = a*Math.cos(alpha);
		
		referenceTriangle.add(new Point(0D, 0D));
		referenceTriangle.add(new Point(b, 0D));
		referenceTriangle.add(new Point(x, y));
		
		return referenceTriangle;
	}
	
	//https://en.wikipedia.org/wiki/Trilateration
	public static Point getRelativePosition(List<Point> reference, List<Double> distances) {		
		Double r1 = distances.get(0);
		Double r2 = distances.get(1);
		Double r3 = distances.get(2);
		
		Double d = reference.get(1).x;
		Double i = reference.get(2).x;
		Double j = reference.get(2).y;
		
		Double x = ( Math.pow(r1,2) - Math.pow(r2,2) + Math.pow(d,2) )/(2*d);
		Double y = ( Math.pow(r1,2) - Math.pow(r3,2) + Math.pow(i,2) + Math.pow(j,2) )/(2*j) - i*x/j; 
		
		return new Point(x,y);
	}
	
	public static Double getDistance(Point p1, Point p2) {
		Double deltaX = Math.abs(p1.x-p2.x);
		Double deltaY = Math.abs(p1.y-p2.y);
		Double distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
		return distance;
	}
	
}