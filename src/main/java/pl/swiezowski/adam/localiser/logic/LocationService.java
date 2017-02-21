package pl.swiezowski.adam.localiser.logic;

import java.util.List;

import pl.swiezowski.adam.localiser.entities.Location;

public class LocationService {

	public static double getDistance(List<Location> locations) {
		double distance = 0.0;
		for (int i = 0; i < locations.size() - 1; i++) {
			distance += locations.get(i).distance(locations.get(i + 1));
		}
		return distance;
	}

}
