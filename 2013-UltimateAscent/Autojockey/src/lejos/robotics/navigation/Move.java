package lejos.robotics.navigation;


import java.lejoslang.Float;
import java.lejoslang.Math;

/*
 * WARNING: THIS CLASS IS SHARED BETWEEN THE classes AND pccomms PROJECTS.
 * DO NOT EDIT THE VERSION IN pccomms AS IT WILL BE OVERWRITTEN WHEN THE PROJECT IS BUILT.
 */

/**
 * Models a movement performed by a pilot
 * 
 * @author Lawrie Griffiths
 *
 */
public class Move{
	/**
	 * The type of  movement made in sufficient detail to allow errors
	 * in the movement to be modeled.
	 */
        public static final int MoveType_TRAVEL = 0;
        public static final int MoveType_ROTATE = 1;
        public static final int MoveType_ARC = 2;
        public static final int MoveType_STOP = 3;
//	public enum MoveType {TRAVEL, ROTATE, ARC, STOP}
	private float distanceTraveled, angleTurned;
	private int moveType;
	private float arcRadius = Float.POSITIVE_INFINITY;
	private boolean isMoving;
	private long timeStamp;
	private float travelSpeed, rotateSpeed;
	
	/**
	 * Create a movement object to record a movement made by a pilot.
         * This method automatically calculates the
	 * MoveType based on the data as follows:<br>
	 * <li>(distance NOT 0) AND (angle NOT 0) --> ARC
	 * <li>(distance = 0) AND (angle NOT 0) --> ROTATE
	 * <li>(distance NOT 0) AND (angle = 0) --> TRAVEL
	 * <li>(distance = 0) AND (angle = 0) --> STOP
	 * @param distance the distance traveled in pilot units
	 * @param angle the angle turned in degrees
	 * @param isMoving true iff the movement was created while the robot was moving
	 */
  public Move(float distance, float angle, boolean isMoving)
  {
		this.moveType = Move.calcMoveType(distance, angle);
		this.distanceTraveled = distance;
		this.angleTurned = angle;
		this.isMoving = isMoving;
		// TODO: This works fine, but could use convertDistanceToAngle() instead here?
		if (Math.abs(angle) > 0.5) {
			double turnRad = Math.toRadians(angle);
			arcRadius = (float) (distance / turnRad);
		}
		this.timeStamp = System.currentTimeMillis();
	}

  /**
   * Create a movement object to record a movement made by a pilot.
   * @param type the movement type
   * @param distance the distance traveled in pilot units
   * @param angle the angle turned in degrees
   * @param travelSpeed the travel speed
   * @param rotateSpeed the rotate speed
   * @param isMoving true iff the movement was created while the robot was moving
   */
  public Move(int type, float distance, float angle, float travelSpeed, float rotateSpeed, boolean isMoving)
  {
    this.moveType = type;
    this.distanceTraveled = distance;
    this.angleTurned = angle;
    if (Math.abs(angle) > 0.5) 
    {
		double turnRad = Math.toRadians(angle);
		arcRadius = (float) (distance / turnRad);
	}
    this.travelSpeed = travelSpeed;
    this.rotateSpeed = rotateSpeed;
    this.isMoving = isMoving;
    this.timeStamp = System.currentTimeMillis();
  }

  /**
   * Create a movement object to record a movement made by a pilot.
   * @param type the movement type
   * @param distance the distance traveled in pilot units
   * @param angle the angle turned in degrees
   * @param isMoving true iff the movement was created while the robot was moving
   */
  public Move(int type, float distance, float angle, boolean isMoving)
  {
	  this(type,distance,angle,0f,0f,isMoving);
  }
  

  /**
   * use this method to recycle an existing Move instead of creating a new one
   * @param distance
   * @param angle
   * @param isMoving
   */
  public void setValues(int type, float distance, float angle,boolean isMoving)
  {
    this.moveType = Move.calcMoveType(distance, angle);
	this.distanceTraveled = distance;
	this.angleTurned = angle;
	this.isMoving = isMoving;
	// TODO: This works fine, but could use convertDistanceToAngle() instead here?
	if (Math.abs(angle) > 0.5) 
	{
		double turnRad = Math.toRadians(angle);
		arcRadius = (float) (distance / turnRad);
	}
	this.timeStamp = System.currentTimeMillis();
  }

