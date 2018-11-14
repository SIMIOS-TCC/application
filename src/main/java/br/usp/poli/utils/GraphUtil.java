//package br.usp.poli.utils;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import br.usp.poli.model.Simio;
//import br.usp.poli.model.SimioDistance;
//import br.usp.poli.service.SimioDistanceService;
//import br.usp.poli.service.SimioService;
//
//@Component
//public class GraphUtil {
//	
//	@Autowired
//	SimioService simioService;
//	@Autowired
//	SimioDistanceService simioDistanceService;
//	
//	@Autowired
//	Graph graph;
//	
//	public GraphUtil() {
//		super();
//	}
//	
//	public void createGraph(Long simioId, Date timestamp) {
//		
//		List<Simio> allSimios = simioService.readAll();
//		List<SimioDistance> allDistances = simioDistanceService.readByTimestamp(timestamp);
//		
//		allSimios.forEach(simio -> {
//			List<SimioDistance> distances = filterDistancesBySimio(allDistances, simio);
//			List<Point> points = getPossiblePositions(distances);
//			Point averagePoint = getAveragePoint(points);
//			Position position = Position.builder().point(averagePoint).timestamp(timestamp).build();
//			simio.setPosition(position);
//		});
//	}
//	
////	public void createGraph(Simio simio, Date timestamp) {
////		mapping = new HashMap<Long, Point>();
////		
////		Long mainSimioId = simio.getId();
////		mapping.put(mainSimioId, new Point(0D, 0D));
////		
////		//filtrar lista de distancias do main simio
////		List<SimioDistance> simioDistances = simioDistanceService.readBySimioId(mainSimioId);
////		for(SimioDistance sd : simioDistances) {
////			if(sd.getDistance() > ConstantsFile.MAX_RANGE/2) {
////				simioDistances.remove(sd);
////			}
////		}
////		List<Long> mainSimioIds = getSimioIds(mainSimioId, simioDistances);
////		
////		//get Reference
////		//TODO: check null
////		Double a = simioDistanceService.readBySimioPair(mainSimioIds.get(0), mainSimioIds.get(1)).getDistance(); 
////		Double b = simioDistanceService.readBySimioPair(mainSimioId, mainSimioIds.get(0)).getDistance();
////		Double c = simioDistanceService.readBySimioPair(mainSimioId, mainSimioIds.get(1)).getDistance();
////		List<Double> referenceSides = Arrays.asList(new Double[] {a, b, c});
////		List<Point> reference = GraphUtil.getReferenceTriangle(referenceSides);
////		
////		mapping.put(mainSimioIds.get(0), reference.get(1).trimPointToPrecision());
////		mapping.put(mainSimioIds.get(1), reference.get(2).trimPointToPrecision());
////		
////		//get each relative position
////		for(int i = 2; i < mainSimioIds.size(); i++) {
////			Long simioId = mainSimioIds.get(i);
////			Double r1 = simioDistanceService.readBySimioPair(mainSimioId, simioId).getDistance();
////			Double r2 = simioDistanceService.readBySimioPair(mainSimioIds.get(0), simioId).getDistance();
////			Double r3 = simioDistanceService.readBySimioPair(mainSimioIds.get(1), simioId).getDistance();
////			List<Double> distances = Arrays.asList(new Double[] {r1, r2, r3});
////			Point position = GraphUtil.getRelativePosition(reference, distances);
////			
////			mapping.put(simioId, position.trimPointToPrecision());
////		}
////		
////		List<Simio> simios = new ArrayList<Simio>();
////		for(Long id : mapping.keySet()) {
////			simios.add(simioService.readById(id));
////		}
////		graph.setSimios(simios);
////
////		List<Point> positions = new ArrayList<Point>();
////		for(Long id : mapping.keySet()) {
////			positions.add(mapping.get(id));
////		}
////		graph.setPositions(positions);
////	}
////	
////	public List<Long> getSimioIds(Long mainSimioId, List<SimioDistance> simioDistances) {
////		List<Long> otherSimioIds = new ArrayList<Long>();
////		for(SimioDistance sd : simioDistances) {
////			otherSimioIds.add(sd.getPairedSimioId(mainSimioId));
////		}
////		return otherSimioIds;
////	}
////
//	public static List<Point> getReferenceTriangle(List<Point> points) {
//		List<Point> referenceTriangle = new ArrayList<Point>();
//		
//		Point p1 = points.get(0);
//		Point p2 = points.get(1);
//		Point p3 = points.get(2);
//		
//		Double d = getDistance(p1, p2);
//		Double theta = getPhase(p1, p2);
//		
//		if(p1.x > p2.x) {
//			d *= -1;
//			theta += Math.PI;
//		}
//		
//		(p1.x - px.x)² + (p1.y + px.y)² = d1
//		(p2.x - px.x)² + (p2.y + px.y)² = d2
//		(p3.x - px.x)² + (p3.y + px.y)² = d3
//		
//		a² - 2ax + x² + b² - 2by + y² = d1
//		c² - 2cx + x² + d² - 2dy + y² = d2
//		e² - 2ex + x² + f² - 2fy + y² = d3
//		
//		(a² - c²) + 2x(c - a) + (b² - d²) + 2y(d - b) = d1 - d2
//		1 (a² - e²) + 2x(e - a) + (f² - d²) + 2y(f - b) = d1 - d3
//		
//		2 [(a² - c²) + 2x(c - a) + (b² - d²) + 2y(d - b) = d1 - d2]*(e-a)/(c-a)
//		
//		2 - 1:
//		 - [(a² - c²) + (b² - d²) - d1 + d2]*(e-a)/(c-a) = 2y(d - b)*(e-a)/(c-a) - 2y(f - b)
//		//c² = a² + b² - 2 * a * b * sin (alpha)
//		Double pitagorasRest = Math.pow(b,2) + Math.pow(a,2) - Math.pow(c,2);
//		Double sinAlpha = pitagorasRest/(2*a*b);
//		Double alpha = Math.asin(sinAlpha);
//		
//		Double x = b - a*sinAlpha;
//		Double y = a*Math.cos(alpha);
//		
//		referenceTriangle.add(new Point(0D, 0D));
//		referenceTriangle.add(new Point(d, 0D));
//		referenceTriangle.add(new Point(x, y));
//		
//		return referenceTriangle;
//	}
//	
//	
//	private static List<SimioDistance> filterDistancesBySimio(List<SimioDistance> allDistances, Simio simio) {
//		List<SimioDistance> distances = new ArrayList<SimioDistance>();
//		allDistances.forEach(d -> {
//			if(d.getSimio().equals(simio)) {
//				distances.add(d);
//			}
//		});
//		return distances;
//	}
//	
//	//https://en.wikipedia.org/wiki/Trilateration
//	public static Point getRelativePosition(List<Point> reference, List<Double> distances) {		
//		Double r1 = distances.get(0);
//		Double r2 = distances.get(1);
//		Double r3 = distances.get(2);
//		
//		Double d = reference.get(1).x;
//		Double i = reference.get(2).x;
//		Double j = reference.get(2).y;
//		
//		Double x = ( Math.pow(r1,2) - Math.pow(r2,2) + Math.pow(d,2) )/(2*d);
//		Double y = ( Math.pow(r1,2) - Math.pow(r3,2) + Math.pow(i,2) + Math.pow(j,2) )/(2*j) - i*x/j; 
//		
//		return new Point(x,y);
//	}
//	
//	public static List<Point> getPossiblePositions(List<SimioDistance> distances) {
//		List<Point> points = new ArrayList<Point>();
//		
//		List<Double> refDistances;
//		List<Point> reference;
//		for(int i = 0; i < distances.size() - 2; i++) {
//			for(int j = i + 1; j < distances.size() - 1; j++) {
//				Double d1 = distances.get(i).getDistance();
//				Double d2 = distances.get(j).getDistance();
//				Double d3 = distances.get(j+1).getDistance();
//				refDistances = Arrays.asList(new Double[] {d1, d2, d3});
//				
//				Point p1 = distances.get(i).getAccessPoint().getPosition();
//				Point p2 = distances.get(j).getAccessPoint().getPosition();
//				Point p3 = distances.get(j+1).getAccessPoint().getPosition();
//				reference = getReferenceTriangle(Arrays.asList(new Double[] {
//						getDistance(p2, p3),
//						getDistance(p1, p2),
//						getDistance(p1, p3)
//				}));
//				
//				Point relativePosNewCoord = getRelativePosition(reference, refDistances);
//				Point relativePosOriginalCoord = getOriginalCoordinatesPoint(p1, p2, relativePosNewCoord);
//				Point absolutePos = new Point(relativePosOriginalCoord.x + p1.x, relativePosOriginalCoord.y + p1.y);
//				points.add(absolutePos);
//			}
//		}
//		
//		return points;
//	}
//	
//	private static Point getOriginalCoordinatesPoint(Point p1, Point p2, Point newCoordPoint) {
//		Point originalCoordPoint;
//		
//		//Consider previous mirroring
//		boolean isQuadrant1 = (p2.x > 0D && p2.y > 0D);
//		boolean isQuadrant2 = (p2.x < 0D && p2.y > 0D);
//		boolean isQuadrant3 = (p2.x < 0D && p2.y < 0D);
//		boolean isQuadrant4 = (p2.x > 0D && p2.y < 0D);
//		
//		//
//		Double theta = getPhase(p1, p2);
//		Double alpha = getPhase(new Point(0D, 0D), newCoordPoint);
//		
//		Double h = getDistance(new Point(0D,0D), newCoordPoint);
//		
//		Double alphaTheta = alpha+theta;
//		
//		Double x = h*Math.cos(alphaTheta);
//		Double y = h*Math.sin(alphaTheta);
//		
//		if(theta > Math.PI/2 && theta < 3*Math.PI/2) { //was reflected on y
//			y *= -1;
//		}
//		
//		if((theta > -Math.PI/2 && theta < 0D) ||
//				(theta > Math.PI && theta < 3*Math.PI/2)) { //was reflected on x
//			x *= -1;
//		}
//		
//		originalCoordPoint = new Point(x,y);
//		return originalCoordPoint;
//	}
//	
//	private static Point getAveragePoint(List<Point> points) {
//		Point p = new Point(0D, 0D);
//		points.forEach(point -> {
//			p.x += point.x;
//			p.y += point.y;
//		});
//		p.x /= points.size();
//		p.y /= points.size();
//		return p.trimPointToPrecision();
//	}
//	
//	private static Double getPhase(Point p1, Point p2) {
//		Point trimP1 = p1.trimPointToPrecision();
//		Point trimP2 = p2.trimPointToPrecision();
//		
//		if(trimP2.y - trimP1.y == 0D) { //tan = 0
//			if(trimP1.x > trimP2.x) { // dx < 0
//				return Math.PI;
//			} else {
//				return 0D;
//			}
//		}
//		
//		if(trimP2.x - trimP1.x == 0D) { //tan = infinite
//			if(trimP1.y > trimP2.y) { // dy < 0
//				return 3*Math.PI/2;
//			} else {
//				return Math.PI/2;
//			}
//		}
//		
//		Double tanPhase = (p2.y - p1.y)/(p2.x - p1.x);
//		Double phase = Math.atan(tanPhase);
//		if((tanPhase > 0 && trimP1.y > trimP2.y) || //3rd quad
//				(tanPhase < 0 && trimP2.y > trimP1.y)) { //2nd quad
//			phase += Math.PI;
//		}
//		
//		return phase;
//	}
//	
//	private static Double getDistance(Point p1, Point p2) {
//		Double deltaX = Math.abs(p1.x-p2.x);
//		Double deltaY = Math.abs(p1.y-p2.y);
//		Double distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
//		return distance;
//	}
//	
//}