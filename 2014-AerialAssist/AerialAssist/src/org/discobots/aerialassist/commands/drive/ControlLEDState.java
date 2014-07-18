/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands.drive;

import org.discobots.aerialassist.commands.CommandBase;

public class ControlLEDState extends CommandBase {

    public static boolean readyToShoot = false;

    public ControlLEDState() {
    }

    protected void initialize() {
    }

    protected void execute() {
        int ultrasonicVal = drivetrainSub.getUltrasonicIntakeAverageValue();
        boolean meetsMinDistance = ultrasonicVal > 108;
        boolean meetsMaxDistance = ultrasonicVal < 156;
        boolean meetsPressure = compressorSub.getPressurePSIInt() > 65;
        boolean readyToShoot = meetsMinDistance && meetsMaxDistance && meetsPressure;
        ControlLEDState.readyToShoot = readyToShoot;
        if (readyToShoot) {
            drivetrainSub.writeLEDState(true);
        } else {
            drivetrainSub.writeLEDState(false);
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
