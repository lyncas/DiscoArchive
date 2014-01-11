/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robt.commands.winch;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 * @author Developer
 */
public class Fire extends CommandGroup {
    
    public Fire() {
        addSequential(new ArmToFire());
        addSequential(new Unlock());//BOOM
        addSequential(new WaitCommand(0.25));
        addSequential(new EngageWinch());
        
    }
}
