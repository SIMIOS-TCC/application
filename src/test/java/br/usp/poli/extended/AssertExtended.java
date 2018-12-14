package br.usp.poli.extended;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import static br.usp.poli.utils.ConstantsFile.ERROR_METERS;

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
	
	public static void assertPointIsPrecise(Point expected, Point actual) {
		actual = actual.trimPointToPrecision();
		boolean isXPrecise = (expected.x+ERROR_METERS > actual.x) && (expected.x-ERROR_METERS < actual.x);
		boolean isYPrecise = (expected.y+ERROR_METERS > actual.y) && (expected.y-ERROR_METERS < actual.y);
		assertTrue("X is not precise", isXPrecise);
		assertTrue("Y is not precise", isYPrecise);
	}
	
	public static void assertPointIsNotPrecise(Point expected, Point actual) {
		actual = actual.trimPointToPrecision();
		boolean isXPrecise = (expected.x+ERROR_METERS > actual.x) && (expected.x-ERROR_METERS < actual.x);
		boolean isYPrecise = (expected.y+ERROR_METERS > actual.y) && (expected.y-ERROR_METERS < actual.y);
		assertTrue("X is precise", !isXPrecise);
		assertTrue("Y is precise", !isYPrecise);
	}
}	