/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dylan.subsystems;
import dylan.templates.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import dylan.commands.TankDrive;
import edu.wpi.first.wpilibj.*;

/**
 *
 * @author Dylan
 */
public class drivetrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private Victor leftDrive1;
    private Victor leftDrive2;
    private Victor rightDrive1;
    private Victor rightDrive2;
    private RobotDrive drive;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new TankDrive());
    }
    public drivetrain(){
        super("drivetrain");
        leftDrive1=new Victor(RobotMap.LeftDrive1Slot,RobotMap.LeftDrive1Channel);
	leftDrive2=new Victor(RobotMap.LeftDrive2Slot,RobotMap.LeftDrive2Channel);
	rightDrive1=new Victor(RobotMap.RightDrive1Slot,RobotMap.RightDrive1Channel);
	rightDrive2=new Victor(RobotMap.RightDrive2Slot,RobotMap.RightDrive2Channel);
        drive= new RobotDrive(leftDrive1,leftDrive2,rightDrive1,rightDrive2);
    }
    public void tank(double left, double right){
        drive.tankDrive(left, right);
    }
}
