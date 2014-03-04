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
    private double calculatedError = 0;
    private double pidOutput = 0;
    private double normalizedScaledTargetAngle = 0;
    private double localRawAngle;
    private boolean a = true; // This boolean is correctly named a. I'm not an idiot.

    public AngleController(double kP, double kI, double kD, DiscoGyro g) {
        pidController = new PIDController(-kP, kI, kD, this, this);
        pidController.setSetpoint(0.0);
        gyro = g;
    }

    public double pidGet() {
        updateLocalAngleData();
        double a = this.localRawAngle - normalizedScaledTargetAngle;
        double b = normalizedScaledTargetAngle - this.localRawAngle;
        double error = 0;
        if (Math.abs(a) <= Math.abs(b)) {
            error = a;
            this.a = true;
        } else if (Math.abs(a) > Math.abs(b)) {
            error = b;
            this.a = false;
        }
        calculatedError = error;
        SmartDashboard.putNumber("AngleController Error Final", calculatedError);
        SmartDashboard.putNumber("AngleController Error a", a);
        SmartDashboard.putNumber("AngleController Error b", b);
        return error;
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
        normalizedScaledTargetAngle = normalizedTargetAngle * scale;
        SmartDashboard.putNumber("AngleController normscal TargetAngle", normalizedScaledTargetAngle);
        double a = this.localRawAngle - normalizedScaledTargetAngle;
        double b = normalizedScaledTargetAngle - this.localRawAngle;
        double error = 0;
        if (Math.abs(a) <= Math.abs(b)) {
            error = a;
            this.a = true;
        } else if (Math.abs(a) > Math.abs(b)) {
            error = b;
            this.a = false;
        }
        calculatedError = error;
        System.out.println(normalizedTargetAngle + " " + scale + " " + normalizedScaledTargetAngle + " " + a + " " + b + " " + error);
        SmartDashboard.putNumber("AngleController Error Final", calculatedError);
        SmartDashboard.putNumber("AngleController Error a", a);
        SmartDashboard.putNumber("AngleController Error b", b);
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
