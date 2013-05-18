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

public class Dashboard {

    public static SendableChooser autonChooser;
    public static double autonSetpoint = 5400;

    public static void init() {
        autonChooser = new SendableChooser();
        autonChooser.addDefault("Safe", new Autonomous(Autonomous.MODE_SAFE));
        autonChooser.addObject("Risky", new Autonomous(Autonomous.MODE_RISKY));
        autonChooser.addObject("Dangerous", new Autonomous(Autonomous.MODE_DANGEROUS));
        autonChooser.addObject("DoNothing", new Autonomous(Autonomous.MODE_NOTHING));
        putStuff();
    }

    public static void putStuff() {
        putSubsystems();
        putSensors();
//        SmartDashboard.putNumber("Shooter Auton Setpoint", autonSetpoint);
        SmartDashboard.putData("Autonomous Chooser", autonChooser);
 //       putTest();
    }

    //Only call this once or we overflow the memory. Bad day.
    private static void putSubsystems() {
        SmartDashboard.putData(CommandBase.drivetrain);
        SmartDashboard.putData(CommandBase.shooter);
        SmartDashboard.putData(CommandBase.compressor);
    }

    //Repeatedly call this to update dashboard values.
    public static void putSensors() {
         SmartDashboard.putNumber("Execution loop time", MainAscent.getExecutionTime());

	 //SHOOTER
	SmartDashboard.putNumber("Forward Shooter RPM", CommandBase.shooter.getFrontRPM());
	SmartDashboard.putNumber("Forward Shooter PWM", CommandBase.shooter.getFrontPower());
	SmartDashboard.putNumber("Back Shooter RPM", CommandBase.shooter.getBackRPM());
	SmartDashboard.putNumber("Back Shooter PWM", CommandBase.shooter.getBackPower());
        SmartDashboard.putNumber("Shooter difference", CommandBase.shooter.difference);
        SmartDashboard.putNumber("Shooter Setpoint", CommandBase.shooter.getSetpoint());
        SmartDashboard.putBoolean("Shooter On target", CommandBase.shooter.isOnTarget());
        SmartDashboard.putNumber("load sensor", CommandBase.shooter.isLoaded());

        //DRIVETRAIN
	SmartDashboard.putNumber("Left joy Y", ((GamePad)(CommandBase.oi.getJoy1())).getLY());
        SmartDashboard.putNumber("Right joy Y", ((GamePad)(CommandBase.oi.getJoy1())).getRY());
        SmartDashboard.putBoolean("Touching Left Pyramid", CommandBase.drivetrain.getLeftPyramid());
        SmartDashboard.putBoolean("Touching Right Pyramid", CommandBase.drivetrain.getRightPyramid());
        SmartDashboard.putNumber("Left Encoder", CommandBase.drivetrain.getLeftEncoder());
        SmartDashboard.putNumber("Right Encoder", CommandBase.drivetrain.getRightEncoder());
        putTest();
        SmartDashboard.putNumber("Left Drive Output", CommandBase.drivetrain.getPWMLeft());
        SmartDashboard.putNumber("Right Drive Output", CommandBase.drivetrain.getPWMRight());
        
	//COMPRESSOR
        SmartDashboard.putBoolean("Air Full", CommandBase.compressor.getPressureSwitch());
        SmartDashboard.putString("Compressor State", CommandBase.compressor.getEnabled() ? "ON" : "OFF");

        //SHOOTER AUTON SETPOINT
        SmartDashboard.getNumber("Shooter Auton Setpoint", autonSetpoint);
    }
    
    public static void putTest(){
        SmartDashboard.putNumber("left velocity", CommandBase.drivetrain.getLeftRate());
        SmartDashboard.putNumber("right velocity", CommandBase.drivetrain.getRightRate());
    }
}
