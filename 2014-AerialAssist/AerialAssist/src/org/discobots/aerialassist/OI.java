
package org.discobots.aerialassist;
import org.discobots.aerialassist.utils.GamePad;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.discobots.aerialassist.commands.motortest.Move;
import org.discobots.aerialassist.commands.drive.SwitchDrive;
import org.discobots.aerialassist.commands.motortest.Hold;
import org.discobots.aerialassist.commands.drive.FixAngle;
import org.discobots.aerialassist.subsystems.Drivetrain;
import org.discobots.aerialassist.utils.GamePad.AxisButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {    
    private GamePad gp1 = new GamePad(1, GamePad.MODE_D);
    private GamePad gp2 = new GamePad(2, GamePad.MODE_D);
    // JOYSTICK 1
    private Button b_dpadU = new AxisButton(gp1, GamePad.DPAD_Y_U);
    private Button b_dpadD = new AxisButton(gp1, GamePad.DPAD_Y_D);
    private Button b_dpadR = new AxisButton(gp1, GamePad.DPAD_X_R);
    private Button b_dpadL = new AxisButton(gp1, GamePad.DPAD_X_L);
    private Button b_bumpR = new JoystickButton(gp1, gp1.BUMPER_R);
    private Button b_bumpL = new JoystickButton(gp1, gp1.BUMPER_L);
    private Button b_trigR = new JoystickButton(gp1, gp1.TRIGGER_R);
    private Button b_trigL = new JoystickButton(gp1, gp1.TRIGGER_L);
    private Button b_sBack = new JoystickButton(gp1, gp1.BTN_BACK);
    private Button b_sStar = new JoystickButton(gp1, gp1.BTN_START);
    private Button b_btnA = new JoystickButton(gp1, gp1.BTN_A);
    private Button b_btnX = new JoystickButton(gp1, gp1.BTN_X);
    private Button b_btnB = new JoystickButton(gp1, gp1.BTN_B);
    private Button b_btnY = new JoystickButton(gp1, gp1.BTN_Y);
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
    private Button b2_sBack = new JoystickButton(gp2, gp2.BTN_BACK);
    private Button b2_sStar = new JoystickButton(gp2, gp2.BTN_START);
    private Button b2_btnA = new JoystickButton(gp2, gp2.BTN_A);
    private Button b2_btnX = new JoystickButton(gp2, gp2.BTN_X);
    private Button b2_btnB = new JoystickButton(gp2, gp2.BTN_B);
    private Button b2_btnY = new JoystickButton(gp2, gp2.BTN_Y);
    private Button b2_clicR = new JoystickButton(gp2, gp2.CLICK_R);
    private Button b2_clicL = new JoystickButton(gp2, gp2.CLICK_L);

    public OI() {
        mapButtons();
    }

    private void mapButtons() {
        b_btnB.whenPressed(new SwitchDrive());
        b_btnX.whenPressed(new FixAngle());
        b_trigL.whenPressed(new Hold());
        b_trigL.whenPressed(new Move(400, -1));
        b_bumpR.whenPressed(new Move(350,-1));
        b_bumpL.whenPressed(new Move(350,-1));
        b_btnY.whenPressed(new Move(10000,-1));
        b_btnA.whenPressed(new Move(10000,1));
    }
     public double getRawAnalogStickALX() {
        return gp1.getLX();
    }

    public double getRawAnalogStickALY() {
        return gp1.getLY();
    }

    public double getRawAnalogStickARX() {
        return gp1.getRX();
    }

    public double getRawAnalogStickARY() {
        return gp1.getRY();
    }

    public double getRawAnalogStickBLX() {
        return gp2.getLX();
    }

    public double getRawAnalogStickBLY() {
        return gp2.getLY();
    }

    public double getRawAnalogStickBRX() {
        return gp2.getRX();
    }

    public double getRawAnalogStickBRY() {
        return gp2.getRY();
    }
}
