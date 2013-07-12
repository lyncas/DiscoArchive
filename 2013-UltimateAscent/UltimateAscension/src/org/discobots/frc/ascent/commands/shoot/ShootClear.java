package org.discobots.frc.ascent.commands.shoot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 * @author Nolan
 */
public class ShootClear extends CommandGroup {
    
    public ShootClear() {
        addSequential(new ToggleClearShoot());
        addSequential(new WaitCommand(0.3));
        addSequential(new ToggleClearShoot());
    }
}
