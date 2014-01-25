
package org.discobots.aerialassist;
import org.discobots.aerialassist.utils.GamePad;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.discobots.aerialassist.commands.Catapult;
import org.discobots.aerialassist.commands.Changedrive;
import org.discobots.aerialassist.commands.Hold;
import org.discobots.aerialassist.controllers.FixAngle;
import org.discobots.aerialassist.subsystems.Drivetrain;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {    
    GamePad controller=new GamePad(1,GamePad.MODE_D);
    Button A= new JoystickButton(controller,controller.BTN_A);
    Button B= new JoystickButton(controller,controller.BTN_B);
    Button X= new JoystickButton(controller,controller.BTN_X);
    Button Y= new JoystickButton(controller,controller.BTN_Y);
    Button LBump= new JoystickButton(controller,controller.BUMPER_L);
    Button RBump= new JoystickButton(controller,controller.BUMPER_R);
    Button LTrig= new JoystickButton(controller,controller.TRIGGER_L);
    Button RTrig= new JoystickButton(controller,controller.TRIGGER_R);

    public OI(){
        A.whenPressed(new Changedrive(Drivetrain.MECANUM));
        B.whenPressed(new Changedrive(Drivetrain.TRACTION));
        X.whenPressed(new FixAngle());
        LTrig.whenPressed(new Hold());
        RTrig.whenPressed(new Catapult());
    }
    
    public GamePad getGP(){
        return controller;
    }
}

