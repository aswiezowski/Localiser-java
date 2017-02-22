package pl.swiezowski.adam.localiser.logic;

import java.util.List;

import com.google.common.collect.Iterables;

import pl.swiezowski.adam.localiser.entities.Location;

public class DistanceCalculator {

	public double getDistance(List<Location> locations) {
		double distance = 0.0;
		for (int i = 0; i < locations.size() - 1; i++) {
			distance += locations.get(i).distance(locations.get(i + 1));
		}
		return distance;
	}

	public double getDistanceWithCycle(List<Location> locations) {
		if (locations.isEmpty()) {
			return 0.0;
		}
		
		double distance = getDistance(locations);
		
		Location firstLocation = locations.iterator().next();
		Location lastLocation = Iterables.getLast(locations);
		return distance + firstLocation.distance(lastLocation);
	}

}
