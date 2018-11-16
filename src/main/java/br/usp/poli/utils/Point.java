package br.usp.poli.utils;

public class Point {

	public Double x;
	public Double y;
	
	public Point() {

	}
	
	public Point(Double x, Double y) {
		this.x = x;
		this.y = y;
	}

	public Point trimPointToPrecision() {
		return new Point (round(x, 2), round(y, 2));
	}
	
	private Double round(Double value, int digits) {
		value = value + 0D;
		
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
	    
	    if(isNegative) { rounded = -rounded; }
	    
	    return rounded;
	}
}