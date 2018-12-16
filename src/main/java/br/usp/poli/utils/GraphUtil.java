package br.usp.poli.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.usp.poli.exception.NoIntersectionPointsException;
import br.usp.poli.model.Simio;
import br.usp.poli.model.SimioDistance;
import br.usp.poli.service.SimioDistanceService;
import br.usp.poli.service.SimioService;

import static br.usp.poli.utils.ConstantsFile.ERROR_METERS;

@Component
public class GraphUtil {
	
	@Autowired
	SimioService simioService;
	@Autowired
	SimioDistanceService simioDistanceService;
	
	public List<Simio> createGraph(List<Simio> allSimios, List<SimioDistance> allDistances) {
		
		List<Simio> filteredSimios = new ArrayList<Simio>();
		
		if(allDistances.isEmpty()) {
			return filteredSimios;
		}
		
		Date timestamp = allDistances.get(0).getTimestamp();
		
		allSimios.forEach(simio -> {
			List<SimioDistance> distances = filterDistancesBySimio(allDistances, simio);
			
			List<Point> points = getPossiblePositions(distances);
			if(!points.isEmpty()) {
			//if(points.size() == 12) {
				Point averagePoint = getAveragePoint(points);
				Position position = Position.builder().point(averagePoint).timestamp(timestamp).build();
				simio.setPosition(position);
				filteredSimios.add(simio);
			}
		});
		
		return filteredSimios;
	}
	
	private static List<SimioDistance> filterDistancesBySimio(List<SimioDistance> allDistances, Simio simio) {
		List<SimioDistance> distances = new ArrayList<SimioDistance>();
		allDistances.forEach(d -> {
			if(d.getSimio().equals(simio)) {
				distances.add(d);
			}
		});
		return distances;
	}
	
	public static Point getTripleCircIntersection(List<Point> reference, List<Double> distances) throws NoIntersectionPointsException {
		Double a = reference.get(0).x;
		Double b = reference.get(0).y;
		Double c = reference.get(1).x;
		Double d = reference.get(1).y;
		Double e = reference.get(2).x;
		Double f = reference.get(2).y;
		
		Double d1 = distances.get(0);
		Double d2 = distances.get(1);
		Double d3 = distances.get(2);
		
		Double x;
		Double y;
		
		if(a - c != 0D) {
			
			Double alpha = (a - e)/(c - a);
			Double beta = 2 * ( ( f - b ) +  ( d - b ) * alpha );
			
			if(beta == 0D) {
				throw new NoIntersectionPointsException("No existing intersection point for provided values");
			}
			
			Double arg0 = Math.pow(e, 2) - Math.pow(a, 2);
			Double arg1 = Math.pow(f, 2) - Math.pow(b, 2);
			Double arg2 = Math.pow(c, 2) - Math.pow(a, 2);
			Double arg3 = Math.pow(d, 2) - Math.pow(b, 2);
			Double arg4 = Math.pow(d1, 2) - Math.pow(d2, 2);
			Double arg5 = Math.pow(d1, 2) - Math.pow(d3, 2);
			
			y = ( arg0 + arg1 + arg5 + alpha * ( arg2 + arg3 + arg4 ) ) / beta;
			x = ( 2 * y * ( b - d ) + arg2 + arg3 + arg4 ) / ( 2 * ( c - a ) );
			
		} else if (c - e != 0D) {
			
			Double alpha = (a - e)/(e - c);
			Double beta = 2 * ( ( f - b ) +  ( d - f ) * alpha );
			
			if(beta == 0D) {
				throw new NoIntersectionPointsException("No existing intersection point for provided values");
			}
			
			Double arg0 = Math.pow(e, 2) - Math.pow(a, 2);
			Double arg1 = Math.pow(f, 2) - Math.pow(b, 2);
			Double arg2 = Math.pow(e, 2) - Math.pow(c, 2);
			Double arg3 = Math.pow(f, 2) - Math.pow(d, 2);
			Double arg4 = Math.pow(d2, 2) - Math.pow(d3, 2);
			Double arg5 = Math.pow(d1, 2) - Math.pow(d3, 2);
			
			y = ( arg0 + arg1 + arg5 + alpha * ( arg2 + arg3 + arg4 ) ) / beta;
			x = ( 2 * y * ( d - f ) + arg2 + arg3 + arg4 ) / ( 2 * ( e - c ) );
			
		} else {
			throw new NoIntersectionPointsException("No existing intersection point for provided values");
		}
		
		Point px = new Point(x, y).trimPointToPrecision();
		return px;
	}
	
