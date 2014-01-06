/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robt.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import robt.HW;
import robt.commands.flip.VariFlip;

/**
 *
 * @author Developer
 */
public class Flipper extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Talon flip=new Talon(HW.flip1slot,HW.flip1channel);

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new VariFlip());
    }
    
    public void setPower(double power){
        flip.set(power);
    }
}
