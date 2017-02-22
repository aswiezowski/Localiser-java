package pl.swiezowski.adam.localiser.logic;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import pl.swiezowski.adam.localiser.entities.Location;

public class DistanceCalculatorTest {
	
	private DistanceCalculator distanceCalculator;
	
	@Before
	public void init() {
		distanceCalculator = new DistanceCalculator();
	}
	
	@Test
	public void shouldReturnValidDistanceForTwoLocations() {
		Location loc1 = Mockito.mock(Location.class);
		Location loc2 = Mockito.mock(Location.class);
		Mockito.when(loc1.distance(loc2)).thenReturn(10.0);
		
		double distance = distanceCalculator.getDistance(Arrays.asList(loc1, loc2));
		
		Assert.assertEquals(10.0, distance, 0.00001);
	}
	
	@Test
	public void shouldReturnValidDistanceWithCycleForTwoLocations() {
		Location loc1 = Mockito.mock(Location.class);
		Location loc2 = Mockito.mock(Location.class);
		Mockito.when(loc1.distance(loc2)).thenReturn(10.0);
		Mockito.when(loc2.distance(loc1)).thenReturn(10.0);
		
		double distance = distanceCalculator.getDistanceWithCycle(Arrays.asList(loc1, loc2));
		
		Assert.assertEquals(20.0, distance, 0.00001);
	}
	
	@Test
	public void shouldReturnZeroWhenDistanceWithCycleForNoLocation() {
		double distance = distanceCalculator.getDistanceWithCycle(Collections.emptyList());
		
		Assert.assertEquals(0.0, distance, 0.00001);
	}
}
