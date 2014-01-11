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
    Victor leftFront;
    Victor leftRear;
    Victor rightFront;
    Victor rightRear;
    RobotDrive drive;
    
    
    public Drivetrain(){
        super("Drivetrain");
        leftFront=new Victor(1,1);
        leftRear=new Victor(1,4);
        rightFront=new Victor(1,2);
        rightRear=new Victor(1,3);
        drive=new RobotDrive(leftFront,leftRear,rightFront,rightRear);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new ArcadeDrive(CommandBase.oi.getGP()));
    }
    
    public void arcade(double move, double rotate){
        drive.arcadeDrive(move, rotate);
    }
    
    public void holonomic(double x, double y, double rotation){    
        double SC = 1/Math.sqrt(2);
        double mag = Math.sqrt(x*x+y*y);
        
        double LF = SC*(x+y)+rotation;
        double RF = SC*(x-y)+rotation;
        double max = Math.max(Math.abs(LF), Math.abs(RF));
        if(max==0) 
            max = 1;
        
        LF=LF* mag/max;
        RF=RF* mag/max;
        double RR = -LF;
        double LR = -RF;
        
        HW.leftFront.set(LF);
        HW.rightFront.set(RF);
        HW.rightRear.set(RR);
        HW.leftRear.set(LR);
    }
    
}
