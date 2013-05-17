/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DriveControllers;

import DriveControllers.AbstractPID;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 * @author JAG
 */
public class SonarPositionPID extends AbstractPID{

    public SonarPositionPID(double Kp, double Ki, double Kd,
            PIDSource source, PIDOutput output,
            double period){
        super(Kp, Ki, Kd, source, output, period);
    }

    protected void calculate(){
        boolean enabled;
        PIDSource pidInput;

        synchronized (this) {
            if (m_pidInput == null) {
                return;
            }
            if (m_pidOutput == null) {
                return;
            }
            enabled = m_enabled; // take snapshot of these values...
            pidInput = m_pidInput;
        }

        if (enabled) {
            double input = pidInput.pidGet();
            double result;
            PIDOutput pidOutput = null;

            synchronized (this) {
                m_error = m_setpoint - input;
                if (!onTarget()) {
                    if (((m_totalError + m_error) * m_I < m_maximumOutput)
                            && ((m_totalError + m_error) * m_I > m_minimumOutput)) {
                        m_totalError += m_error;
                    }

                    m_result = (m_P * m_error + m_I * m_totalError + m_D * (m_error - m_prevError));
                    m_prevError = m_error;

                    if (m_result > m_maximumOutput) {
                        m_result = m_maximumOutput;
                    } else if (m_result < m_minimumOutput) {
                        m_result = m_minimumOutput;
                    }
                    pidOutput = m_pidOutput;
                    result = m_result;
                } else {
                    m_result = 0.0;
                }
            }
            pidOutput.pidWrite(m_result);
        }
    }
}
