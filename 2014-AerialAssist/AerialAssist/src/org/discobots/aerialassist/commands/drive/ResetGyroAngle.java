/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands.drive;

import org.discobots.aerialassist.commands.CommandBase;

public class ResetGyroAngle extends CommandBase {
    
    public ResetGyroAngle() {
    }

    protected void initialize() {
        drivetrainSub.gyro.reset();
        drivetrainSub.setAngleControllerSetpoint(0.0);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
