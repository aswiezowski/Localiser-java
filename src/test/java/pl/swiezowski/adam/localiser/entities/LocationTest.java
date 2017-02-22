package pl.swiezowski.adam.localiser.entities;

import org.junit.Assert;
import org.junit.Test;

public class LocationTest {
	
	@Test
	public void shouldReturnValidDistanceBetweenTwoLocations() {
		Location loc1 = new Location(52.241929, 20.993809, "");
		Location loc2 = new Location(52.244274, 21.000941, "");
		
		double distance = loc1.distance(loc2);
		
		Assert.assertEquals(551.28, distance, 1);
	}
}
