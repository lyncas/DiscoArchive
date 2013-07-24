package org.discobots.frc.ascent.commands.shoot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 * @author Nolan
 */
public class ShootMain extends CommandGroup {
    /**
     * DANGEROUS:
     * If shoot toggled once somewhere else, now in wrong position forever.
     * Consider changing to explicit 'in' and 'out' calls.
     */
    public ShootMain() {
        addSequential(new ToggleMainShoot());
        addSequential(new WaitCommand(0.2));
        addSequential(new ToggleMainShoot());
    }
    
}
