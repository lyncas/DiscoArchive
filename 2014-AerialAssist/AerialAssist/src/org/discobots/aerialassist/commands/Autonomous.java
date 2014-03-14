/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands;

import org.discobots.aerialassist.commands.SetPneumaticsRunnable;
import org.discobots.aerialassist.commands.upperbody.AutonomousIntake;
import org.discobots.aerialassist.commands.drive.AutonomousTankDrive;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.discobots.aerialassist.commands.drive.SetMiniCimUsage;
import org.discobots.aerialassist.commands.upperbody.FirePneumatapult;
import org.discobots.aerialassist.commands.upperbody.Intake;
import org.discobots.aerialassist.commands.upperbody.ToggleArm;
import org.discobots.aerialassist.utils.BetterRobotDrive;

/**
 *
 * @author Seth
 */
public class Autonomous extends CommandGroup {
    
        long time;
    public Autonomous(int mode) {
        time = System.currentTimeMillis();
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
    
    private void autonomousMode1Init() { // TWO BALL
        addSequential(new ToggleCompressor());
        addSequential(new SetPneumaticsRunnable(true));
        addSequential(new ToggleArm(false));
        addSequential(new AutonomousIntake(0.3*Intake.IN, 1500));
        addSequential(new AutonomousTankDrive(-0.6, -0.6, 0.5*Intake.IN, 2000));
        addSequential(new WaitCommand(1.500));
        addSequential(new FirePneumatapult(true, 2));
        addSequential(new WaitCommand(1.500));
        addSequential(new AutonomousIntake(1*Intake.IN, 2500));
        addSequential(new FirePneumatapult(true, 2));
    }
    
    private void autonomousMode2Init() { // NO GOAL : NO BALL : MOVE ONLY
        addSequential(new ToggleCompressor());
        addSequential(new SetPneumaticsRunnable(true));
        addSequential(new AutonomousTankDrive(-0.5, -0.5, 3000));
    }
    
    private void autonomousMode3Init() { // LOW/HIGH GOAL : TWO BALL : MOVE FIRST THEN FIRE
        addSequential(new ToggleCompressor());
        addSequential(new SetPneumaticsRunnable(true));
        addSequential(new AutonomousTankDrive(-1, -1, 1550));
        addSequential(new AutonomousTankDrive(0.7, 0.7, 900));
        addSequential(new ToggleArm(false));
        addSequential(new AutonomousIntake(0.4*Intake.IN, 3000));
        addSequential(new FirePneumatapult(true, 2));
        addSequential(new WaitCommand(55));
        
   }
    
    private void autonomousMode4Init() { // LOW GOAL : ONE BALL
        addSequential(new AutonomousTankDrive(1.0, 1.0, 4000));
        addSequential(new SetPneumaticsRunnable(true));
        addSequential(new ToggleArm(true));
        addSequential(new AutonomousIntake(Intake.OUT, 2500));
    }
    
    public void end() {
        System.out.println(System.currentTimeMillis() - time);
    }
}
