/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.discobots.aerialassist.commands.upperbody.FirePneumatapult;
import org.discobots.aerialassist.commands.upperbody.ToggleArm;
import org.discobots.aerialassist.utils.BetterRobotDrive;

/**
 *
 * @author Seth
 */
public class Autonomous extends CommandGroup {
    
    public Autonomous() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.
        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
        
        addSequential(new ToggleArm());
        addSequential(new WaitCommand(500));
        addSequential(new FirePneumatapult(true, 2));
        addSequential(new WaitCommand(1500));
        addSequential(new AutonomousDrive(1, 180, 0, 500));
        addSequential(new AutonomousDrive(1, 0, 0, 500));
        addSequential(new AutonomousIntake(500));
        addSequential(new FirePneumatapult(true, 2));
              
    }
}
