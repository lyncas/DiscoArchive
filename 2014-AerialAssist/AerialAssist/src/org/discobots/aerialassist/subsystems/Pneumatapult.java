/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import org.discobots.aerialassist.HW;

/**
 *
 * @author Seth
 */
public class Pneumatapult extends Subsystem {
    
    private Solenoid fire1;
    private Solenoid fire2;
    private int mode;
    public void initDefaultCommand() {
    }
    
    public Pneumatapult() {
        fire1 = new Solenoid(HW.pneumatapultSolenoidA);
        fire2 = new Solenoid(HW.pneumatapultSolenoidB);
        mode = 2;
    }

    public void fire(boolean shoot) {
        fire1.set(shoot);
        fire2.set(shoot);
    }
    public int getMode(){
        return mode;
    }
    public void changeMode(){
        mode++;
        mode=mode%3;
    }
}
