package br.usp.poli.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class GraphUtil {

	private GraphUtil() {
		super();
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
	
	
}