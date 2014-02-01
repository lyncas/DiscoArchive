/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robt.commands.winch;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Developer
 */
public class ArmToFire extends CommandGroup {
    
    public ArmToFire() {
        addSequential(new WinchStop());
        addSequential(new Lock());
        addSequential(new ReleaseWinch());
        //all we need now is Unlock()
    }
}
