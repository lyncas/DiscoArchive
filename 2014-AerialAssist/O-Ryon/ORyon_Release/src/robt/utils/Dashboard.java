/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robt.utils;


import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robt.commands.CommandBase;

public class Dashboard {

    public static SendableChooser autonChooser;
    public static double autonSetpoint = 5400;

    public static void putStuff() {
        putSubsystems();
        putSensors();
    }

    //Only call this once or we overflow the memory. Bad day.
    private static void putSubsystems() {
        SmartDashboard.putData(CommandBase.drivetrain);
        SmartDashboard.putData(CommandBase.flipper);
        SmartDashboard.putData(CommandBase.compressor);
        SmartDashboard.putData(CommandBase.intake);
    }

    //Repeatedly call this to update dashboard values.
    public static void putSensors() {
        //DRIVETRAIN
	SmartDashboard.putNumber("Left joy Y", ((GamePad)(CommandBase.oi.getJoy1())).getLY());
        SmartDashboard.putNumber("Right joy Y", ((GamePad)(CommandBase.oi.getJoy1())).getRY());

	//COMPRESSOR
        SmartDashboard.putBoolean("Air Full", CommandBase.compressor.getPressureSwitch());
        SmartDashboard.putString("Compressor State", CommandBase.compressor.getEnabled() ? "ON" : "OFF");

    }

}
