package org.discobots.frc.ascent.framework;

public final class Constants {

    /* PID CONSTANTS */
    public static final double 
            ClosedTank_P = 0.08333,
            ClosedTank_I = 0.0,
            ClosedTank_D = 0.0,
            ClosedTank_MaxVelocityInFeetPerSecond = 12.0; // Max FeetPerSecond
    /* SENSOR CONSTANTS */
    public static final int 
            Encoder_SampleSize = 10;
    /* SHOOTER CONSTANTS */
    public static final int 
            Shooter_DefaultSetpointPyramidRPM = 5700,
            Shooter_DefaultSetpointFullCourtRPM = 7000;
    public static final double 
            Shooter_DefaultSetpointPyramidPWM = 1.0;
    /* DRIVE CONSTANTS */
    public static final double
            SkidSteer_ErrorAngleThreshold = 5;
    
    
    public static final boolean EXPERIMENTAL_MODE = false;
    public static final boolean DEBUGGING_MODE = false;
}
