package org.discobots.frc.ascent;

/**
 * The HW is a mapping from the ports sensors and actuators are wired into to a
 * variable name. This provides flexibility changing wiring, makes checking the
 * wiring easier and significantly reduces the number of magic numbers floating
 * around.
 */
public class HW {

    public static final double wheelRadius = 2.0; //in
    public static final double wheelSeparation = 18.0; //in
    public static final double driveReduction = 1 / 3.0 * 1 / 2.0;  //KNOWN INCORRECT
    public static final double maxFPS = 8; // THEORETICALLY CORRECT
    public static final double encoderTicksPerRev = 256 / driveReduction;
    public static final double distancePerRev = 2 * Math.PI * wheelRadius;
    public static final double distancePerPulse = distancePerRev / encoderTicksPerRev;
    
    /* SIDECARS & MISC *////////////////////////////////////////////////////////
    public static final int digitalModuleSlot = 1;
    public static final byte localI2CAddress = 85; // 01010101
    public static final byte arduinoI2CAddress = 21 << 1 + 0; // 00010101 << 1 + 0  // Includes directional data. Add 1 to switch direction.
    /* MOTORS */////////////////////////////////////////////////////////////////
    public static final int motorDriveLeft1Slot = 1, motorDriveLeft1Channel = 1,
            motorDriveLeft2Slot = 1, motorDriveLeft2Channel = 2,
            motorDriveRight1Slot = 1, motorDriveRight1Channel = 3,
            motorDriveRight2Slot = 1, motorDriveRight2Channel = 4,
            motorShooterFrontSlot = 1, motorShooterFrontChannel = 5,
            motorShooterBackSlot = 1, motorShooterBackChannel = 6;
    /* RELAYS */////////////////////////////////////////////////////////////////
    public static final int compressorSlot = 1, compressorChannel = 8;
    /* SOLENOIDS *//////////////////////////////////////////////////////////////
    public static final int solenoidShootAChannel = 6,
            solenoidShootBChannel = 5,
            solenoidShiftLeftChannel = 2,
            solenoidShiftRightChannel = 3,
            encoderFrontPowerChannel = 7,
            encoderBackPowerChannel = 8;
    /* SENSORS *////////////////////////////////////////////////////////////////
    public static final int encoderDriveLeftSlot = 1, encoderDriveLeftAChannel = 8,
            encoderDriveLeftBChannel = 9,
            encoderDriveRightSlot = 1, encoderDriveRightAChannel = 4,
            encoderDriveRightBChannel = 5,
            encoderShooterFrontSlot = 1, encoderShooterFrontChannel = 1,
            encoderShooterBackSlot = 1, encoderShooterBackChannel = 2,
            pressureswitchSlot = 1, pressureswitchChannel = 14,
            limitswitchDriveLeftSlot = 1, limitswitchDriveLeftChannel = 7,
            limitswitchDriveRightSlot = 1, limitswitchDriveRightChannel = 12,
            gyroDriveSlot = 1, gyroDriveChannel = 2;
}
