package br.usp.poli.utils;

public class TestsUtil {
	
	private TestsUtil() {
		super();
	}
	
	public static Point trimPointToPrecision(Point p) {

		p.x = round(p.x, 2);
		p.y = round(p.y, 2);
		
		return p;
	}
	
	public static Double round(Double value, int digits) {
		boolean isNegative = false;
		Double rounded;
		
		if(value < 0) {
			isNegative = true;
			rounded = -value;
		} else { rounded = value; }

		Double precisao = (Math.pow(10, digits));
		rounded *= precisao;
	    
	    if (rounded%precisao >= 0.5) {
	    	//Arredonda para cima
	    	rounded = Math.ceil(rounded);           
	    } else {
	    	//Arredonda para baixo
	    	rounded = Math.floor(rounded);
	    }
	    
	    rounded /= (Math.pow(10, digits));
	    
	    if(isNegative && rounded != 0) { rounded = -rounded; }
	    
	    return rounded;
	}

}	