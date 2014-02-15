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
    public long startTime;
    private final long maxRunTime;
    public static final boolean FIRE = true;
    public static final boolean LOAD = false;
    
    
    public FirePneumatapult(boolean fire) {
        requires(pneumatapultSub);
        shoot = fire;
        count=0;
        maxRunTime=1000;
    }

    protected void initialize() {
<<<<<<< HEAD
        startTime = System.currentTimeMillis();
=======
>>>>>>> a11319d3199eafd953a8702ae4f67907ecd86b02
        startTime=System.currentTimeMillis();
    }

    protected void execute() {
       count++;
       SmartDashboard.putBoolean("Catapult status", shoot);
       if (rollerSub.isExtended()) 
        {
<<<<<<< HEAD
                if(count%5<3)
=======
                if(count%5<=3)
>>>>>>> a11319d3199eafd953a8702ae4f67907ecd86b02
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
<<<<<<< HEAD
        return System.currentTimeMillis() - startTime > maxRunTime;
=======
        return System.currentTimeMillis()-startTime> maxRunTime;
>>>>>>> a11319d3199eafd953a8702ae4f67907ecd86b02
    }

    protected void end() {
        pneumatapultSub.fire(false);
    }

    protected void interrupted() {
    }
}
