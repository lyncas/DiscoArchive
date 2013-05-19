package lejos.robotics.pathfinding;

import java.lejosutil.Collection;
import lejos.robotics.navigation.Waypoint;

/*
 * WARNING: THIS CLASS IS SHARED BETWEEN THE classes AND pccomms PROJECTS.
 * DO NOT EDIT THE VERSION IN pccomms AS IT WILL BE OVERWRITTEN WHEN THE PROJECT IS BUILT.
 */

/**
 * An interface for defining generic node search algorithms.
 * NOTE: Implementations of this interface should override Object.toString() with the name of the algorithm. 
 * e.g. "A*", "Dijkstra", "Best-First", "D* Lite"
 * @author BB
 * @see java.lang.Object#toString()
 */
public interface SearchAlgorithm {
	
	/**
	 * Method accepts a start node and a goal node, and returns a path consisting of a collection of waypoints which
	 * includes the startNode coordinates as the first waypoint, and the goal node coordinates as the final waypoint.
	 * Note: The startNode must be connected with other nodes (neighbors) that eventually connect to the goalNode.
	 * @param startNode
	 * @param goalNode
	 * @return A collection of waypoints. Returns null if it fails to find a path.
	 */
	public Path findPath(Node startNode, Node goalNode);
}
