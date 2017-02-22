package pl.swiezowski.adam.localiser.logic;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import pl.swiezowski.adam.localiser.entities.Location;

public class TravellingSalesmanSolverTest {

	private TravellingSalesmanSolver solver;
	private DistanceCalculator distanceCalculator = new DistanceCalculator();
	
	@Before
	public void init() {
		solver = new TravellingSalesmanSolver();
	}
	
	@Test
	public void shouldReturnEqualDistanceWhenPermutatingLocations() {
		Location loc1 = new Location(50.070892, 19.938521, "krakow");
		Location loc2 = new Location(50.254658, 19.024302, "katowice");
		Location loc3 = new Location(51.755600, 19.450057, "lodz");
		Location loc4 = new Location(52.399548, 16.920722, "poznan");
		Location loc5 = new Location(52.220967, 21.015730, "warszawa");

		List<Location> path = solver.findShortestPath(loc3, Lists.newArrayList(loc1, loc2, loc3, loc4, loc5));
		List<Location> path2 = solver.findShortestPath(loc1, Lists.newArrayList(loc5, loc2, loc1, loc4, loc3));
		double path1Distance = distanceCalculator.getDistanceWithCycle(path);
		double path2Distance = distanceCalculator.getDistanceWithCycle(path2);
		
		Assert.assertEquals(path1Distance, path2Distance, 0.0001);
	}

	@Test
	public void shouldReturnLessOrEqualPathDistance() {
		Location loc1 = new Location(50.061424, 19.937340, "Main Square");
		Location loc2 = new Location(50.054877, 19.893238, "Koscioszko Mound");
		Location loc3 = new Location(50.054877, 19.893238, "Pilsudzki Mound");
		Location loc4 = new Location(50.037975, 19.958465, "Krak Mound");
		Location loc5 = new Location(50.054877, 19.893238, "Wanda Mound");
		ArrayList<Location> initialLocations = Lists.newArrayList(loc1, loc2, loc3, loc4, loc5);

		List<Location> path = solver.findShortestPath(loc3, initialLocations);
		double pathDistance = distanceCalculator.getDistanceWithCycle(path);
		double initialPathdistance = distanceCalculator.getDistanceWithCycle(initialLocations);
		
		Assert.assertTrue(pathDistance <= initialPathdistance);
	}

	@Test
	public void shouldReturnSamePathWhenLessThanFourLocations() {
		Location loc1 = new Location(52.249592, 21.012296, "Main Square");
		Location loc2 = new Location(52.247608, 21.014779, "King's Castle");
		Location loc3 = new Location(52.242998, 21.016722, "Presidential Palace");
		ArrayList<Location> initialLocations = Lists.newArrayList(loc1, loc2, loc3);

		List<Location> path = solver.findShortestPath(loc3, initialLocations);

		Assert.assertEquals(initialLocations, path);
	}
}
