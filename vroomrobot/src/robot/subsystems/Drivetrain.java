/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.subsystems;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import robot.HW;
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
        super("Drivetrain");
        left1=new Victor(HW.leftdrive1slot,HW.leftdrive1channel);
        left2=new Victor(HW.leftdrive2slot,HW.leftdrive2channel);
        right1=new Victor(HW.rightdrive1slot,HW.rightdrive1channel);
        right2=new Victor(HW.rightdrive2slot,HW.rightdrive2channel);
        drive=new RobotDrive(left1,left2,right1,right2);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new ArcadeDrive(CommandBase.oi.getGP()));
    }
    
    public void arcade(double move, double rotate){
        drive.arcadeDrive(move, rotate);
    }
    
}
