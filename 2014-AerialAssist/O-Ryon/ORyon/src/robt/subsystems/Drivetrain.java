
package robt.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import robt.HW;
import robt.commands.drive.Lerpdrive;
import robt.utils.BetterDrive;

/**
 *
 */
public class Drivetrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Talon left1=new Talon(HW.left1slot,HW.left1channel);
    Talon left2=new Talon(HW.left2slot,HW.left2channel);
    Talon right1=new Talon(HW.right1slot,HW.right1channel);
    Talon right2=new Talon(HW.right2slot,HW.right2channel);
    BetterDrive drive=new BetterDrive(left1,left2,right1,right2);
    
    public Drivetrain(){
        super("drivetrain");
        drive.setSafetyEnabled(false);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new Lerpdrive());
    }

    public void tankDriveUnsmoothed(double driveLeft, double driveRight) {
        drive.tankDrive(driveLeft, driveRight, false, false);
    }
}

