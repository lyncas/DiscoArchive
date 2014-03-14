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
        check = pneumatapultSub.getMode();
        check = ch;
        shoot = fire;
        count=0;
        maxRunTime=1500;
    }

    protected void initialize() {
        startTime=System.currentTimeMillis();
    }

    protected void execute() {
        if (rollerSub.isExtended()) {
            pneumatapultSub.fire(shoot);
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
