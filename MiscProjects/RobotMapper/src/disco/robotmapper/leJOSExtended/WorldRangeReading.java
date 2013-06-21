/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.robotmapper.leJOSExtended;

import java.awt.geom.Point2D;
import lejos.robotics.RangeReading;
import lejos.robotics.navigation.Pose;

/**
 *
 * @author Sam Dietrich
 */
public class WorldRangeReading extends RangeReading {

    protected Point2D location;

    public WorldRangeReading(float angle, float range) {
	this(angle, range, new Pose(0, 0, 0));
    }

    public WorldRangeReading(float angle, float range, Pose robotPose) {
	super(angle, range);
	angle = angle + robotPose.getHeading();
	double x = robotPose.getX() + range * Math.cos(Math.toRadians(angle));
	double y = robotPose.getY() + range * Math.sin(Math.toRadians(angle));
	location=new Point2D.Double(x,y);
    }

    public WorldRangeReading(RangeReading rr, Pose robotPose) {
	this(rr.getAngle(),rr.getRange(),robotPose);
    }

    public Point2D getLocation(){
	return location;
    }
}
