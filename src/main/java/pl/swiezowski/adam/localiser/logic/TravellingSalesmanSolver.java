package pl.swiezowski.adam.localiser.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import pl.swiezowski.adam.localiser.entities.Location;

public class TravellingSalesmanSolver {

	private double distances[][];
	private List<Location> locations;
	private Comparator<List<Integer>> pathComparator = new Comparator<List<Integer>>() {
		@Override
		public int compare(List<Integer> path1, List<Integer> path2) {
			double distance1 = getDistanceWithCycle(path1);
			double distance2 = getDistanceWithCycle(path2);
			return Double.compare(distance1, distance2);
		}
	};

	public List<Location> findShortestPath(Location startLocation, Collection<Location> initialLocations) {
		if (initialLocations.size() < 4) {
			return new ArrayList<>(initialLocations);
		}
		PriorityQueue<List<Integer>> queue = new PriorityQueue<>(pathComparator);
		initDistances(initialLocations);
		List<Integer> solution = new ArrayList<>();
		solution.add(0);
		List<Integer> bestPath = getInititalSolution();
		double bestDistance = getDistanceWithCycle(bestPath);
		queue.add(solution);
		while (!queue.isEmpty()) {
			solution = queue.poll();
			if (getLowerBound(solution) < bestDistance) {
				List<List<Integer>> children = getBranches(solution);
				for (List<Integer> child : children) {
					if (child.size() == locations.size()) {
						double distanceWithCycle = getDistanceWithCycle(child);
						if (distanceWithCycle < bestDistance) {
							bestDistance = distanceWithCycle;
							bestPath = child;
						}
					} else {
						double bound = getLowerBound(child);
						if (bound < bestDistance) {
							queue.add(child);
						}
					}
				}
			}
		}
		return getResults(startLocation, bestPath);
	}

	private void initDistances(Collection<Location> initLocations) {
		distances = new double[initLocations.size()][initLocations.size()];
		locations = new ArrayList<>(initLocations);
		for (int i = 0; i < locations.size(); i++) {
			for (int j = 0; j < locations.size(); j++) {
				if (i == j) {
					distances[i][j] = Double.MAX_VALUE;
				} else {
					distances[i][j] = locations.get(i).distance(locations.get(j));
				}
			}
		}
	}

	private List<Integer> getInititalSolution() {
		List<Integer> initialBest = new ArrayList<>();
		for (int node = 0; node < distances.length; node++) {
			initialBest.add(node);
		}
		return initialBest;
	}

	private double getLowerBound(List<Integer> partialSolution) {
		double minDistance = getDistance(partialSolution);
		for (int nodeFrom = 0; nodeFrom < distances.length; nodeFrom++) {
			if (!partialSolution.contains(nodeFrom) || isLastInList(nodeFrom, partialSolution)) {
				double min = Double.MAX_VALUE;
				for (int nodeTo = 0; nodeTo < distances.length; nodeTo++) {
					if (!partialSolution.contains(nodeTo)) {
						min = Math.min(min, getDistance(nodeFrom, nodeTo));
					}
				}
				minDistance += min;
			}
		}
		return minDistance;
	}

	private double getDistance(List<Integer> path) {
		double distance = 0.0;
		for (int i = 0; i < path.size() - 1; i++) {
			distance += getDistance(path.get(i), path.get(i + 1));
		}
		return distance;
	}

	private boolean isLastInList(int item, List<Integer> list) {
		return list.indexOf(item) + 1 == list.size();
	}

	private List<List<Integer>> getBranches(List<Integer> partialSolution) {
		List<List<Integer>> branches = new ArrayList<>();
		for (int node = 0; node < locations.size(); node++) {
			if (!partialSolution.contains(node)) {
				List<Integer> branch = new ArrayList<>(partialSolution);
				branch.add(node);
				if (branch.size() == locations.size() - 1) {
					branch.addAll(getMissingNodes(branch));
				}
				branches.add(branch);
			}
		}
		return branches;
	}

	private List<Integer> getMissingNodes(List<Integer> branch) {
		List<Integer> missingNode = new ArrayList<>();
		for (int node = 0; node < locations.size(); node++) {
			if (!branch.contains(node)) {
				missingNode.add(node);
			}
		}
		return missingNode;
	}

	private List<Location> getResults(Location startLocation, List<Integer> solution) {
		int startLocationId = locations.indexOf(startLocation);
		int startNode = solution.indexOf(startLocationId);
		List<Location> result = new ArrayList<>();
		for (int node = startNode; node < solution.size(); node++) {
			result.add(locations.get(solution.get(node)));
		}
		for (int node = 0; node < startNode; node++) {
			result.add(locations.get(solution.get(node)));
		}
		return result;
	}

	private double getDistance(int startNode, int endNode) {
		return distances[startNode][endNode];
	}

	private double getDistanceWithCycle(List<Integer> path) {
		double distance = 0.0;
		for (int i = 0; i < path.size() - 1; i++) {
			distance += getDistance(path.get(i), path.get(i + 1));
		}
		distance += getDistance(path.get(path.size() - 1), path.get(0));
		return distance;
	}
}
