package lejos.robotics;

//;
import java.lejosutil.ArrayList;
import java.lejosutil.Iterator;

/*
 * WARNING: THIS CLASS IS SHARED BETWEEN THE classes AND pccomms PROJECTS.
 * DO NOT EDIT THE VERSION IN pccomms AS IT WILL BE OVERWRITTEN WHEN THE PROJECT IS BUILT.
 */

/**
 * Represents a set of range readings.
 *
 * @author Lawrie Griffiths
 */
public class RangeReadings extends ArrayList  {


  public RangeReadings(int numReadings) {
    super(numReadings);
    for(int i=0;i<numReadings;i++) add(new RangeReading(0,-1));
  }

  /**
   * Get a specific range reading
   *
   * @param i the reading index
   * @return the range value
   */
  public float getRange(int i) {
    return ((RangeReading)get(i)).getRange();
  }

  /**
   * Get a range reading for a specific angle
   *
   * @param angle the reading angle
   * @return the range value
   */
  public float getRange(float angle) {
    for (Iterator it = this.iterator(); it.hasNext();) {
	  RangeReading r = (RangeReading)it.next();
    	if (r.getAngle() == angle) return r.getRange();
    }
    return -1f;
  }

  /**
   * Get the angle of a specific reading
   *
   * @param index the index of the reading
   * @return the angle in degrees
   */
  public float getAngle(int index) {
	  return  ((RangeReading)get(index)).getAngle();
  }

  /**
   * Return true if the readings are incomplete
   *
   * @return true iff one of the readings is not valid
   */
  public boolean incomplete() {
      for (Iterator it = this.iterator(); it.hasNext();) {
	  RangeReading r = (RangeReading)it.next();
	  if (r.invalidReading()) return true;
      }
    return false;
  }

  /**
   * Get the number of readings in a set
   */
  public int getNumReadings() {
    return size();
  }

  /**
   * Set the range reading
   *
   * @param index the index of the reading in the set
   * @param angle the angle of the reading relative to the robot heading
   * @param range the range reading
   */
  public void setRange(int index, float angle, float range) {
	  set(index, new RangeReading(angle, range));
  }



  /**
   * Print the range readings on standard out
   */
  public void printReadings() {
	int index = 0;
      for (Iterator it = this.iterator(); it.hasNext();) {
	  RangeReading r = (RangeReading) it.next();
	  System.out.println("Range " + index + " = " +
			  (r.invalidReading() ? "Invalid" : String.valueOf(r.getRange())));
	  index++;
      }
  }
}

