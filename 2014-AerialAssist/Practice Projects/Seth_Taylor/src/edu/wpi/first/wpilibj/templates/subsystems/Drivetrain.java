
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.templates.commands.Arcade;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drivetrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private Victor LFVictor,LBVictor,RFVictor,RBVictor;
    private RobotDrive robotDrive;

    public Drivetrain(){
        LFVictor=new Victor(1,1);
        LBVictor=new Victor(2,2);
        RFVictor=new Victor(3,3);
        RBVictor=new Victor(4,4);
    
        robotDrive=new RobotDrive(LFVictor,LBVictor,RFVictor,RBVictor);
    }

    public void arcadeDrive(double move,double turn){
        double moveOutput=move, turnOutput=turn;
        robotDrive.arcadeDrive(moveOutput,turnOutput);
    }
    
       public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new Arcade());
    }
}

