/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands;

import org.discobots.aerialassist.commands.upperbody.AutonomousIntake;
import org.discobots.aerialassist.commands.drive.AutonomousTankDrive;
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
    
    public Autonomous(int mode) {
        switch (mode) {
            case 0:
                autonomousMode0Init();
                break;
            case 1:
                autonomousMode1Init();
                break;
            case 2:
                autonomousMode2Init();
                break;
            case 3:
                autonomousMode3Init();
                break;
            case 4:
                autonomousMode4Init();
                break;
        }
    }
    
    private void autonomousMode0Init() {
        // Do Nothing
    }
    
    private void autonomousMode1Init() {
        addSequential(new ToggleCompressor());
        addSequential(new AutonomousTankDrive(-0.6, -0.6, 0.5*Intake.IN, 2000));
        addSequential(new FirePneumatapult(true, 2));
        addSequential(new AutonomousTankDrive(1, 1, 300));
        addSequential(new WaitCommand(1.500));
        addSequential(new AutonomousIntake(1*Intake.IN, 2500));
        addSequential(new FirePneumatapult(true, 2));
        addSequential(new AutonomousTankDrive(-1, -1, 300));
        addSequential(new AutonomousTankDrive(1, 1, 300));
    }
    
    private void autonomousMode2Init() {
    }
    
    private void autonomousMode3Init() {
    }
    
    private void autonomousMode4Init() {
    }
}
