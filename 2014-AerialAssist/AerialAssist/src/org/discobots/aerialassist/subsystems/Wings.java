/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.aerialassist.HW;

/**
 *
 * @author Seth
 */
public class Wings extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private Solenoid extend;
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public Wings() {
        extend = new Solenoid(HW.wingsSolenoid);
    }
    
    public void setExtended(boolean on) {
        extend.set(on);
    }
    
    public boolean isExtended() {
        return extend.get();   //The robot thinks that down is false.
    }
    
}
