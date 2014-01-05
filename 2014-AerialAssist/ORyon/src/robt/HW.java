package robt;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class HW {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static final int leftMotor = 1;
    // public static final int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;
    
    public static final int 
            left1slot=1,    left1channel=1,
            left2slot=1,    left2channel=2,
            right1slot=1,   right1channel=3,
            right2slot=1,   right2channel=4,
            
            intakslot=1,    intakechannel=5;
    
    
    public static final int 
            compressorSlot=1,   compressorChannel=1;
    
    public static final int armSolenoid1Channel=1,
                            armSolenoid2Channel=2;
    
    public static final int 
            presssureSwitchSlot=1,  pressureSwitchChannle=1;
}
