package pl.swiezowski.adam.localiser.entities;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
public class Route {

	private List<Node> nodes;

	public double getDistance() {
		return nodes.stream().map(Node::getDistance).reduce((acc, val) -> acc + val).get();
	}

	@Data
	@Builder
	public static class Node {
		private Localisation startNode;
		private Localisation endNode;
		private double distance;
	}
}
