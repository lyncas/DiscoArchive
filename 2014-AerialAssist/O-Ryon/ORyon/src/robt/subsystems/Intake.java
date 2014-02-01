/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robt.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import robt.HW;
import robt.commands.arm.VariIntake;

/**
 *
 * @author Developer
 */
public class Intake extends Subsystem {
    Talon intakemotor=new Talon(HW.intakslot,HW.intakechannel);
    private double power=0.5;
    private boolean enabled=false;
    

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new VariIntake());
    }
    
    public void setIntake(double power){
        this.power=power;
    }
    
    public void intakeOn(){
        intakemotor.set(power);
        enabled=true;
    }
    
    public void intakeOff(){
        intakemotor.set(0);
        enabled=false;
    }
    
    public boolean getEnabled(){
        return enabled;
    }
}
