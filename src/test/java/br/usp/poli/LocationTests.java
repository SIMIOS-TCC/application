package br.usp.poli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.usp.poli.extended.AssertExtended;
import br.usp.poli.model.AccessPoint;
import br.usp.poli.model.SimioDistance;
import br.usp.poli.utils.GraphUtil;
import br.usp.poli.utils.Point;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SimiosApplication.class)
@WebAppConfiguration
public class LocationTests {
	
	private static final Double sqrt200 = 10*Math.sqrt(2);
	private static final Double sqrt500 = 10*Math.sqrt(5);
	
	private List<Point> refListExpected;
	
	@Before
	public void loadContext() {
		//Mocka pontos de referencia fixos
		Point p1 = new Point(0D,0D);
		Point p2 = new Point(10D,0D);
		Point p3 = new Point(10D,10D);
		
		refListExpected = new ArrayList<Point>();
		refListExpected.add(p1);
		refListExpected.add(p2);
		refListExpected.add(p3);	
	}
	
	//Double Circ Intersection
	@Test
	public void getDoubleCircIntersectionTest_Quadrant2_3() throws Exception {
		
		List<Point> pxsExpected = Arrays.asList(new Point[] {
				new Point(-5D,5D),
				new Point(-5D,-5D)
		});
		
		//{ r1, r2 }
		Double[] distancesArray = new Double[] {sqrt200/2, sqrt200/2};
		List<Double> distancesList = Arrays.asList(distancesArray);
		
		List<Point> reference = Arrays.asList(new Point[] {
				new Point(0D,0D),
				new Point(-10D,0D)
		});
		
		List<Point> pxsActual = GraphUtil.getDoubleCircIntersection(reference, distancesList);
		
		AssertExtended.assertPointListEquals(pxsExpected, pxsActual);
	}
	
	@Test
	public void getDoubleCircIntersectionTest_Quadrant1_4() throws Exception {
		
		List<Point> pxsExpected = Arrays.asList(new Point[] {
				new Point(5D,5D),
				new Point(5D,-5D)
		});
		
		//{ r1, r2 }
		Double[] distancesArray = new Double[] {sqrt200/2, sqrt200/2};
		List<Double> distancesList = Arrays.asList(distancesArray);
		
		List<Point> pxsActual = GraphUtil.getDoubleCircIntersection(refListExpected, distancesList);
		
		AssertExtended.assertPointListEquals(pxsExpected, pxsActual);
	}
	
	//Absolute Position
	@Test
	public void getTripleCircIntersectionTest_Quadrant1() throws Exception {
		
		Point pxExpected = new Point(0D,10D);

		//{ r1, r2, r3 }
		Double[] distancesArray = new Double[] {10D, sqrt200, 10D};
		List<Double> distancesList = Arrays.asList(distancesArray);
		
		Point pxActual = GraphUtil.getTripleCircIntersection(refListExpected, distancesList);
		
		AssertExtended.assertPointEquals(pxExpected, pxActual);
	}
	
	@Test
	public void getTripleCircIntersectionTest_Quadrant2() throws Exception {
		
		Point pxExpected = new Point(-10D,10D);

		//{ r1, r2, r3 }
		Double[] distancesArray = new Double[] {sqrt200, sqrt500, 20D};
		List<Double> distancesList = Arrays.asList(distancesArray);
		
		Point pxActual = GraphUtil.getTripleCircIntersection(refListExpected, distancesList);
		
		AssertExtended.assertPointEquals(pxExpected, pxActual);
	}
	
	@Test
	public void getTripleCircIntersectionTest_Quadrant3() throws Exception {
		
		Point pxExpected = new Point(0D,-10D);

		//{ r1, r2, r3 }
		Double[] distancesArray = new Double[] {10D, sqrt200, sqrt500};
		List<Double> distancesList = Arrays.asList(distancesArray);
		
		Point pxActual = GraphUtil.getTripleCircIntersection(refListExpected, distancesList);
		
		AssertExtended.assertPointEquals(pxExpected, pxActual);
	}
	
	@Test
	public void getTripleCircIntersectionTest_Origin() throws Exception {
		
		Point pxExpected = new Point(0D,0D);

		//{ r1, r2, r3 }
		Double[] distancesArray = new Double[] {sqrt200, sqrt200, sqrt200};
		List<Double> distancesList = Arrays.asList(distancesArray);
		
		Point p1 = new Point(10D,10D);
		Point p2 = new Point(-10D,-10D);
		Point p3 = new Point(10D,-10D);
		Point pxActual = GraphUtil.getTripleCircIntersection(Arrays.asList(new Point[] {p1,p2,p3}), distancesList);
		
		AssertExtended.assertPointEquals(pxExpected, pxActual);
	}
	
	@Test
	public void getTripleCircIntersectionTest_Obtuse() throws Exception {
		
		Point pxExpected = new Point(-10D,10D);

		//{ r1, r2, r3 }
		Double[] distancesArray = new Double[] {20D, 10D, sqrt200};
		List<Double> distancesList = Arrays.asList(distancesArray);
		
		Point p1 = new Point(10D,10D);
		Point p2 = new Point(0D,10D);
		Point p3 = new Point(0D,0D);
		Point pxActual = GraphUtil.getTripleCircIntersection(Arrays.asList(new Point[] {p1,p2,p3}), distancesList);
		
		AssertExtended.assertPointEquals(pxExpected, pxActual);
	}
	
