/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands.drivetrain;

import disco.commands.CommandBase;
import java.util.Random;
import lejos.geom.Point;
import lejos.geom.Rectangle;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.DestinationUnreachableException;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;
import lejos.robotics.pathfinding.ShortestPathFinder;

public class GoToRandom extends CommandBase {

    DifferentialPilot pilot;
    PoseProvider pp;
    Navigator nav;
    double end_heading = 999;
    Rectangle boundary;
    LineMap obstacles = null;
    int MAX_ITERATIONS = 100;
    Path thePath;

    /**
     * Make the robot go to a random location given specified parameters
     *
     * @param features The obstacles and bounding rectangle to create the path
     * around
     * @param end_heading The heading of the robot at the destination
     */
    public GoToRandom(LineMap features, double end_heading) {
	this(features.getBoundingRect(), end_heading);
	this.obstacles = features;
    }

    public GoToRandom(LineMap features) {
	this(features, 999);
    }

    public GoToRandom(Rectangle boundary) {
	this(boundary, 999);
    }

    /**
     * Make the robot go to a random location given specified parameters
     *
     * @param boundary The rectangle the robot should stay inside. Use wheel
     * diameter units.
     * @param end_heading The heading of the robot at the destination
     */
    public GoToRandom(Rectangle boundary, double end_heading) {
	requires(drivetrain);
	pilot = drivetrain.getPilot();
	nav = drivetrain.getNavigator();
	pp = drivetrain.getPoseProvider();
	this.boundary = boundary;
	this.end_heading = end_heading;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	Pose pose = pp.getPose();
	Point dst = null;
	Random r = new Random();
	//find final desired heading
	if (end_heading == 999) {
	    end_heading = r.nextInt(360);
	}
	end_heading %= 360;
	//Rectangle only
	if (obstacles == null) {
	    int range_X = (int) boundary.width;
	    int x = (int) (r.nextInt(range_X) + boundary.x);
	    int range_Y = (int) boundary.height;
	    int y = (int) (boundary.y - r.nextInt(range_Y));
	    nav.clearPath();
	    nav.goTo(new Waypoint(x,y,end_heading));
	}
	//Worry about the lineMap
	else {
	    //Attempt to find random points inside the map by searching inside its bounding rectangle.
	    int iterations = 0;
	    do {
		int range_X = (int) boundary.width;
		int x = (int) (r.nextInt(range_X) + boundary.x);
		int range_Y = (int) boundary.height;
		int y = (int) (boundary.y - r.nextInt(range_Y));
		dst = new Point(x, y);
		iterations++;
	    } while (!obstacles.inside(dst) && iterations < MAX_ITERATIONS);
	    //handle unreachable points
	    if (!obstacles.inside(dst)) {
		System.out.println("No suitable destination point found in " + iterations + " attempts!");
		dst = new Point(pose.getX(), pose.getY());
	    }
	    //Find path
	    try {
		thePath = new ShortestPathFinder(obstacles).findRoute(pose, new Waypoint(dst.getX(), dst.getY(), end_heading));
	    } catch (DestinationUnreachableException ex) {
		System.out.println("No Path found to " + dst.toString());
		thePath = new Path();
	    }
	    //Go there
	    nav.setPath(thePath);
	    nav.followPath();
	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return nav.pathCompleted();
    }

    // Called once after isFinished returns true
    protected void end() {
	pilot.stop();
	drivetrain.tankDrive(0, 0);
	drivetrain.disableControl();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
    }
}