package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;
import edu.wpi.first.wpilibj.templates.commands.ExampleCommand;


/**
 *
 */
public class ExampleSubsystem extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Victor leftFront;
    Victor leftBack;
    Victor rightFront;
    Victor rightBack;
    
    public ExampleSubsystem(){
        leftFront=new Victor(RobotMap.leftFront);
        leftBack=new Victor(RobotMap.leftBack);
        rightFront=new Victor(RobotMap.rightFront);
        rightBack=new Victor(RobotMap.rightBack);
        //Tells where in the sidecar the motors are connected
    }
   
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new ExampleCommand(CommandBase.oi.getGP()));
    }
    
    public void forward(){
        leftFront.set(1);
        leftBack.set(1);
        rightFront.set(1);
        rightBack.set(1);
    }
    
    public void leftTurn(){
        leftFront.set(-1);
        leftBack.set(-1);
        rightFront.set(1);
        rightBack.set(1);
    }
    
    public void rightTurn(){
        leftFront.set(1);
        leftBack.set(1);
        rightFront.set(-1);
        rightBack.set(-1);
    }
    
    public void stop(){
        leftFront.set(0);
        leftBack.set(0);
        rightFront.set(0);
        rightBack.set(0);
    }
}

