/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DriveControllers;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.can.CANNotInitializedException;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/** -------------------------------------------------------
 * @class DiscoDriveStandard
 * @purpose  Class for handling Robot drive The disco
 * drive class handles basic driving for a robot.
 * This is a 4 wheel holonomic drive setup. Each wheel is mounted
 * on a corner of the robot. This configuration allows to both
 * translate in the x and y direction and rotate at the same time.

@author Allen Gregory
@written Jan 22,2010
------------------------------------------------------- */
public class DiscoDriveStandard extends RobotDrive {

    private int NUMBER_OF_MOTORS = 4;

    public DiscoDriveStandard(SpeedController frontLeftMotor, SpeedController frontRightMotor,
            SpeedController rearRightMotor, SpeedController rearLeftMotor) {
        super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
    }

    /**
     * Holonomic Drive method for omni wheel robots
     *
     * Cartesian holonomic drive
     * @param x - desired x-output (left/right)
     * @param y - desired y-output (forward/backwards)
     * independent of the rotation rate.
     * @param rotation The rate of rotation for the robot that is completely independent of
     * the magnitude or direction.  [-1.0..1.0]
     */
    public void HolonomicDrive(double x, double y, double rotation, double offset) {
        double xIn = x;//limitToPercent(x);
        double yIn = y;//limitToPercent(y);
        // Negate y for the joystick.
        //yIn = -yIn; on button control, no need to negate the y joystick

        double wheelSpeeds[] = new double[NUMBER_OF_MOTORS];
        wheelSpeeds[MotorType.kFrontLeft.value] = xIn + yIn + rotation + offset;
        wheelSpeeds[MotorType.kFrontRight.value] = -xIn + yIn - rotation - offset;
        wheelSpeeds[MotorType.kRearRight.value] = xIn + yIn - rotation + offset;
        wheelSpeeds[MotorType.kRearLeft.value] = -xIn + yIn + rotation - offset;

        //capWheelSpeeds(wheelSpeeds);
        normalize(wheelSpeeds);

        m_frontLeftMotor.set(wheelSpeeds[MotorType.kFrontLeft.value] * m_invertedMotors[MotorType.kFrontLeft.value] * m_maxOutput);
        m_frontRightMotor.set(wheelSpeeds[MotorType.kFrontRight.value] * m_invertedMotors[MotorType.kFrontRight.value] * m_maxOutput);
        m_rearLeftMotor.set(wheelSpeeds[MotorType.kRearLeft.value] * m_invertedMotors[MotorType.kRearLeft.value] * m_maxOutput);
        m_rearRightMotor.set(wheelSpeeds[MotorType.kRearRight.value] * m_invertedMotors[MotorType.kRearRight.value] * m_maxOutput);

        /*if (m_controlMode == PWMJaguar.ControlMode.kPercentVbus) {
        setMotorsPercentVbus(wheelSpeeds[MotorType.kFrontLeft.value], wheelSpeeds[MotorType.kRearRight.value],
        wheelSpeeds[MotorType.kFrontRight.value], wheelSpeeds[MotorType.kRearLeft.value]);
        } else if (m_controlMode == PWMJaguar.ControlMode.kSpeed) {
        setMotorsSpeed(wheelSpeeds[MotorType.kFrontLeft.value], wheelSpeeds[MotorType.kRearRight.value],
        wheelSpeeds[MotorType.kFrontRight.value], wheelSpeeds[MotorType.kRearLeft.value]);
        }*/

    }

    public void capWheelSpeeds(double[] wheelSpeeds) {
        for(int k=0;k<wheelSpeeds.length;k++) {
            if(wheelSpeeds[k] > 1.0) {
                wheelSpeeds[k] = 1.0;
            } else if(wheelSpeeds[k] < -1.0) {
                wheelSpeeds[k] = -1.0;
            }
        }
    }

    public void HolonomicDrive(double x, double y, double rotation) {
        double xIn = x;//limitToPercent(x);
        double yIn = y;//limitToPercent(y);
        // Negate y for the joystick.
        yIn = -yIn;

        double wheelSpeeds[] = new double[NUMBER_OF_MOTORS];
        wheelSpeeds[MotorType.kFrontLeft.value] = xIn + yIn + rotation;
        wheelSpeeds[MotorType.kFrontRight.value] = -xIn + yIn - rotation;
        wheelSpeeds[MotorType.kRearRight.value] = xIn + yIn - rotation;
        wheelSpeeds[MotorType.kRearLeft.value] = -xIn + yIn + rotation;


        normalize(wheelSpeeds);

        m_frontLeftMotor.set(wheelSpeeds[MotorType.kFrontLeft.value] * m_invertedMotors[MotorType.kFrontLeft.value] * m_maxOutput);
        m_frontRightMotor.set(wheelSpeeds[MotorType.kFrontRight.value] * m_invertedMotors[MotorType.kFrontRight.value] * m_maxOutput);
        m_rearLeftMotor.set(wheelSpeeds[MotorType.kRearLeft.value] * m_invertedMotors[MotorType.kRearLeft.value] * m_maxOutput);
        m_rearRightMotor.set(wheelSpeeds[MotorType.kRearRight.value] * m_invertedMotors[MotorType.kRearRight.value] * m_maxOutput);

        /*if (m_controlMode == PWMJaguar.ControlMode.kPercentVbus) {
        setMotorsPercentVbus(wheelSpeeds[MotorType.kFrontLeft.value], wheelSpeeds[MotorType.kRearRight.value],
        wheelSpeeds[MotorType.kFrontRight.value], wheelSpeeds[MotorType.kRearLeft.value]);
        } else if (m_controlMode == PWMJaguar.ControlMode.kSpeed) {
        setMotorsSpeed(wheelSpeeds[MotorType.kFrontLeft.value], wheelSpeeds[MotorType.kRearRight.value],
        wheelSpeeds[MotorType.kFrontRight.value], wheelSpeeds[MotorType.kRearLeft.value]);
        }*/

    }

