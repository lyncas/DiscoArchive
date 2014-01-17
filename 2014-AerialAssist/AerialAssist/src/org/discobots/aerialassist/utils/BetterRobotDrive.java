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

/**
 *
 * @author Developer
 */
public class BetterRobotDrive extends RobotDrive{

    public BetterRobotDrive(SpeedController frontLeftMotor, SpeedController rearLeftMotor, SpeedController frontRightMotor, SpeedController rearRightMotor) {
        super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
    }
    
    public void mecanumDrive_Polar(double magnitude, double direction, double rotation) {
        if(!kMecanumPolar_Reported){
            UsageReporting.report(UsageReporting.kResourceType_RobotDrive, getNumMotors(), UsageReporting.kRobotDrive_MecanumPolar);
            kMecanumPolar_Reported = true;
        }
        double frontLeftSpeed, rearLeftSpeed, frontRightSpeed, rearRightSpeed;
        // Normalized for full power along the Cartesian axes.
        magnitude = limit(magnitude) * Math.sqrt(2.0);
        // The rollers are at 45 degree angles.
        double dirInRad = (direction + 45.0) * 3.14159 / 180.0;
        double cosD = Math.cos(dirInRad);
        double sinD = Math.sin(dirInRad);

        double wheelSpeeds[] = new double[kMaxNumberOfMotors];
        wheelSpeeds[MotorType.kFrontLeft_val] = (sinD * magnitude + rotation);
        wheelSpeeds[MotorType.kFrontRight_val] = (cosD * magnitude - rotation);
        wheelSpeeds[MotorType.kRearLeft_val] = (cosD * magnitude + rotation);
        wheelSpeeds[MotorType.kRearRight_val] = (sinD * magnitude - rotation);

        normalize(wheelSpeeds);

        byte syncGroup = (byte)0x80;

        m_frontLeftMotor.set(wheelSpeeds[MotorType.kFrontLeft_val] * m_invertedMotors[MotorType.kFrontLeft_val] * m_maxOutput, syncGroup);
        m_frontRightMotor.set(wheelSpeeds[MotorType.kFrontRight_val] * m_invertedMotors[MotorType.kFrontRight_val] * m_maxOutput, syncGroup);
        m_rearLeftMotor.set(wheelSpeeds[MotorType.kRearLeft_val] * m_invertedMotors[MotorType.kRearLeft_val] * m_maxOutput, syncGroup);
        m_rearRightMotor.set(wheelSpeeds[MotorType.kRearRight_val] * m_invertedMotors[MotorType.kRearRight_val] * m_maxOutput, syncGroup);

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
