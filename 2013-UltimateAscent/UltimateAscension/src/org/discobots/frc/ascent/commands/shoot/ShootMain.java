package org.discobots.frc.ascent.commands.shoot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 * @author Nolan
 */
public class ShootMain extends CommandGroup {
    
    public ShootMain() {
        addSequential(new ToggleMainShoot());
        addSequential(new WaitCommand(0.3));
        addSequential(new ToggleMainShoot());
    }
    
}
