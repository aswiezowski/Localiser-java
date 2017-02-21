package pl.swiezowski.adam.localiser.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import pl.swiezowski.adam.localiser.entities.Location;

public class TravellingSalesmanSolver {

	double distances[][];
	List<Location> locations;

	public List<Location> findShortestPath(Location startLocalisation, Collection<Location> initialLocations) {
		PriorityQueue<List<Integer>> queue = new PriorityQueue<>(new Comparator<List<Integer>>() {
			@Override
			public int compare(List<Integer> o1, List<Integer> o2) {
				double distance1 = valueWithCycle(o1);
				double distance2 = valueWithCycle(o2);
				return Double.compare(distance1, distance2);
			}
		});
		initDistances(initialLocations);
		List<Integer> v = new ArrayList<>();
		v.add(0);
		List<Integer> bestItems = getInitital();
		double best = valueWithCycle(bestItems);
		queue.add(v);
		printTable();
		while (!queue.isEmpty()) {
			v = queue.poll();
			if (bound(v) < best) {
				List<List<Integer>> children = getChildren(v);
				for (List<Integer> child : children) {
					double valueWithCycle = valueWithCycle(child);
					if (valueWithCycle < best && child.size() == locations.size()) {
						best = valueWithCycle;
						bestItems = child;
						System.out.println("best: " + best + " child:" + child);
					}
					double bound = bound(child);
					if (bound < best) {
						queue.add(child);
					} else {
						System.out.println("best: " + best + " bound: " + bound + " child:" + child);
					}
				}
			}
		}

		System.out.println("Best: " + bestItems);
		return getResults(startLocalisation, bestItems);
	}

	private List<Location> getResults(Location startLocalisation, List<Integer> bestItems) {
		int startLocalisationId = locations.indexOf(startLocalisation);
		int start = bestItems.indexOf(startLocalisationId);
		List<Location> result = new ArrayList<>();
		for (int i = start; i < bestItems.size(); i++) {
			result.add(locations.get(bestItems.get(i)));
		}
		for (int i = 0; i < start; i++) {
			result.add(locations.get(bestItems.get(i)));
		}
		return result;
	}

	private List<Integer> getInitital() {
		List<Integer> initialBest = new ArrayList<>();
		for (int i = 0; i < distances.length; i++) {
			initialBest.add(i);
		}
		return initialBest;
	}

	private List<List<Integer>> getChildren(List<Integer> v) {
		List<List<Integer>> children = new ArrayList<>();
		for (int i = 0; i < locations.size(); i++) {
			if (!v.contains(i)) {
				List<Integer> child = new ArrayList<>(v);
				child.add(i);
				if (child.size() == locations.size() - 1) {
					child.addAll(getMissingNumbers(child));
				}
				children.add(child);
			}
		}
		return children;
	}

	List<Integer> getMissingNumbers(List<Integer> list) {
		List<Integer> missingNumbers = new ArrayList<>();
		for (int i = 0; i < locations.size(); i++) {
			if (!list.contains(i)) {
				missingNumbers.add(i);
			}
		}
		return missingNumbers;
	}

	double getDistance(int x, int y) {
		return distances[x][y];
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

	private double bound(List<Integer> v) {
		double minPath = getPathLengths(v);
		for (int node = 0; node < distances.length; node++) {
			if (!v.contains(node) || isLastInList(v, node)) {
				double min = Double.MAX_VALUE;
				for (int j = 0; j < distances.length; j++) {
					if (!v.contains(j)) {
						min = Math.min(min, getDistance(node, j));
					}
				}
				minPath += min;
			}
		}
		return minPath;
	}

	private boolean isLastInList(List<Integer> v, int node) {
		return v.indexOf(node) + 1 == v.size();
	}

	private double getPathLengths(List<Integer> localisations) {
		double distance1 = 0.0;
		for (int i = 0; i < localisations.size() - 1; i++) {
			distance1 += getDistance(localisations.get(i), localisations.get(i + 1));
		}
		return distance1;
	}

	private double valueWithCycle(List<Integer> localisations) {
		double distance1 = 0.0;
		for (int i = 0; i < localisations.size() - 1; i++) {
			distance1 += getDistance(localisations.get(i), localisations.get(i + 1));
		}
		distance1 += getDistance(localisations.get(localisations.size() - 1), localisations.get(0));
		return distance1;
	}

	void printTable() {
		System.out.println("----------------------------");
		for (int i = 0; i < distances.length; i++) {
			System.out.print(i + "  |");
			for (int j = 0; j < distances[0].length; j++) {
				System.out.print(distances[i][j] + "\t|");
			}
			System.out.println();
		}
		System.out.println("----------------------------");
	}
}
