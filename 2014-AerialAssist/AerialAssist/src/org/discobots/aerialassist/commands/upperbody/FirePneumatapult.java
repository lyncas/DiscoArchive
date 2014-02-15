/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands.upperbody;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Seth
 */
public class FirePneumatapult extends CommandBase {
    
    private boolean shoot;
    public int count;
    public static final boolean FIRE = true;
    public static final boolean LOAD = false;
    
    
    public FirePneumatapult(boolean fire) {
        requires(pneumatapultSub);
        shoot = fire;
        count=0;
    }

    protected void initialize() {
    }

    protected void execute() {
       count++;
       SmartDashboard.putBoolean("Catapult status", shoot);
       if (rollerSub.isExtended()) 
        {
            if(count%5<=3)
                pneumatapultSub.fire(shoot);
            else
                pneumatapultSub.fire(false);
            if (!compressorSub.check())
            {
                compressorSub.on();
            }
        } 
       else
       {
           pneumatapultSub.fire(false);
       }
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
