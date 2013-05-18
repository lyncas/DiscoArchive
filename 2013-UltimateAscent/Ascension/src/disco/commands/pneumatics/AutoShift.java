/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands.pneumatics;

import disco.commands.CommandBase;
import disco.subsystems.Shifter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoShift extends CommandBase {
    //When to shift

    final double upShiftThreshold = 3.0;//feet/sec
    final double downShiftThreshold = 2.0;//feet/sec
    final double joyShiftThreshold = 0.55;
    //Other conditions to consider:
    //throttle position (requested acceleration)
    //actual acceleration
    //low speed and high throttle (pushing). higher threshold than normal downshifting?
    //current and desired velocity in opposite directions? Shift for faster decel?
    //when not to shift
    final double noShiftInterval = 500;//msec between shifts, minimum
    final double turningThreshold = 1.5;//feet/sec. If left or right moves faster by this much, we are turning. don't shift.
    //internal use only
    double lastShiftTime = 0;
    double leftSpeed = 0, rightSpeed = 0;
    double speed = 0;
    Filter filter;

    public AutoShift() {
        requires(shifter);
        filter = new Filter();
        filter.start();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        leftSpeed = rightSpeed = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        leftSpeed = filter.averageLeftSpeed();
        rightSpeed = filter.averageRightSpeed();
        //don't shift if turning
        if (Math.abs(leftSpeed) - Math.abs(rightSpeed) > turningThreshold) {
            return;
        }
        //don't shift if just shifted
        if (System.currentTimeMillis() - lastShiftTime < noShiftInterval) {
            return;
        }
        speed = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed)); //We will shift both sides at the same time.
        if (speed > upShiftThreshold) {
            if (Math.max(Math.abs(drivetrain.getRightInput()), Math.abs(drivetrain.getLeftInput())) > joyShiftThreshold) {
                shifter.setLeftShifter(Shifter.GEAR_HIGH);
                shifter.setRightShifter(Shifter.GEAR_HIGH);
                lastShiftTime = System.currentTimeMillis();
            }
        } else if (speed < downShiftThreshold) {
            shifter.setLeftShifter(Shifter.GEAR_LOW);
            shifter.setRightShifter(Shifter.GEAR_LOW);
            lastShiftTime = System.currentTimeMillis();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }

    //The same filter as used in DiscoCounterEncoder.
    class Filter extends Thread {

        private int length = 5;
        private double[] leftSpeeds = new double[length];
        private double[] rightSpeeds = new double[length];
        private int index = 0;

        public Filter() {
        }

        public void run() {
            while (true) {
                leftSpeeds[index] = drivetrain.getLeftRate();
                rightSpeeds[index] = drivetrain.getRightRate();
                index++;
                if (index > length - 1) {
                    index = 0;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    System.out.println("Drivetrain Filter dum!");
                }
            }
        }

        public double averageLeftSpeed() {
            double sum = 0.0;
            for (int i = 0; i < length; i++) {
                sum += leftSpeeds[i];
            }
            return sum / length;
        }

        public double averageRightSpeed() {
            double sum = 0.0;
            for (int i = 0; i < length; i++) {
                sum += rightSpeeds[i];
            }
            return sum / length;
        }
    }
}