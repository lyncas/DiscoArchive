/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands.pneumatics;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 * @author Developer
 */
public class Shoot extends CommandGroup {
    
    public Shoot() {
        addSequential(new ShootIn());
        addSequential(new WaitCommand(0.2));
        addSequential(new ShootOut());
    }
}