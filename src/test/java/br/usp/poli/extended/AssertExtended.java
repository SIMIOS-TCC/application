package br.usp.poli.extended;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import br.usp.poli.utils.Point;

public class AssertExtended {
	
	public static void assertMappingEquals(Map<Long, Point> mapExpected, Map<Long, Point> mapActual) {
		assertEquals(mapExpected.keySet(), mapActual.keySet());
		for(Long id : mapExpected.keySet()) {
			assertPointEquals(mapExpected.get(id), mapActual.get(id));
		}
	}
	
	public static void assertPointListEquals(List<Point> expecteds, List<Point> actuals) {
		
		assertEquals(expecteds.size(), actuals.size());
		
		for(int i = 0; i < expecteds.size(); i++) {
			Point pExpected = expecteds.get(i);
			Point pActual = actuals.get(i);
			
			assertPointEquals(pExpected, pActual);
		}
	}

	public static void assertPointEquals(Point expected, Point actual) {
		actual = actual.trimPointToPrecision();
		assertEquals(expected.x, actual.x);
		assertEquals(expected.y, actual.y);
		
	}
}	