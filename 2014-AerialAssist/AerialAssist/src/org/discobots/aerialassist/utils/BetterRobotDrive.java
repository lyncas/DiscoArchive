package org.discobots.aerialassist.utils;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.communication.UsageReporting;

public class BetterRobotDrive extends RobotDrive {

    static final int kFrontLeft_val = 0;
    static final int kFrontRight_val = 1;
    static final int kRearLeft_val = 2;
    static final int kRearRight_val = 3;

    public BetterRobotDrive(SpeedController frontLeftMotor, SpeedController rearLeftMotor, SpeedController frontRightMotor, SpeedController rearRightMotor) {
        super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
    }

    public void mecanumDrive_Polar(double magnitude, double direction, double rotation) {
        if (!kMecanumPolar_Reported) {
            UsageReporting.report(UsageReporting.kResourceType_RobotDrive, getNumMotors(), UsageReporting.kRobotDrive_MecanumPolar);
            kMecanumPolar_Reported = true;
        }
        // Normalized for full power along the Cartesian axes.
        magnitude = limit(magnitude) * Math.sqrt(2.0);
        // The rollers are at 45 degree angles.

        double dirInRad = (direction - 45) * 3.14159 / 180.0;    //I changed the +45 to -45.       
        double cosD = Math.cos(dirInRad);   //1; I switched the values.
        double sinD = Math.sin(dirInRad);   //1: I switched the values.

        double wheelSpeeds[] = new double[kMaxNumberOfMotors];
        wheelSpeeds[kFrontLeft_val] = (cosD * magnitude + rotation);   // I changed it from sinD * magnitude + rotation.
        wheelSpeeds[kFrontRight_val] = (sinD * magnitude - rotation);  // I changed it from cosD * magnitude - rotation.
        wheelSpeeds[kRearLeft_val] = (sinD * magnitude + rotation);    // I changed it from cosD * magnitude + rotation.
        wheelSpeeds[kRearRight_val] = (cosD * magnitude - rotation);   // I changed it from sinD * magnitude - rotation.

        normalize(wheelSpeeds);

        byte syncGroup = (byte) 0x80;

        m_frontLeftMotor.set(wheelSpeeds[kFrontLeft_val] * m_invertedMotors[kFrontLeft_val] * m_maxOutput, syncGroup);
        m_frontRightMotor.set(wheelSpeeds[kFrontRight_val] * m_invertedMotors[kFrontRight_val] * m_maxOutput, syncGroup);
        m_rearLeftMotor.set(wheelSpeeds[kRearLeft_val] * m_invertedMotors[kRearLeft_val] * m_maxOutput, syncGroup);
        m_rearRightMotor.set(wheelSpeeds[kRearRight_val] * m_invertedMotors[kRearRight_val] * m_maxOutput, syncGroup);
    }
}
