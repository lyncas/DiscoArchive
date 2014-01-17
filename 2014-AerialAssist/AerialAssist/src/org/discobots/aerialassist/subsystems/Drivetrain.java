/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.subsystems;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.aerialassist.commands.CommandBase;
import org.discobots.aerialassist.commands.MecanumDrive;
//import robot.commands.CommandBase;
//import robot.commands.HolonomicPolar;

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
        drive.setSafetyEnabled(false);
        drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new MecanumDrive(CommandBase.oi.getGP()));
    }
    
    public void holonomicPolar(double mag,double dir,double rot) {
        drive.mecanumDrive_Polar(mag, dir, rot);
    }
    
    /*
    public void holonomic(double x, double y, double rotation){    
        double SC = 1/Math.sqrt(2);
        double mag = Math.sqrt(x*x+y*y);
        
        double LF = SC*(x+y)+rotation;
        double RF = SC*(x-y)-rotation;
        double max = Math.max(Math.abs(LF), Math.abs(RF));
        if(max==0) 
            max = 1;
        
        LF=LF* mag/max;
        RF=RF* mag/max;
        double RR = -LF;
        double LR = -RF;
        
        leftFront.set(-LF);
        rightFront.set(RF);
        rightRear.set(RR);
        leftRear.set(LR);
    }*/
}
