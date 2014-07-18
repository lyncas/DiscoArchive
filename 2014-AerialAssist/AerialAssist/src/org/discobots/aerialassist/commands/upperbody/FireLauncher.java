package org.discobots.aerialassist.commands.upperbody;

import org.discobots.aerialassist.commands.CommandBase;

public class FireLauncher extends CommandBase {

    private boolean shoot;
    private int check;
    public long startTime;
    private long maxRunTime;
    public static final boolean FIRE = true;
    public static final boolean LOAD = false;

    public FireLauncher(boolean fire, int ch) {
        requires(pneumatapultSub);
        check = ch;
        shoot = fire;
        maxRunTime = 1500;
        switch (check) {
            case 0:
                maxRunTime = 150;
                break;
            case 1:
                maxRunTime = 200;
                break;
            case 2:
                maxRunTime = 170;
                break;
            case 3:
                maxRunTime = 500;
                break;
            case 4:
                maxRunTime = 190;
                break;
            case 5:
                maxRunTime = 210;
                break;
        }
    }

    protected void initialize() {
        startTime = System.currentTimeMillis();
        System.out.println("[Debug] Firing a shot of type " + check + " with a run time of " + maxRunTime + " ms.");
    }

    protected void execute() {
        if (rollerSub.isExtended()) {
            pneumatapultSub.fire(shoot);
        }
    }

    protected boolean isFinished() {
        return System.currentTimeMillis() - startTime > maxRunTime;
    }

    protected void end() {
        pneumatapultSub.fire(false);
    }

    protected void interrupted() {
        end();
    }
}
