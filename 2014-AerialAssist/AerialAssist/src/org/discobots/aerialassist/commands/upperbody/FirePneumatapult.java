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
    private int check;
    public int count;
    public long startTime;
    private final long maxRunTime;
    public static final boolean FIRE = true;
    public static final boolean LOAD = false;
    public static final int STUTTER = 0;
    public static final int HALF = 1;
    public static final int FULL = 2;

    
    
    public FirePneumatapult(boolean fire, int ch) {
        requires(pneumatapultSub);
        check = ch;
        shoot = fire;
        count=0;
        maxRunTime=1000;
    }

    protected void initialize() {
        startTime=System.currentTimeMillis();
    }

    protected void execute() {
       count+=20;
       SmartDashboard.putBoolean("Catapult status", shoot);
       if (rollerSub.isExtended()){      
                if(check==0){
                    if(count%200<180)//200 is one fifth of the total execution time, the solenoid starts and stops 5 times now
                        pneumatapultSub.fire(shoot);
                    else
                        pneumatapultSub.fire(false);
                }
                if(check==1){
                    if(count<=maxRunTime)
                        pneumatapultSub.fire(shoot);
                    else
                        pneumatapultSub.fire(false);
                }
                if(check==2){
                    if(count<=maxRunTime)
                        pneumatapultSub.fire(shoot);
                    else
                        pneumatapultSub.fire(false);
                }
                if (!compressorSub.check()){
                    compressorSub.on();
                }
        } 
       else
       {
           pneumatapultSub.fire(false);
       }
    }

    protected boolean isFinished() {
        return System.currentTimeMillis()-startTime> maxRunTime;
    }

    protected void end() {
        pneumatapultSub.fire(false);
    }

    protected void interrupted() {
        end();
    }
}
