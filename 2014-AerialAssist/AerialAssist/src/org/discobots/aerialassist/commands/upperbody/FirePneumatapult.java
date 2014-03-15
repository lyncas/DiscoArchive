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
    private long maxRunTime;
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
        switch (check){
            case 0:
                maxRunTime=500;
                break;
            case 1:
                maxRunTime=1000;
                break;
            case 2:
                maxRunTime=1500;
                break;
            case 3:
                maxRunTime=1500;
                break;
            case 4:
                maxRunTime=250;
                break;
        }
    }

    protected void initialize() {
        startTime=System.currentTimeMillis();
        System.out.println("Firing a shot of type " + check);
    }

    protected void execute() {
        if (rollerSub.isExtended()) {
            switch (check){
            case 0:
                pneumatapultSub.fire(shoot);
                break;
            case 1:
                pneumatapultSub.fire(shoot);
                break;
            case 2:
                pneumatapultSub.fire(shoot);
                break;
            case 3:
                if (count%80 != 0)
                    pneumatapultSub.fire(shoot);
                else
                    pneumatapultSub.fire(false);
                break;
            case 4:
                pneumatapultSub.fire(shoot);
                break;
        }
            pneumatapultSub.fire(shoot);
            count+=20;
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
