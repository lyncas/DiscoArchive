/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Doris
 */
public class Autonomous extends CommandGroup {
    public static final int MODE_SAFE = 0;
    public static final int MODE_RISKY = 1;
    public static final int MODE_DANGEROUS = 2;
    public static final int MODE_NOTHING = 3;

    public Autonomous(int mode) {

    }
    protected void initialize() {

    }
    protected void execute() {

    }
    protected boolean isFinished() {
        return false;
    }
    protected void end() {
    }
    protected void interrupted() {
        end();
    }
}
