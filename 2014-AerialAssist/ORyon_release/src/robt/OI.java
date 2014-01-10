
package robt;

import robt.commands.compress.ToggleCompressor;
import robt.commands.arm.ArmUp;
import robt.commands.arm.ArmDown;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import robt.commands.arm.ArmStage2Down;
import robt.commands.arm.ArmStage2Up;
import robt.utils.GamePad;
import robt.utils.GamePad.AxisButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    GamePad gp1=new GamePad(1,GamePad.MODE_D);
    
    public Joystick getJoy1() {
        return gp1;
    }
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // Another type of button you can create is a DigitalIOButton, which is
    // a button or switch hooked up to the cypress module. These are useful if
    // you want to build a customized operator interface.
    // Button button = new DigitalIOButton(1);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
    private Button b_A=new JoystickButton(gp1,gp1.BTN_A);
    private Button b_B=new JoystickButton(gp1,gp1.BTN_B);
    private Button b_RB=new JoystickButton(gp1,gp1.BUMPER_R);
    private Button b_LB=new JoystickButton(gp1,gp1.BUMPER_L);
    private Button b_DPAD_Y_UP=new AxisButton(gp1,GamePad.DPAD_Y_U);
    private Button b_DPAD_Y_DN=new AxisButton(gp1,GamePad.DPAD_Y_D);

    
    public OI(){
        //b_A.whenPressed(new ToggleIntake());
        b_B.whenPressed(new ToggleCompressor());
        b_RB.whenPressed(new ArmUp());
        b_LB.whenPressed(new ArmDown());
        b_DPAD_Y_UP.whenPressed(new ArmStage2Up());
        b_DPAD_Y_DN.whenPressed(new ArmStage2Down());
    }
}

