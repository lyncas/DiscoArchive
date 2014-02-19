/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.discobots.aerialassist.commands.upperbody.FirePneumatapult;
import org.discobots.aerialassist.commands.upperbody.Intake;
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
        
        addSequential(new ToggleArm(true));
        addSequential(new AutonomousIntake(0.2*Intake.IN, 2000));
        addSequential(new FirePneumatapult(true, 2));
//        addSequential(new WaitCommand(1.500));
//        addSequential(new AutonomousDrive(-1, -1, 1000));
        addSequential(new AutonomousDrive(1, 90, 0, 100));
//        addSequential(new AutonomousDrive(1, 1, 1000));
        addSequential(new AutonomousIntake(1*Intake.IN, 3000));
        addSequential(new AutonomousDrive(1, 270, 0, 100));
        addSequential(new FirePneumatapult(true, 2));
        addSequential(new AutonomousDrive(1, 270, 0, 500));
              
    }
}
