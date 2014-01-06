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
 * @author Developer
 */
public class CompressorSub extends Subsystem {
    Compressor comp=new Compressor(HW.presssureSwitchSlot,HW.pressureSwitchChannel,HW.compressorSlot,HW.compressorChannel);
    DoubleSolenoid armSolenoid=new DoubleSolenoid(HW.armSolenoid1Channel,HW.armSolenoid2Channel);

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
}
