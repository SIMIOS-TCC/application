package br.usp.poli.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
	
	public List<Simio> createGraph(List<Simio> allSimios, List<SimioDistance> allDistances) {
		
		if(allDistances.isEmpty()) {
			return allSimios;
		}
		
		Date timestamp = allDistances.get(0).getTimestamp();
		allSimios.forEach(simio -> {
			List<SimioDistance> distances = filterDistancesBySimio(allDistances, simio);
			List<Point> points = new ArrayList<Point>();
			try {
				points = getPossiblePositions(distances);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			
			Point averagePoint = getAveragePoint(points);
			Position position = Position.builder().point(averagePoint).timestamp(timestamp).build();
			simio.setPosition(position);
		});
		
		return allSimios;
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
	
	public static Point getAbsolutePosition(List<Point> reference, List<Double> distances) throws Exception {
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
				throw new Exception("No existing intersection point for provided values");
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
				throw new Exception("No existing intersection point for provided values");
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
			throw new Exception("No existing intersection point for provided values");
		}
		
		Point px = new Point(x, y).trimPointToPrecision();
		return px;
	}
	
	public static List<Point> getPossiblePositions(List<SimioDistance> distances) throws Exception {
		List<Point> points = new ArrayList<Point>();
		
		List<Double> refDistances;
		for(int i = 0; i < distances.size() - 2; i++) {
			for(int j = i + 1; j < distances.size() - 1; j++) {
				Double d1 = distances.get(i).getDistance();
				Double d2 = distances.get(j).getDistance();
				Double d3 = distances.get(j+1).getDistance();
				refDistances = Arrays.asList(new Double[] {d1, d2, d3});
				
				Point p1 = distances.get(i).getAccessPoint().getPosition();
				Point p2 = distances.get(j).getAccessPoint().getPosition();
				Point p3 = distances.get(j+1).getAccessPoint().getPosition();
				
				Point point = getAbsolutePosition(Arrays.asList(new Point[] {p1, p2, p3}), refDistances);
				points.add(point);
			}
		}
		
		return points;
	}
	
	private static Point getAveragePoint(List<Point> points) {
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