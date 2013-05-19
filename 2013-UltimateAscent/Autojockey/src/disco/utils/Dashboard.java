/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.utils;

import disco.MainAscent;
import disco.commands.Autonomous;
import disco.commands.CommandBase;
import disco.subsystems.Arduino;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import lejos.robotics.navigation.Pose;

public class Dashboard {

    public static SendableChooser autonChooser;
    public static double autonSetpoint = 5400;

    public static void init() {
	putStuff();
    }

    public static void putStuff() {
	putSubsystems();
	putSensors();
	//       putTest();
    }

    //Only call this once or we overflow the memory. Bad day.
    private static void putSubsystems() {
	SmartDashboard.putData(CommandBase.drivetrain);
	SmartDashboard.putData(CommandBase.compressor);
    }

    //Repeatedly call this to update dashboard values.
    public static void putSensors() {
	SmartDashboard.putNumber("Execution loop time", MainAscent.getExecutionTime());

	//DRIVETRAIN
	//Joystick information
	SmartDashboard.putNumber("Left joy Y", ((GamePad) (CommandBase.oi.getJoy1())).getLY());
	SmartDashboard.putNumber("Right joy Y", ((GamePad) (CommandBase.oi.getJoy1())).getRY());
	//Encoder information
	SmartDashboard.putNumber("Left Encoder", CommandBase.drivetrain.getLeftEncoder());
        SmartDashboard.putNumber("Left degrees", CommandBase.drivetrain.leftDrive.getTachoCount());
        SmartDashboard.putNumber("left speed lejos", CommandBase.drivetrain.leftDrive.getSpeed());
        SmartDashboard.putNumber("right speed lejos", CommandBase.drivetrain.rightDrive.getSpeed());
        SmartDashboard.putNumber("Right degrees", CommandBase.drivetrain.rightDrive.getTachoCount());
	SmartDashboard.putNumber("Right Encoder", CommandBase.drivetrain.getRightEncoder());
	putTest();
	//Drive power information
	SmartDashboard.putNumber("Left Drive Output", CommandBase.drivetrain.getPWMLeft());
	SmartDashboard.putNumber("Right Drive Output", CommandBase.drivetrain.getPWMRight());
	//Location information
	Pose p = CommandBase.drivetrain.getPoseProvider().getPose();
	SmartDashboard.putNumber("X", p.getX());
	SmartDashboard.putNumber("Y", p.getY());
	SmartDashboard.putNumber("Heading:", p.getHeading());

	//COMPRESSOR
	SmartDashboard.putBoolean("Air Full", CommandBase.compressor.getPressureSwitch());
	SmartDashboard.putString("Compressor State", CommandBase.compressor.getEnabled() ? "ON" : "OFF");
    }

    public static void putTest() {
	SmartDashboard.putNumber("left velocity", CommandBase.drivetrain.getLeftRate());
	SmartDashboard.putNumber("right velocity", CommandBase.drivetrain.getRightRate());
    }
}
