/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.discobots.aerialassist.utils;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANNotInitializedException;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.communication.UsageReporting;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.discobots.aerialassist.HW;

/**
 *
 * @author Patrick
 */
public class BetterRobotDrive extends RobotDrive{

    static final int kFrontLeft_val = 0;
    static final int kFrontRight_val = 1;
    static final int kRearLeft_val = 2;
    static final int kRearRight_val = 3;
    
    public BetterRobotDrive(SpeedController frontLeftMotor, SpeedController rearLeftMotor, SpeedController frontRightMotor, SpeedController rearRightMotor) {
        super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
    }
    
    public void mecanumDrive_Polar(double magnitude, double direction, double rotation) {
        if(!kMecanumPolar_Reported){
            UsageReporting.report(UsageReporting.kResourceType_RobotDrive, getNumMotors(), UsageReporting.kRobotDrive_MecanumPolar);
            kMecanumPolar_Reported = true;
        }
        // Normalized for full power along the Cartesian axes.
        magnitude = limit(magnitude) * Math.sqrt(2.0);
        // The rollers are at 45 degree angles.
        
        double dirInRad = (direction + 45)* 3.14159 / 180.0;
        // - HW.angleController.getOutput()
        double cosD = Math.cos(dirInRad);
        double sinD = Math.sin(dirInRad);

        double wheelSpeeds[] = new double[kMaxNumberOfMotors];
        wheelSpeeds[kFrontLeft_val] = (sinD * magnitude + rotation);
        SmartDashboard.putNumber("FL Motor:   ", -(sinD * magnitude + rotation)); 
        wheelSpeeds[kFrontRight_val] = (cosD * magnitude + rotation);  //Uses cosD * magnitude - rotation in default code
        SmartDashboard.putNumber("FR Motor:   ", (cosD * magnitude + rotation)); 
        wheelSpeeds[kRearLeft_val] = (cosD * magnitude - rotation);    //Uses cosD * magnitude + rotation in default code
        SmartDashboard.putNumber("RL Motor:   ", -(cosD * magnitude - rotation)); 
        wheelSpeeds[kRearRight_val] = (sinD * magnitude - rotation);
        SmartDashboard.putNumber("RR Motor:   ", -(sinD * magnitude - rotation)); 

        normalize(wheelSpeeds);

        byte syncGroup = (byte)0x80;

        m_frontLeftMotor.set(wheelSpeeds[kFrontLeft_val] * m_invertedMotors[kFrontLeft_val] * m_maxOutput, syncGroup);
        m_frontRightMotor.set(wheelSpeeds[kFrontRight_val] * m_invertedMotors[kFrontRight_val] * m_maxOutput, syncGroup);
        m_rearLeftMotor.set(wheelSpeeds[kRearLeft_val] * m_invertedMotors[kRearLeft_val] * m_maxOutput, syncGroup);
        m_rearRightMotor.set(wheelSpeeds[kRearRight_val] * m_invertedMotors[kRearRight_val] * m_maxOutput, syncGroup);

        if (m_isCANInitialized) {
            try {
                CANJaguar.updateSyncGroup(syncGroup);
            } catch (CANNotInitializedException e) {
                m_isCANInitialized = false;
            } catch (CANTimeoutException e) {}
        }

        if (m_safetyHelper != null) m_safetyHelper.feed();
    }    
}