	@Test
	public void getTripleCircIntersectionTest_OnAP() throws Exception {
		
		Point pxExpected = new Point(10D,10D);

		//{ r1, r2, r3 }
		Double[] distancesArray = new Double[] {0D, 10D, sqrt200};
		List<Double> distancesList = Arrays.asList(distancesArray);
		
		Point p1 = new Point(10D,10D);
		Point p2 = new Point(0D,10D);
		Point p3 = new Point(0D,0D);
		Point pxActual = GraphUtil.getTripleCircIntersection(Arrays.asList(new Point[] {p1,p2,p3}), distancesList);
		
		AssertExtended.assertPointEquals(pxExpected, pxActual);
	}
	
	//Possible Positions
	@Test
	public void getPossiblePositionsTest_Quadrant1() throws Exception {
		
		List<Point> pxsExpected = Arrays.asList(new Point[] {new Point(5D,5D), new Point(5D,5D), new Point(5D,5D)});

		//{ d1, d2, d3, d4 }
		AccessPoint ap = AccessPoint.builder().position(new Point(0D,0D)).build();
		SimioDistance d1 = SimioDistance.builder()
				.distance(sqrt200)
				.accessPoint(ap)
				.build();
		
		ap = AccessPoint.builder().position(new Point(10D,0D)).build();
		SimioDistance d2 = SimioDistance.builder()
				.distance(sqrt200)
				.accessPoint(ap)
				.build();
		
		ap = AccessPoint.builder().position(new Point(10D,10D)).build();
		SimioDistance d3 = SimioDistance.builder()
				.distance(sqrt200)
				.accessPoint(ap)
				.build();
		
		ap = AccessPoint.builder().position(new Point(0D,10D)).build();
		SimioDistance d4 = SimioDistance.builder()
				.distance(sqrt200)
				.accessPoint(ap)
				.build();
		
		List<Point> pxsActual = GraphUtil.getPossiblePositions(Arrays.asList(new SimioDistance[] {d1, d2, d3, d4}));
		
		AssertExtended.assertPointListEquals(pxsExpected, pxsActual);
	}
	
	@Test
	public void getPossiblePositionsTest_Quadrant2() throws Exception {
		
		List<Point> pxsExpected = Arrays.asList(new Point[] {new Point(-5D,5D), new Point(-5D,5D), new Point(-5D,5D)});

		//{ d1, d2, d3, d4 }
		AccessPoint ap = AccessPoint.builder().position(new Point(0D,0D)).build();
		SimioDistance d1 = SimioDistance.builder()
				.distance(sqrt200)
				.accessPoint(ap)
				.build();
		
		ap = AccessPoint.builder().position(new Point(-10D,0D)).build();
		SimioDistance d2 = SimioDistance.builder()
				.distance(sqrt200)
				.accessPoint(ap)
				.build();
		
		ap = AccessPoint.builder().position(new Point(-10D,10D)).build();
		SimioDistance d3 = SimioDistance.builder()
				.distance(sqrt200)
				.accessPoint(ap)
				.build();
		
		ap = AccessPoint.builder().position(new Point(0D,10D)).build();
		SimioDistance d4 = SimioDistance.builder()
				.distance(sqrt200)
				.accessPoint(ap)
				.build();
		
		List<Point> pxsActual = GraphUtil.getPossiblePositions(Arrays.asList(new SimioDistance[] {d1, d2, d3, d4}));
		
		AssertExtended.assertPointListEquals(pxsExpected, pxsActual);
	}
	
	@Test
	public void getPossiblePositionsTest_Quadrant3() throws Exception {
		
		List<Point> pxsExpected = Arrays.asList(new Point[] {new Point(-5D,-5D), new Point(-5D,-5D), new Point(-5D,-5D)});

		//{ d1, d2, d3, d4 }
		AccessPoint ap = AccessPoint.builder().position(new Point(0D,0D)).build();
		SimioDistance d1 = SimioDistance.builder()
				.distance(sqrt200)
				.accessPoint(ap)
				.build();
		
		ap = AccessPoint.builder().position(new Point(-10D,0D)).build();
		SimioDistance d2 = SimioDistance.builder()
				.distance(sqrt200)
				.accessPoint(ap)
				.build();
		
		ap = AccessPoint.builder().position(new Point(-10D,-10D)).build();
		SimioDistance d3 = SimioDistance.builder()
				.distance(sqrt200)
				.accessPoint(ap)
				.build();
		
		ap = AccessPoint.builder().position(new Point(0D,-10D)).build();
		SimioDistance d4 = SimioDistance.builder()
				.distance(sqrt200)
				.accessPoint(ap)
				.build();
		
		List<Point> pxsActual = GraphUtil.getPossiblePositions(Arrays.asList(new SimioDistance[] {d1, d2, d3, d4}));
		
		AssertExtended.assertPointListEquals(pxsExpected, pxsActual);
	}
	
	//Create Graph
	
}
