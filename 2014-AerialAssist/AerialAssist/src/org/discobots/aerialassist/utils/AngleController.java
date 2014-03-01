package org.discobots.aerialassist.utils;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Patrick
 */
public class AngleController implements PIDSource, PIDOutput {

    private final PIDController pidController;
    private final DiscoGyro gyro;
    private double calculatedError;
    private double pidOutput;
    private double localRawAngle;

    public AngleController(double kP, double kI, double kD, DiscoGyro g) {
        pidController = new PIDController(-kP, kI, kD, this, this);
        pidController.setSetpoint(0.0);
        gyro = g;
    }

    public double pidGet() {
        return calculatedError;
    }

    public void pidWrite(double pidOut) {
        synchronized (this) {
            this.pidOutput = pidOut;
        }
    }

    public double getSetpoint() {
        return pidController.getSetpoint();
    }
    
    public void setSetpoint(double targetAngle) {
        updateLocalAngleData();
        SmartDashboard.putNumber("AngleController TargetAngle ", targetAngle);
        double normalizedTargetAngle = DiscoGyro.normalize(targetAngle);
        double scale = MathUtils.round(this.localRawAngle / 360.0);
        double normalizedScaledTargetAngle = normalizedTargetAngle * scale;
        
        double a = this.localRawAngle - normalizedScaledTargetAngle;
        double b = normalizedScaledTargetAngle = this.localRawAngle;
        double error = 0;
        if (Math.abs(a) <= Math.abs(b)) {
            error = a;
        } else if (Math.abs(a) > Math.abs(b)) {
            error = b;
        }
        System.out.println(normalizedTargetAngle + " " + scale + " " + normalizedScaledTargetAngle + " " + a + " " + b + " " + error);
        SmartDashboard.putNumber("AngleController Error ", error);
        pidController.setSetpoint(error);
    }

    public double getOutput() {
        synchronized (this) {
            SmartDashboard.putNumber("Angle Output", this.pidOutput);
            return this.pidOutput;
        }
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            this.pidController.enable();
        } else {
            this.pidController.disable();
        }
    }
    
    private void updateLocalAngleData() {
        this.localRawAngle = gyro.getAngle();
        //this.localNormAngle = gyro.getNormalizedAngle();
    }
}
