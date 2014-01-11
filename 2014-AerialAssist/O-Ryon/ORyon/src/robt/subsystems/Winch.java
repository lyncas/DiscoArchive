/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robt.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import robt.HW;
import robt.utils.SwitchButton;

/**
 *
 * @author Developer
 */
public class Winch extends Subsystem {
    Talon motor1/*=new Talon(HW.winch1slot,HW.winch1channel)*/;
    Talon motor2/*=new Talon(HW.winch2slot,HW.winch2channel)*/;
    Talon motor3/*=new Talon(HW.winch3slot,HW.winch3channel)*/;
    
    DoubleSolenoid lock=new DoubleSolenoid(HW.lockSolenoid1Channel,HW.lockSolenoid2Channel);
    DoubleSolenoid release=new DoubleSolenoid(HW.releaseSolenoid1Channel,HW.releaseSolenoid2Channel);
    
    //Moved to OI
//    DigitalInput cocked=new DigitalInput(HW.winchLimitSlot,HW.winchLimitChannel);
//    SwitchButton limitButton=new SwitchButton(cocked);

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    //engage pin lock
    public void lock(){
        lock.set(DoubleSolenoid.Value.kForward);
    }
    
    //release pin lock
    public void unlock(){
        lock.set(DoubleSolenoid.Value.kReverse);
    }
    
    //engage winch motors
    public void engage(){
        release.set(DoubleSolenoid.Value.kForward);
    }
    
    //release winch motors
    public void release(){
        release.set(DoubleSolenoid.Value.kReverse);
    }
    
    public void winchIn(){
        motor1.set(1);
        motor2.set(1);
        motor3.set(1);
    }
    
    public void winchStop(){
        motor1.set(0);
        motor2.set(0);
        motor3.set(0);
    }
}