	/**
	 * Helper method to calculate the MoveType based on distance, angle, radius parameters.
	 * 
	 * @param distance
	 * @param angle
	 * @return
	 */
	private static int calcMoveType(float distance, float angle) {
		if(distance == 0 & angle == 0) return MoveType_STOP;
		else if(distance != 0 & angle == 0) return MoveType_TRAVEL;
		else if(distance == 0 & angle != 0) return MoveType_ROTATE;
		else return MoveType_ARC;
	}
	
	/**
	 * Alternate constructor that uses angle and turn radius instead. Useful for constructing arcs, but it
	 * can't represent a straight line of travel with a set distance (use the other constructor to specify distance).
	 *  This method automatically calculates the MoveType based on the data as follows:<br>
	 * <li>(radius NOT 0) AND (angle NOT 0) --> ARC
	 * <li>(radius = 0) AND (angle NOT 0) --> ROTATE
	 * <li>(radius = 0) AND (angle = 0) --> STOP
	 * <li>(radius = +infinity) AND (angle = 0) --> TRAVEL
	 * <li>NOTE: can't calculate distance based only on angle and radius, therefore distance can't be calculated and will equal NaN)
	 * @param isMoving
	 * @param angle
	 * @param turnRadius
	 */
	public Move(boolean isMoving, float angle, float turnRadius) {
		this.distanceTraveled = Move.convertAngleToDistance(angle, turnRadius);
		this.moveType = Move.calcMoveType(this.distanceTraveled, angle);
		this.angleTurned = angle;
		this.isMoving = isMoving;
		arcRadius = turnRadius;
		this.timeStamp = System.currentTimeMillis();
	}

	/**
	 * Get the distance traveled. This can be in a straight line or an arc path.
	 * 
	 * @return the distance traveled
	 */
	public float getDistanceTraveled() {
		return distanceTraveled;
	}
	
	/**
	 * The time stamp is the system clock at the time the Move object is created. It is set automatically
	 * in the Move constructor using {@link System#currentTimeMillis()} 
	 * @return Time stamp in milliseconds.
	 */
	public long getTimeStamp() {
		return timeStamp;
	}
	
	/**
	 * Get the angle turned by a rotate or an arc operation.
	 * 
	 * @return the angle turned
	 */
	public float getAngleTurned() {
		return angleTurned;
	}
	
	/**
	 * Get the type of the movement performed
	 * 
	 * @return the movement type
	 */
	public int getMoveType() {
		return moveType;
	}
	
	/**
	 * Get the radius of the arc
	 * 
	 * @return the radius of the arc
	 */
	public float getArcRadius() {
		return arcRadius;
	}
	
	/**
	 * Get the travel speed
	 * @return the travel speed
	 */
	public float getTravelSpeed() {
		return travelSpeed;
	}
	
	/**
	 * Get the rotate speed
	 * @return the rotate speed
	 */
	public float getRotateSpeed() {
		return rotateSpeed;
	}
		
	/**
	 * Test if move was in progress
	 * 
	 * @return true iff the robot was moving when this Move object was created
	 */
	public boolean isMoving() {
		return isMoving;
	}
	
	/**
	 * Static utility method for converting distance (given turn radius) into angle.
	 * @param distance
	 * @param turnRadius
	 * @return angle
	 */
	public static float convertDistanceToAngle(float distance, float turnRadius){
		return (float)((distance * 360) / (2 * Math.PI * turnRadius));
	}
	
	/**
	 * Static utility method for converting angle (given turn radius) into distance.
	 * @param angle
	 * @param turnRadius
	 * @return distance
	 */
	public static float convertAngleToDistance(float angle, float turnRadius){
		return (float)((angle * 2 * Math.PI * turnRadius) / 360);
	}
	
	
	public String toString() {
                
		String s = "";
		switch(moveType) {
		case MoveType_ROTATE: 
			s += "Rotate" + angleTurned + " at " + rotateSpeed;
			break;
		case MoveType_TRAVEL:
			s += "Travel" + distanceTraveled + " at " + travelSpeed;
			break;
		case MoveType_ARC:
			s += "Arc" + " of " + arcRadius + " for " + angleTurned + "degrees  at " + travelSpeed;
			break;
                case MoveType_STOP:
                        s += "Stop";
                        break;
		}
		return s;
	}
}
