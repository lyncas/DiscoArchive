/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.subsystems;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import robot.commands.ArcadeDrive;
import robot.commands.CommandBase;

/**
 *
 * @author Sam
 */
public class Drivetrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Victor left1;
    Victor left2;
    Victor right1;
    Victor right2;
    RobotDrive drive;
    
    
    public Drivetrain(){
        left1=new Victor(1,1);
        left2=new Victor(1,2);
        right1=new Victor(1,3);
        right2=new Victor(1,4);
        drive=new RobotDrive(left1,left2,right1,right2);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new ArcadeDrive(CommandBase.oi.getJoy()));
    }
    
    public void arcade(double move, double rotate){
        drive.arcadeDrive(move, rotate);
    }
    
}
