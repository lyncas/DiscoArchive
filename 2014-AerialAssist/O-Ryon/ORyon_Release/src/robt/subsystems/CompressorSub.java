/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robt.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import robt.HW;

/**
 *
 * @author Sam Dietrich / Team ORyon
 */
public class CompressorSub extends Subsystem {
    Compressor comp=new Compressor(HW.presssureswitchslot,HW.pressureswitchchannel,HW.compressorslot,HW.compressorchannel);
    //These should be in their own subsustem, in best practice
    DoubleSolenoid armSolenoid=new DoubleSolenoid(HW.armsolenoid1channel,HW.armsolenoid2channel);
    DoubleSolenoid armStage2=new DoubleSolenoid(HW.armstage2channel1,HW.armstage2channel2);

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void enable(){
        comp.start();
    }
    
    public void disable(){
        comp.stop();
    }
    
    public boolean getEnabled(){
        return comp.enabled();
    }
    
    
    public void armUp(){
        armSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    
    public void armDown(){
        armSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    
    public void armStage2Up(){
        armStage2.set(DoubleSolenoid.Value.kForward);
    }
    
    public void armStage2Down(){
        armStage2.set(DoubleSolenoid.Value.kReverse);
    }

    public boolean getPressureSwitch() {
        return comp.getPressureSwitchValue();
    }
}
