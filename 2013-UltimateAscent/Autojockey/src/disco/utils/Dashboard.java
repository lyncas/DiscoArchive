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
import lejos.robotics.pathfinding.Path;

public class Dashboard {

    private static NetworkTable table;
    //These must be the same as in the RobotMapperExtension
    private static final String RobotMapperTableLocation = "LocationInformation",
	    KEY_POSE = "robot_Pose",
	    KEY_ROBOT_WIDTH = "robot_Width",
	    KEY_ROBOT_LENGTH = "robot_Length",
	    KEY_PATH="robot_path";

    public static void init() {
        table = NetworkTable.getTable(RobotMapperTableLocation);
        if (table == null) {
            System.out.println("NULL TABLE");
        } else {
            System.out.println("Table OK");
        }
        putStuff();
    }

    public static void putStuff() {
        putSubsystems();
        putSensors();
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

        sendleJOSPose();

        //DRIVETRAIN
        //Encoder information
        SmartDashboard.putNumber("Left Encoder", CommandBase.drivetrain.getLeftEncoder());
        SmartDashboard.putNumber("Right Encoder", CommandBase.drivetrain.getRightEncoder());
        SmartDashboard.putNumber("left velocity", CommandBase.drivetrain.getLeftRate());
        SmartDashboard.putNumber("right velocity", CommandBase.drivetrain.getRightRate());
        SmartDashboard.putNumber("Gyro", CommandBase.drivetrain.getGyroAngle());
        //Sonar information
        SmartDashboard.putNumber("Front sonar", CommandBase.drivetrain.getFrontSonar());
        SmartDashboard.putNumber("Left sonar", CommandBase.drivetrain.getLeftSonar());
        SmartDashboard.putNumber("Right sonar", CommandBase.drivetrain.getRightSonar());
        SmartDashboard.putNumber("Back sonar", CommandBase.drivetrain.getBackSonar());

        //Location information
        Pose p = CommandBase.drivetrain.getPoseProvider().getPose();
        SmartDashboard.putNumber("X", p.getX());
        SmartDashboard.putNumber("Y", p.getY());
        SmartDashboard.putNumber("Heading:", p.getHeading());

        //COMPRESSOR
        SmartDashboard.putBoolean("Air Full", CommandBase.compressor.getPressureSwitch());
        SmartDashboard.putString("Compressor State", CommandBase.compressor.getEnabled() ? "ON" : "OFF");
    
        //SHOOTER
        SmartDashboard.putNumber("Forward Shooter RPM", CommandBase.shooter.getFrontRPM());
        SmartDashboard.putNumber("Back Shooter RPM", CommandBase.shooter.getBackRPM());
        SmartDashboard.putNumber("Shooter difference", CommandBase.shooter.difference);
        SmartDashboard.putNumber("Shooter Setpoint", CommandBase.shooter.getSetpoint());
        SmartDashboard.putBoolean("Shooter On target", CommandBase.shooter.isOnTarget());
    
    }   

    public static void sendleJOSPose() {
        Pose p = CommandBase.drivetrain.getPoseProvider().getPose();
        if (p != null) {
            table.putString(KEY_POSE,p.toString());
        }
            table.putNumber(KEY_ROBOT_WIDTH, HW.wheelSeparation + 4);
            table.putNumber(KEY_ROBOT_LENGTH, HW.robotLength);
        
    }

    public static void sendleJOSPath(Path p){
	if(p!=null) {
	    table.putString(KEY_PATH, p.toString());
	}
    }

}
