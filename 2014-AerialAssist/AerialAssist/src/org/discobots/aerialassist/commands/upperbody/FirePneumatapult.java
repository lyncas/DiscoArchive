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
    public static final boolean FIRE = true;
    public static final boolean LOAD = false;
    private final long maxRunTime;
    private long startTime;
    
    public FirePneumatapult(boolean fire) {
        requires(pneumatapultSub);
        shoot = fire;
        maxRunTime=1000;
    }

    protected void initialize() {
        startTime = System.currentTimeMillis();
    }

    protected void execute() {
       SmartDashboard.putBoolean("Catapult status", shoot);
       if (rollerSub.isExtended()) 
        {
            pneumatapultSub.fire(shoot);
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
        return System.currentTimeMillis() - startTime > maxRunTime;
    }

    protected void end() {
        pneumatapultSub.fire(false);
    }

    protected void interrupted() {
    }
}
