
package robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.DigitalIOButton;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
<<<<<<< HEAD
=======
import robot.commands.Changedrive;
>>>>>>> d08ce8c1ad5363ee1c93e092937f4e7b4e903c33
import robot.commands.Changepower;
import robot.commands.Changetime;
import robot.commands.Hold;
import robot.commands.Threesec;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
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
    GamePad controller=new GamePad(1,GamePad.MODE_D);
    GamePad controller2=new GamePad(2,GamePad.MODE_D);
    Button b=new JoystickButton(controller,controller.BTN_B);
    Button a=new JoystickButton(controller,controller.BTN_A);
    Button triggerR= new JoystickButton(controller,controller.TRIGGER_R);
    Button bumperR= new JoystickButton(controller,controller.BUMPER_R);
    Button triggerL= new JoystickButton(controller,controller.TRIGGER_L);
    Button bumperL= new JoystickButton(controller,controller.BUMPER_L);
<<<<<<< HEAD
=======
    Button X=new JoystickButton(controller,controller.BTN_X);
>>>>>>> d08ce8c1ad5363ee1c93e092937f4e7b4e903c33
    
    
    public OI(){
        b.whenPressed(new Threesec());
        a.whileHeld(new Hold());
        triggerR.whenPressed(new Changepower(.05));
        bumperR.whenPressed(new Changepower(-.05));
        triggerL.whenPressed(new Changetime(5));
        bumperL.whenPressed(new Changetime(-5));
<<<<<<< HEAD
=======
        X.whenPressed(new Changedrive());
>>>>>>> d08ce8c1ad5363ee1c93e092937f4e7b4e903c33
    }
    public GamePad getGP(){
        return controller;
    }
    
}


