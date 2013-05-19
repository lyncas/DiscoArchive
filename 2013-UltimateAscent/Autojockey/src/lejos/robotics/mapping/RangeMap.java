package lejos.robotics.mapping;

import lejos.geom.*;
import lejos.robotics.navigation.Pose;

/*
 * WARNING: THIS CLASS IS SHARED BETWEEN THE classes AND pccomms PROJECTS.
 * DO NOT EDIT THE VERSION IN pccomms AS IT WILL BE OVERWRITTEN WHEN THE PROJECT IS BUILT.
 */

/**
 * The RangeMap interface supports determining the range to a feature on the map
 * (such as a wall), from an object with a specific pose.
 * 
 * It also supports the a method to determine if a point is within the mapped
 * area.
 * 
 * @author Lawrie Griffiths
 * 
 */
public interface RangeMap {
	/**
	 * The the range to the nearest wall (or other feature)
	 * @param pose the pose of the robot
	 * @return the range
	 */
  public float range(Pose pose);
  
  /**
   * Test if a point is within the mapped area
   * 
   * @param p the point
   * @return true iff the point is within the mapped area
   */
  public boolean inside(Point p);
  
  /**
   * Get the bounding rectangle for the mapped area
   * 
   * @return the bounding rectangle
   */
  public Rectangle getBoundingRect();
}
