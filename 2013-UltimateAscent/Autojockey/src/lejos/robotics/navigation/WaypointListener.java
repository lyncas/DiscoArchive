package lejos.robotics.navigation;

/*
 * WARNING: THIS CLASS IS SHARED BETWEEN THE classes AND pccomms PROJECTS.
 * DO NOT EDIT THE VERSION IN pccomms AS IT WILL BE OVERWRITTEN WHEN THE PROJECT IS BUILT.
 */

/**
 * Interface for informing listeners that a way point has been generated.
 * 
 */
public interface WaypointListener
{
  /**
   * Called when the class providing waypoints generates a new waypoint.
   * @param wp the new waypoint
   */
  public void addWaypoint(Waypoint wp);
  
  /**
   * Called when generation of the path is complete
   */
  public void pathGenerated();
  
}