	public static List<Point> getDoubleCircIntersection(List<Point> reference, List<Double> distances) throws NoIntersectionPointsException {
		Double a = reference.get(0).x;
		Double b = reference.get(0).y;
		Double c = reference.get(1).x;
		Double d = reference.get(1).y;
		
		Double d1 = distances.get(0);
		Double d2 = distances.get(1);
		
		Double distance = getDistance(reference.get(0),reference.get(1));
		if(distance > (d1 + d2) ||
				d1 > (distance + d2) ||
				d2 > (distance + d1)) {
			throw new NoIntersectionPointsException("No existing intersection point for provided values");
		}
		
		Double arg0 = Math.pow(c, 2) - Math.pow(a, 2);
		Double arg1 = Math.pow(d, 2) - Math.pow(b, 2);
		Double arg2 = Math.pow(d1, 2) - Math.pow(d2, 2);
		
		Double alpha;
		Double beta;
		Double gama;
		Double delta;
		
		if(a - c != 0D) {
			
			alpha = (arg0 + arg1 + arg2)/(2*(c - a));
			beta = (b - d)/(c - a);
			
			gama = 2*(alpha*beta - a*beta - b);
			Double phi = Math.pow(a, 2) + Math.pow(b, 2) - Math.pow(d1, 2) + Math.pow(alpha, 2) - 2*a*alpha;
			
			delta = Math.pow(gama, 2) - 4*(1 - Math.pow(beta, 2))*phi;
			
			if(delta < 0) {
				throw new NoIntersectionPointsException("No existing intersection point for provided values");
			}
			
		} else if(d - b != 0D) {
			
			alpha = (arg0 + arg1 + arg2)/(2*(d - b));
			beta = (c - a)/(b - d);
			
			gama = 2*(alpha*beta - b*beta - a);
			Double phi = Math.pow(a, 2) + Math.pow(b, 2) - Math.pow(d1, 2) + Math.pow(alpha, 2) - 2*b*beta;
			
			delta = Math.pow(gama, 2) - 4*(1 + Math.pow(beta, 2))*phi;
			
			if(delta < 0) {
				throw new NoIntersectionPointsException("No existing intersection point for provided values");
			}
			
		} else {
			throw new NoIntersectionPointsException("No existing intersection point for provided values");
		}
		
		Double y1 = ((-1)*gama + Math.sqrt(delta))/(2*(1 + Math.pow(beta, 2)));
		Double y2 = ((-1)*gama - Math.sqrt(delta))/(2*(1 + Math.pow(beta, 2)));
		
		Double x1 = alpha + beta*y1;
		Double x2 = alpha + beta*y2;
		
		Point px1 = new Point(x1, y1).trimPointToPrecision();
		Point px2 = new Point(x2, y2).trimPointToPrecision();
		return Arrays.asList(new Point[] {px1, px2});
	}
	
	public static List<Point> getPossiblePositions(List<SimioDistance> distances) {
		List<Point> points = new ArrayList<Point>();
		
		Map<Integer, List<Circumference>> apCircumferences = new HashMap<Integer, List<Circumference>>();
		Integer apIndex = 0;
		for(SimioDistance d : distances) {
			Point center = d.getAccessPoint().getPosition();
			Double minusRadius = d.getDistance()-ERROR_METERS < 0 ? 0.5D : d.getDistance()-ERROR_METERS;
			List<Circumference> circumferences = Arrays.asList(new Circumference[] {
					Circumference.builder().center(center).radius(d.getDistance()+ERROR_METERS).build(), //external circle
					Circumference.builder().center(center).radius(minusRadius).build() //internal circle
			});
			
			apCircumferences.put(apIndex++, circumferences);
		}
		
		for(Integer ap1 = 0; ap1 < apCircumferences.keySet().size(); ap1++) {
			Circumference externalA = apCircumferences.get(ap1).get(0);
			
			for(Integer ap2 = 0; ap2 < apCircumferences.keySet().size(); ap2++) {
				if(ap1 == ap2) {
					continue;
				}
				
				Circumference internalB = apCircumferences.get(ap2).get(0);
				
				List<Point> refPoints = Arrays.asList(new Point[] {
						externalA.center, internalB.center
				});
						
				List<Double> refDistances = Arrays.asList(new Double[] {
						externalA.radius, internalB.radius
				});
				
				try {
					points.addAll(getDoubleCircIntersection(refPoints, refDistances));
				} catch(NoIntersectionPointsException e) {
					System.out.println(distances.get(0).getSimio().getName() + ": Circumferences with no point intersection: " + refPoints.get(0).toString() + 
							" r1=" + refDistances.get(0) + " & " + refPoints.get(1).toString() + " r2=" + refDistances.get(1));
				}
			}
		}
		
		return points;
	}
	
	public static Point getAveragePoint(List<Point> points) {
		Point p = new Point(0D, 0D);
		points.forEach(point -> {
			p.x += point.x;
			p.y += point.y;
		});
		p.x /= points.size();
		p.y /= points.size();
		return p.trimPointToPrecision();
	}
	
	private static Double getPhase(Point p1, Point p2) {
		Point trimP1 = p1.trimPointToPrecision();
		Point trimP2 = p2.trimPointToPrecision();
		
		if(trimP2.y - trimP1.y == 0D) { //tan = 0
			if(trimP1.x > trimP2.x) { // dx < 0
				return Math.PI;
			} else {
				return 0D;
			}
		}
		
		if(trimP2.x - trimP1.x == 0D) { //tan = infinite
			if(trimP1.y > trimP2.y) { // dy < 0
				return 3*Math.PI/2;
			} else {
				return Math.PI/2;
			}
		}
		
		Double tanPhase = (p2.y - p1.y)/(p2.x - p1.x);
		Double phase = Math.atan(tanPhase);
		if((tanPhase > 0 && trimP1.y > trimP2.y) || //3rd quad
				(tanPhase < 0 && trimP2.y > trimP1.y)) { //2nd quad
			phase += Math.PI;
		}
		
		return phase;
	}
	
	public static Double getDistance(Point p1, Point p2) {
		Double deltaX = Math.abs(p1.x-p2.x);
		Double deltaY = Math.abs(p1.y-p2.y);
		Double distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
		return distance;
	}
	
}