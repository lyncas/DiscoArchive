/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands.pneumatics;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class Clear extends CommandGroup {
    
    public Clear() {
        addSequential(new ClearIn());
        addSequential(new WaitCommand(0.2));
        addSequential(new ClearOut());
        

    }
}