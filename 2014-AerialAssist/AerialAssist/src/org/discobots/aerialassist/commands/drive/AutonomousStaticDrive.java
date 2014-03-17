package org.discobots.aerialassist.commands.drive;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.discobots.aerialassist.commands.CommandBase;
import org.discobots.aerialassist.utils.Constants;

public class AutonomousStaticDrive extends CommandBase {

    private double distanceInchesSetpoint, gyroAngleSetpoint;
    private PIDController pidController_y;
    private PIDSource pidSource_y;
    private PIDOutput pidOutput_y;
    private PIDController pidController_x;
    private PIDSource pidSource_x;
    private PIDOutput pidOutput_x;
    private long startTime;
    private double output_y = 0.0, output_x = 0.0;
    private double initialGyroAngle = 0.0;
    private double initialEncoderValue = 0.0;

    public AutonomousStaticDrive() throws Exception {
        throw new Exception();
    }

    public AutonomousStaticDrive(double distancesInchesSetpoint, double gyroAngleSetpoint) {
        requires(drivetrainSub);
        this.distanceInchesSetpoint = distancesInchesSetpoint;
        this.gyroAngleSetpoint = gyroAngleSetpoint;
        pidSource_y = new PIDSource() {
            public double pidGet() {
                return drivetrainSub.getEncoderForwardDistance() - initialEncoderValue;
            }
        };
        pidOutput_y = new PIDOutput() {

            public boolean equals(Object obj) {
                return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
            }

            public void pidWrite(double output) {
                output_y = output;
            }
        };
        pidController_y = new PIDController(Constants.AutonomousStraightTank_YkP, Constants.AutonomousStraightTank_YkI, Constants.AutonomousStraightTank_YkD, pidSource_y, pidOutput_y);
        pidSource_x = new PIDSource() {
            public double pidGet() {
                return drivetrainSub.getGyroAngle() - initialGyroAngle;
            }
        };
        pidOutput_x = new PIDOutput() {
            public void pidWrite(double output) {
                output_x = output;
            }
        };
        pidController_x = new PIDController(Constants.AutonomousStraightTank_XkP, Constants.AutonomousStraightTank_XkI, Constants.AutonomousStraightTank_XkD, pidSource_x, pidOutput_x);
    }

    protected void initialize() {
        if (Constants.DEBUG) {
            System.out.println("Initializing Command " + this.getName() + " at " + (startTime = System.currentTimeMillis()));
        }
        this.initialGyroAngle = drivetrainSub.getGyroAngle();
        this.initialEncoderValue = drivetrainSub.getEncoderForwardDistance();
        pidController_y.setSetpoint(distanceInchesSetpoint);
        pidController_x.setSetpoint(gyroAngleSetpoint);
        pidController_y.enable();
        pidController_x.enable();
    }

    protected void execute() {
        SmartDashboard.putData("AutonomousStaticDrive PID X", this.pidController_x);
        SmartDashboard.putData("AutonomousStaticDrive PID Y", this.pidController_y);

        drivetrainSub.tankDrive(output_y - output_x, output_y + output_x);
    }

    protected boolean isFinished() {
        return pidController_y.onTarget() && pidController_y.onTarget();
    }

    protected void end() {
        pidController_y.disable();
        pidController_x.disable();
        drivetrainSub.tankDrive(0, 0);
        if (Constants.DEBUG) {
            long endTime = System.currentTimeMillis();
            System.out.println("Ending command " + this.getName() + " at " + endTime + ". Command ran for " + (endTime - startTime) + " milliseconds.");
        }
    }

    protected void interrupted() {
        end();
    }
}
