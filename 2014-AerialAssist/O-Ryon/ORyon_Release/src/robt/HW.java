package robt;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class HW {
   
    //MOTORS
    public static final int 
            left1slot=1,    left1channel=1,
            left2slot=1,    left2channel=2,
            right1slot=1,   right1channel=3,
            right2slot=1,   right2channel=4,
            
            intakslot=1,    intakechannel=5,
            
            flip1slot=1,    flip1channel=7,
            flip2slot=1,    flip2channel=8;
    
    public static final int 
            compressorslot=1,   compressorchannel=1;
    
    public static final int armsolenoid1channel=1,  armsolenoid2channel=2,
                            armstage2channel1=3,    armstage2channel2=4;
    
    public static final int 
            presssureswitchslot=1,  pressureswitchchannel=1,
            ballsensorslot=1,       ballsensorchannel=2;
}
