package DiscoLift;

import discobot.HW;

/**
 * 
 * @author Nelson Chen
 */
public class Arm {

    public static double k_armUpSpeed = -0.4;
    public static double k_armDownSpeed = 0.3;
    public static final double k_collectorOutSpeed = -0.5;
    public static final double k_collectorInSpeed = 0.5;

    public Arm(){

    }

    public void updateArmSpeed() {
        k_armUpSpeed = HW.k_MaxVoltage/HW.driverStation.getBatteryVoltage() * -0.4;
        k_armDownSpeed = HW.k_MaxVoltage/HW.driverStation.getBatteryVoltage() * 0.3;
    }

    public void collect() {
        HW.lift.setSetpoint(HW.lift.kLiftD);
        down();
        tubeIn();
    }
    private boolean collecting = false;
    //Assume lift will go up when this function is not called

    public void autoCollect(boolean sw) {
        if (sw) {
            down();
            tubeIn();
            collecting = true;
        } else if (isArmUp() && collecting) {
            stopCollector();
            collecting = false;
        }
    }

    public void up() {
        if (!isUp()) {
            HW.armMotor.set(k_armUpSpeed);
        } else {
            stopArm();
        }

    }

    public void down() {

        if (!isDown()) {
            HW.armMotor.set(k_armDownSpeed);
        } else {
            stopArm();
        }
    }

    public boolean isArmDown() {
        return !HW.armSwitchDown.get();
    }

    public boolean isArmUp() {
        return !HW.armSwitchUp.get();
    }

    public void stopArm() {
        HW.armMotor.set(0.0);
    }

    public void stopCollector() {
        HW.collectorMotor.set(0.0);
    }

    public void tubeIn() {
        HW.collectorMotor.set(k_collectorInSpeed);
    }

    public void tubeIn(double speed) {
        HW.collectorMotor.set(speed);
    }

    public void tubeOut() {
        HW.collectorMotor.set(k_collectorOutSpeed);
    }

    public boolean isUp() {
        return !HW.armSwitchUp.get();
    }
    public boolean isDown() {
        return !HW.armSwitchDown.get();
    }
}
