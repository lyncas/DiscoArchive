/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.utils;

import disco.HW;
import disco.MainAscent;
import disco.commands.CommandBase;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import lejos.robotics.navigation.Pose;

public class Dashboard {

    public static SendableChooser autonChooser;
    public static double autonSetpoint = 5400;
    private static NetworkTable table;
    //These must be the same as in the RobotMapperExtension
    private static final String RobotMapperTableLocation = "LocationInformation",
            KEY_X_POSITION = "xPosition",
            KEY_Y_POSITION = "yPosition",
            KEY_HEADING = "heading",
            KEY_ROBOT_WIDTH = "robot_Width",
            KEY_ROBOT_LENGTH = "robot_Length";

    public static void init() {
        table = NetworkTable.getTable(RobotMapperTableLocation);
        if(table==null){
            System.out.println("NULL TABLE");
        }
        else{
            System.out.println("Table OK");
        }
        putStuff();
    }

    public static void putStuff() {
        putSubsystems();
        putSensors();
        sendleJOS();
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
        SmartDashboard.putNumber("Right Encoder", CommandBase.drivetrain.getRightEncoder());
        putTest();
        //Drive power information
        SmartDashboard.putNumber("Left Drive Output", CommandBase.drivetrain.getPWMLeft());
        SmartDashboard.putNumber("Right Drive Output", CommandBase.drivetrain.getPWMRight());
        SmartDashboard.putNumber("Raw Gyro", CommandBase.drivetrain.getRawGyroAngle());
        SmartDashboard.putNumber("Gyro", CommandBase.drivetrain.getGyroAngle());
        
        //Location information
        Pose p = CommandBase.drivetrain.getPoseProvider().getPose();
        SmartDashboard.putNumber("X", p.getX());
        SmartDashboard.putNumber("Y", p.getY());
        SmartDashboard.putNumber("Heading:", p.getHeading());

        //COMPRESSOR
        SmartDashboard.putBoolean("Air Full", CommandBase.compressor.getPressureSwitch());
        SmartDashboard.putString("Compressor State", CommandBase.compressor.getEnabled() ? "ON" : "OFF");
    }

    public static void sendleJOS() {
        Pose p = CommandBase.drivetrain.getPoseProvider().getPose();
        if (p != null) {
            table.putNumber(KEY_X_POSITION, p.getX());
            table.putNumber(KEY_Y_POSITION, p.getY());
            table.putNumber(KEY_HEADING, p.getHeading());
            table.putNumber(KEY_ROBOT_WIDTH, HW.wheelSeparation + 4);
            table.putNumber(KEY_ROBOT_LENGTH, HW.robotLength);
        }
    }

    public static void putTest() {
        SmartDashboard.putNumber("left velocity", CommandBase.drivetrain.getLeftRate());
        SmartDashboard.putNumber("right velocity", CommandBase.drivetrain.getRightRate());
    }
}
