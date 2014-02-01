/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robt.commands.winch;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robt.OI;
import robt.commands.CommandBase;

/**
 *
 * @author Developer
 */
public class Pull extends CommandGroup {

    public Pull() {

        if (CommandBase.oi.cocked.get()) {
            addSequential(new ArmToFire());//failsafe if we start pull when already pulled
        } else {
            addSequential(new EngageWinch());
            addSequential(new Unlock());
            addSequential(new WinchIn());
        }

    }
}
