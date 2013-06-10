/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands.drivetrain;

import com.sun.squawk.util.Arrays;
import disco.commands.CommandBase;
import java.lejosutil.ListIterator;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;


public class FollowPath extends CommandBase {
    DifferentialPilot p;
    Navigator nav;
    Path path;
    /*
     * distance in wheel diameter units
     */
    public FollowPath(Path path) {
        // Use requires() here to declare subsystem dependencies
        
        requires(drivetrain);
        System.out.println("INIT 0");
	p=drivetrain.getPilot();
        nav=drivetrain.getNavigator();
        this.path=path;
    }
    
    public FollowPath(Waypoint[] waypoints){
        this(new Path());
        for(int i=0;i<waypoints.length;i++) {
            path.add(waypoints[i]);
        }
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        System.out.println("INIT");
	nav.clearPath();
        //nav.setPath(path);
        ListIterator it=path.listIterator();
        while (it.hasNext()){
            nav.addWaypoint((Waypoint)it.next());
        }
        nav.followPath();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        System.out.println(nav.pathCompleted());
        return nav.pathCompleted();
    }

    // Called once after isFinished returns true
    protected void end() {
        System.out.println("ENDING");
	p.stop();
        drivetrain.tankDrive(0, 0);
        drivetrain.disableControl();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
    }
}