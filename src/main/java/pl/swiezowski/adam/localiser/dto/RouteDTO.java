package pl.swiezowski.adam.localiser.dto;

import java.util.List;

import lombok.Builder;
import lombok.Value;
import pl.swiezowski.adam.localiser.entities.Location;

@Builder
@Value
public class RouteDTO {

	private List<Location> locations;
	private Double distance;

}