    /**
     * Holonomic Drive method for omni wheel robots
     *
     * This is a modified version of the WPILIB Mecannum code. The formula works but
     * there is no need to invert the rotations because you rotate but increasing the speed of every wheel
     *
     * @param magnitude The speed that the robot should drive in a given direction.  [-1.0..1.0]
     * @param direction The direction the robot should drive. The direction and maginitute are
     * independent of the rotation rate.
     * @param rotation The rate of rotation for the robot that is completely independent of
     * the magnitude or direction.  [-1.0..1.0]
     */
    public void holonomicDrive(double magnitude, double direction, double rotation) {
        // Normalized for full power along the Cartesian axes.
        magnitude = limit(magnitude) * Math.sqrt(2.0);
        // The rollers are at 45 degree angles.
        double dirInRad = (direction + 45.0) * 3.14159 / 180.0;
        double cosD = Math.cos(dirInRad);
        double sinD = Math.cos(dirInRad);

        double wheelSpeeds[] = new double[kMaxNumberOfMotors];
        wheelSpeeds[MotorType.kFrontLeft.value] = (sinD * magnitude + rotation);
        wheelSpeeds[MotorType.kRearRight.value] = (sinD * magnitude - rotation);
        wheelSpeeds[MotorType.kFrontRight.value] = (cosD * magnitude + rotation);
        wheelSpeeds[MotorType.kRearLeft.value] = (cosD * magnitude - rotation);




        normalize(wheelSpeeds);

        byte syncGroup = (byte) 0x80;

        m_frontLeftMotor.set(wheelSpeeds[MotorType.kFrontLeft.value] * m_invertedMotors[MotorType.kFrontLeft.value] * m_maxOutput, syncGroup);
        m_frontRightMotor.set(wheelSpeeds[MotorType.kFrontRight.value] * m_invertedMotors[MotorType.kFrontRight.value] * m_maxOutput, syncGroup);
        m_rearLeftMotor.set(wheelSpeeds[MotorType.kRearLeft.value] * m_invertedMotors[MotorType.kRearLeft.value] * m_maxOutput, syncGroup);
        m_rearRightMotor.set(wheelSpeeds[MotorType.kRearRight.value] * m_invertedMotors[MotorType.kRearRight.value] * m_maxOutput, syncGroup);

        if (m_isCANInitialized) {
            try {
                CANJaguar.updateSyncGroup(syncGroup);
            } catch (CANNotInitializedException e) {
                m_isCANInitialized = false;
            } catch (CANTimeoutException e) {
            }
        }

        if (m_safetyHelper != null) {
            m_safetyHelper.feed();
        }
    }

    /**
     * This class should be used for field centeric control of the robot.
     * It adds the gyro angle to the direction of the robot.
     * Ensure that the gyro angle is at zero when the robot is driving away from
     * the driver.
     * @param magnitude
     * @param direction
     * @param rotation
     * @param gyro
     */
    public void holonomicDrive(double magnitude, double direction, double rotation, Gyro gyro) {
        holonomicDrive(magnitude, direction + gyro.getAngle(), rotation);
    }

    /**
     * Sets individual motor speeds to certain values
     * Will invert the motors properly and limit them with setMaxOutput(double)
     * @param frontLeftSpeed
     * @param frontRightSpeed
     * @param rearLeftSpeed
     * @param rearRightSpeed
     */
    public void setMotorSpeeds(double frontLeftSpeed, double frontRightSpeed, double rearLeftSpeed, double rearRightSpeed) {
        m_frontLeftMotor.set(frontLeftSpeed * m_invertedMotors[MotorType.kFrontLeft.value] * m_maxOutput);
        m_frontRightMotor.set(frontRightSpeed * m_invertedMotors[MotorType.kFrontRight.value] * m_maxOutput);
        m_rearLeftMotor.set(rearLeftSpeed * m_invertedMotors[MotorType.kRearLeft.value] * m_maxOutput);
        m_rearRightMotor.set(rearRightSpeed * m_invertedMotors[MotorType.kRearRight.value] * m_maxOutput);
    }

    public void setNormalizeMotorSpeeds(double frontLeftSpeed, double frontRightSpeed, double rearLeftSpeed, double rearRightSpeed) {
        double[] wheelSpeeds = new double[4];
        wheelSpeeds[0] = frontLeftSpeed;
        wheelSpeeds[1] = frontRightSpeed;
        wheelSpeeds[2] = rearRightSpeed;
        wheelSpeeds[3] = rearLeftSpeed;

        normalize(wheelSpeeds);

        m_frontLeftMotor.set(wheelSpeeds[MotorType.kFrontLeft.value] * m_invertedMotors[MotorType.kFrontLeft.value] * m_maxOutput);
        m_frontRightMotor.set(wheelSpeeds[MotorType.kFrontRight.value] * m_invertedMotors[MotorType.kFrontRight.value] * m_maxOutput);
        m_rearRightMotor.set(wheelSpeeds[MotorType.kRearRight.value] * m_invertedMotors[MotorType.kRearRight.value] * m_maxOutput);
        m_rearLeftMotor.set(wheelSpeeds[MotorType.kRearLeft.value] * m_invertedMotors[MotorType.kRearLeft.value] * m_maxOutput);
    }
}
