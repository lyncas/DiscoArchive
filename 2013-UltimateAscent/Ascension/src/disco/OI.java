package disco;

import disco.commands.drivetrain.*;
import disco.commands.pneumatics.*;
import disco.commands.shooter.*;
import disco.utils.GamePad;
import disco.utils.GamePad.AxisButton;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    // Joysticks
    private int gp1_mode = GamePad.MODE_D;
    private GamePad gp1 = new GamePad(1, gp1_mode);
    private int gp2_mode = GamePad.MODE_D;
    private GamePad gp2 = new GamePad(2, gp2_mode);
    // JOYSTICK 1
    private Button b_dpadU = new AxisButton(gp1, GamePad.DPAD_Y_U);
    private Button b_dpadD = new AxisButton(gp1, GamePad.DPAD_Y_D);
    private Button b_dpadR = new AxisButton(gp1, GamePad.DPAD_X_R);
    private Button b_dpadL = new AxisButton(gp1, GamePad.DPAD_X_L);
    private Button b_bumpR = new JoystickButton(gp1, gp1.BUMPER_R);
    private Button b_bumpL = new JoystickButton(gp1, gp1.BUMPER_L);
    private Button b_trigR = new JoystickButton(gp1, gp1.TRIGGER_R);
    private Button b_trigL = new JoystickButton(gp1, gp1.TRIGGER_L);
    private Button b_Back = new JoystickButton(gp1, gp1.BTN_BACK);
    private Button b_Start = new JoystickButton(gp1, gp1.BTN_START);
    private Button b_A = new JoystickButton(gp1, gp1.BTN_A);
    private Button b_X = new JoystickButton(gp1, gp1.BTN_X);
    private Button b_B = new JoystickButton(gp1, gp1.BTN_B);
    private Button b_Y = new JoystickButton(gp1, gp1.BTN_Y);
    private Button b_clicR = new JoystickButton(gp1, gp1.CLICK_R);
    private Button b_clicL = new JoystickButton(gp1, gp1.CLICK_L);
    // JOYSTICK 2
    private Button b2_dpadU = new AxisButton(gp2, GamePad.DPAD_Y_U);
    private Button b2_dpadD = new AxisButton(gp2, GamePad.DPAD_Y_D);
    private Button b2_dpadR = new AxisButton(gp2, GamePad.DPAD_X_R);
    private Button b2_dpadL = new AxisButton(gp2, GamePad.DPAD_X_L);
    private Button b2_bumpR = new JoystickButton(gp2, gp2.BUMPER_R);
    private Button b2_bumpL = new JoystickButton(gp2, gp2.BUMPER_L);
    private Button b2_trigR = new JoystickButton(gp2, gp2.TRIGGER_R);
    private Button b2_trigL = new JoystickButton(gp2, gp2.TRIGGER_L);
    private Button b2_Back = new JoystickButton(gp2, gp2.BTN_BACK);
    private Button b2_Start = new JoystickButton(gp2, gp2.BTN_START);
    private Button b2_A = new JoystickButton(gp2, gp2.BTN_A);
    private Button b2_X = new JoystickButton(gp2, gp2.BTN_X);
    private Button b2_B = new JoystickButton(gp2, gp2.BTN_B);
    private Button b2_Y = new JoystickButton(gp2, gp2.BTN_Y);
    private Button b2_clicR = new JoystickButton(gp2, gp2.CLICK_R);
    private Button b2_clicL = new JoystickButton(gp2, gp2.CLICK_L);

    public static final int MODE_OLD = 0;
    public static final int MODE_ANDY = 1;
    public static final int MODE_SPLIT = 2;

    private int joyMode = MODE_OLD;

    public OI() {
        switch (joyMode){
                case MODE_OLD:
                    oldMap();
                    break;
                case MODE_ANDY:
                    andyMap();
                    break;
                case MODE_SPLIT:
                    splitMap();
                    break;
		default:
		    oldMap();
		    break;
        }

    }
    public final void oldMap() {
	/*
	 * Shooter
	 */
        b_dpadU.whenPressed(new ShooterIncDiff());
        b2_dpadD.whenPressed(new ShooterDecDiff());
        b2_bumpR.whenPressed(new ShooterToggle());
        b2_dpadR.whenPressed(new ShooterInc());
        b2_dpadL.whenPressed(new ShooterDec());
        b2_trigR.whenPressed(new Shoot());
        b2_trigL.whenPressed(new Clear());
        b2_Back.whenPressed(new cycleShooter());
        b2_A.whenPressed(new AutoShoot(5,1000,250));

	/*
	 * Drivetrain
	 */
        b_Start.whenPressed(new cycleDrive());

	/*
	 * Pneumatics
	 */
	b_X.whenPressed(new ToggleCompressor());

    }
    public final void andyMap() {
        /*
	 * Shooter
	 */
        b_dpadU.whenPressed(new ShooterIncDiff());
        b_dpadD.whenPressed(new ShooterDecDiff());
        b_bumpR.whenPressed(new ShooterToggle());
        b_dpadR.whenPressed(new ShooterInc());
        b_dpadL.whenPressed(new ShooterDec());
        b_trigR.whenPressed(new Shoot());
        b_B.whenPressed(new Clear());
        b_Back.whenPressed(new cycleShooter());

	/*
	 * Drivetrain
	 */
	b_Start.whenPressed(new cycleDrive());

        /*
	 * Pneumatics
	 */
        b_X.whenPressed(new ToggleCompressor());
    }
    public final void splitMap() {
        /*
	 * Shooter
	 */
        b2_dpadU.whenPressed(new ShooterIncDiff());
        b2_dpadD.whenPressed(new ShooterDecDiff());
        b2_dpadR.whenPressed(new ShooterInc());
        b2_dpadL.whenPressed(new ShooterDec());
        b2_Start.whenPressed(new cycleShooter());
        b2_bumpR.whenPressed(new ShooterToggle());
        b2_trigL.whenPressed(new Clear());
        b2_trigR.whenPressed(new Shoot());

	/*
	 * Drivetrain
	 */
        b_A.whenPressed(new NegateDrive());
        b_Start.whenPressed(new cycleDrive());
	//HW: not supported
	    //b_trigL.whileHeld(new ShiftDown());

        /*
	 * Pneumatics
	 */
        b2_Y.whenPressed(new ToggleCompressor());


    }

    public void setMode(int mode) {
        this.joyMode = mode;
    }
    public Joystick getJoy1() {
        return gp1;
    }
    public Joystick getJoy2() {
        return gp2;
    }
}

