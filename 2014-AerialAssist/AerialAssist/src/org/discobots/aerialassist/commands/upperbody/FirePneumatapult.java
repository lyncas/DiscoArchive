package org.discobots.aerialassist.commands.upperbody;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.discobots.aerialassist.commands.CommandBase;

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
        count = 0;
        maxRunTime = 1500;
        switch (check) {
            case 0:
                maxRunTime = 300;
                break;
            case 1:
                maxRunTime = 210;
                break;
            case 2:
                maxRunTime = 1000;
                break;
            case 3:
                maxRunTime = 1500;
                break;
            case 4:
                maxRunTime = (int) SmartDashboard.getNumber("FirePneumatapult Shot Time", 1500);
                break;
            case 5:
                maxRunTime = 1500;
                break;
        }
    }

    protected void initialize() {
        startTime = System.currentTimeMillis();
        
        System.out.println("Firing a shot of type " + check);
    }

    protected void execute() {
        if (rollerSub.isExtended()) {
            switch (check) {
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
                    pneumatapultSub.fire(shoot);
                    break;
                case 4:
                    pneumatapultSub.fire(shoot);
                    break;
                case 5:
                    if (SmartDashboard.getNumber("FirePneumatapult Cycle", 4)!= 0 && count%(SmartDashboard.getNumber("FirePneumatapult Cycle", 4)*20)==0)
                        pneumatapultSub.fire(false);
                    else
                        pneumatapultSub.fire(shoot);
                    break;
            }
            pneumatapultSub.fire(shoot);
            count += 20;
        }
    }

    protected boolean isFinished() {
        return System.currentTimeMillis() - startTime > maxRunTime;
    }

    protected void end() {
        System.out.println("Done firing in " + (System.currentTimeMillis() - startTime) + " milliseconds");
        pneumatapultSub.fire(false);
    }

    protected void interrupted() {
        end();
    }
}
