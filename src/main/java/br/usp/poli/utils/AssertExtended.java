package br.usp.poli.utils;

import java.util.List;

import org.junit.Assert;

public class AssertExtended extends Assert{
	
	public static void assertPointListEquals(List<Point> expecteds, List<Point> actuals) {
		
		assertEquals(expecteds.size(), actuals.size());
		
		for(int i = 0; i < expecteds.size(); i++) {
			Point pExpected = expecteds.get(i);
			Point pActual = actuals.get(i);
			
			assertPointEquals(pExpected, pActual);
		}
	}

	public static void assertPointEquals(Point expected, Point actual) {
		
		assertEquals(expected.x, actual.x);
		assertEquals(expected.y, actual.y);
		
	}
}	