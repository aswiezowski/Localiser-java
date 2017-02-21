package pl.swiezowski.adam.localiser.logic;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;

import pl.swiezowski.adam.localiser.entities.Location;

public class TravellingSalesmanSolverTest {

	@Test
	public void testFindingShortestPath() {
		Location loc1 = new Location(0l, 50.070892, 19.938521, "krakow");
		Location loc2 = new Location(1l, 50.254658, 19.024302, "katowice");
		Location loc3 = new Location(2l, 51.755600, 19.450057, "lodz");
		Location loc4 = new Location(3l, 52.399548, 16.920722, "poznan");
		Location loc5 = new Location(4l, 52.220967, 21.015730, "warszawa");

		TravellingSalesmanSolver solver = new TravellingSalesmanSolver();
		List<Location> path = solver.findShortestPath(loc3, Lists.newArrayList(loc1, loc2, loc3, loc4, loc5));
		List<Location> path2 = solver.findShortestPath(loc1, Lists.newArrayList(loc5, loc2, loc1, loc4, loc3));

		Assert.assertTrue(Math.abs(getDistance(path) - getDistance(path2)) < 0.0001);
	}

	double getDistance(List<Location> locations) {
		double distance = 0.0;
		for (int i = 0; i < locations.size() - 1; i++) {
			distance += locations.get(i).distance(locations.get(i + 1));
		}
		distance += locations.get(0).distance(locations.get(locations.size()
				- 1));
		return distance;
	}
}
