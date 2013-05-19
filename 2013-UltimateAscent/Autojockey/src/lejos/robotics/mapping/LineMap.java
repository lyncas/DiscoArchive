package lejos.robotics.mapping;

import java.lejoslang.Math;
import lejos.geom.*;
import lejos.robotics.navigation.Pose;

/*
 * WARNING: THIS CLASS IS SHARED BETWEEN THE classes AND pccomms PROJECTS.
 * DO NOT EDIT THE VERSION IN pccomms AS IT WILL BE OVERWRITTEN WHEN THE PROJECT IS BUILT.
 */

/**
 * A map of a room or other closed environment, represented by line segments
 *
 * @author Lawrie Griffiths
 *
 */
public class LineMap implements RangeMap {
  private Line[] lines;
  private Rectangle boundingRect;

  /**
   * Calculate the range of a robot to the nearest wall
   *
   * @param pose the pose of the robot
   * @return the range or -1 if not in range
   */
  public float range(Pose pose) {
    Line l = new  Line(pose.getX(), pose.getY(), pose.getX() + 254f
    	        * (float) Math.cos(Math.toRadians(pose.getHeading())), pose.getY() + 254f
    	        * (float) Math.sin(Math.toRadians(pose.getHeading())));
    Line rl = null;

    for (int i = 0; i < lines.length; i++) {
      Point p = lines[i].intersectsAt(l);
      if (p == null) continue; // Does not intersect
      Line tl = new Line(pose.getX(), pose.getY(), p.x, p.y);

      // If the range line intersects more than one map line
      // then take the shortest distance.
      if (rl == null || tl.length() < rl.length()) rl = tl;
    }
    return (rl == null ? -1 : rl.length());
  }

  /**
   * Create a map from an array of line segments and a bounding rectangle
   *
   * @param lines the line segments
   * @param boundingRect the bounding rectangle
   */
  public LineMap(Line[] lines, Rectangle boundingRect) {
    this.lines = lines;
    this.boundingRect = boundingRect;
  }

  /**
   * Constructor to use when map will be loaded from a data stream
   */
  public LineMap() {
  }

  /**
   * Check if a point is within the mapped area
   *
   * @param p the Point
   * @return true iff the point is with the mapped area
   */
  public boolean inside(Point p) {
    if (p.x < boundingRect.x || p.y < boundingRect.y) return false;
    if (p.x > boundingRect.x + boundingRect.width
        || p.y > boundingRect.y + boundingRect.height) return false;

    // Create a line from the point to the left
    Line l = new Line(p.x, p.y, p.x - boundingRect.width, p.y);

    // Count intersections
    int count = 0;
    for (int i = 0; i < lines.length; i++) {
      if (lines[i].intersectsAt(l) != null) count++;
    }
    // We are inside if the number of intersections is odd
    return count % 2 == 1;
  }

  /**
   * Return the bounding rectangle of the mapped area
   *
   * @return the bounding rectangle
   */
  public Rectangle getBoundingRect() {
    return boundingRect;
  }

  /**
   * Dump the map to a DataOutputStream
   * @param dos the stream
   * @throws IOException
   */
  /**
   * Load a map from a DataInputStream
   *
   * @param dis the stream
   * @throws IOException
   */

  /**
   * Get the lines as an array
   *
   * @return the lines as an array
   */
  public Line[] getLines() {
	  return lines;
  }

  /**
   * Create an SVG map file
   *
   * @param fileName the name of the file to create or overwrite
   * @throws IOException
   */
//DELETED

  /**
   * Create a line map with the y axis flipped
   *
   * @return the new LineMap
   */
  public LineMap flip() {
	  float maxY = boundingRect.y + boundingRect.height;
	  Line[] ll = new Line[lines.length];

	  for(int i=0;i<lines.length;i++) {
		  ll[i] = new Line(lines[i].x1, maxY - lines[i].y1, lines[i].x2, maxY - lines[i].y2);
	  }

	  return new LineMap(ll, boundingRect);
  }
}

