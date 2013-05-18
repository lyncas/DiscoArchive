package disco.subsystems;

import disco.HW;
import disco.commands.shooter.RawShooter;
import disco.commands.shooter.ShooterBangBang;
import disco.utils.DiscoCounterEncoder;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {

    private Talon m_shooterFront;
    private Talon m_shooterBack;
    private DiscoCounterEncoder m_encoderFront;
    private DiscoCounterEncoder m_encoderBack;
    private AnalogChannel loadSensor;

    public static final int IN=0;
    public static final int OUT=1;

    private Solenoid m_shoot;
    private Solenoid m_clear;


    private boolean enabled=false;
    private boolean onTarget=false;
    private double setpoint=0;
    public double m_defaultSetpoint=5700;


    public double frontPWM=0.65;
    public double backPWM=0.55;
    public double difference = 0;


    public static final int MODE_BANG=0;
    public static final int MODE_OPEN_LOOP=1;
    private int thisMode=0;
    public double prevRPM;
    public double prevDiff;

    public Shooter(){
	m_shooterFront=new Talon(HW.ShooterFrontSlot,HW.ShooterFrontChannel);
	m_shooterBack=new Talon(HW.ShooterBackSlot,HW.ShooterBackChannel);

        //Encoder power. Do not remove.
        new Solenoid(HW.encoder1PowerChannel).set(true);
        new Solenoid(HW.encoder2PowerChannel).set(true);

	m_encoderFront=new DiscoCounterEncoder(HW.shooterEncoderFrontSlot,HW.shooterEncoderFrontChannel,2);
        //m_encoderFront.setUpSourceEdge(true, true);
        m_encoderFront.setSemiPeriodMode(true);
        m_encoderFront.setMaxPeriod(.03);
        m_encoderFront.start();

        m_encoderBack=new DiscoCounterEncoder(HW.shooterEncoderBackSlot,HW.shooterEncoderBackChannel,2);
        //m_encoderBack.setUpSourceEdge(true, true);
        m_encoderBack.setSemiPeriodMode(true);
        m_encoderBack.setMaxPeriod(.03);
        m_encoderBack.start();

        setSetpoint(m_defaultSetpoint);

        m_shoot=new Solenoid(HW.shootPneumaticChannel);
        m_clear = new Solenoid(HW.clearShooterChannel);

        loadSensor=new AnalogChannel(HW.frisbeeLoadedSlot,HW.frisbeeLoadedChannel);
    }

    public void initDefaultCommand() {
    }

    public void setSetpoint(double setpoint){
        this.setpoint=setpoint;
    }
    public double getSetpoint(){
        return this.setpoint;
    }

    public void enable(){
        this.enabled=true;
        switch(thisMode){
            case MODE_BANG:
                new ShooterBangBang().start();
                break;
            case MODE_OPEN_LOOP:
                new RawShooter().start();
                break;
            default:
                new ShooterBangBang().start();
        }

    }
    public void disable(){
        Command com=this.getCurrentCommand();
        if(com!=null){
            com.cancel();
        }
        this.enabled=false;
    }
    public boolean isEnabled(){
        return this.enabled;
    }

    public void setMode(int mode){
        thisMode=mode;
    }
    public int getMode(){
        return thisMode;
    }

    public void setPower(double power){
	setFrontPower(power);
	setBackPower(power);
    }

    public void setFrontPower(double power){
	m_shooterFront.set(power);
    }

    public void setBackPower(double power){
	m_shooterBack.set(power);
    }

    public double getFrontPower(){
	return m_shooterFront.get();
    }

    public double getBackPower(){
	return m_shooterBack.get();
    }

    public void setPneuShoot(int position){
        if(position==IN){
	    m_shoot.set(true);
	} else if(position==OUT){
	    m_shoot.set(false);
	}
    }
    public void setPneuClear(int position) {
        if(position==IN){
	    m_clear.set(true);
	} else if(position==OUT){
	    m_clear.set(false);
	}
    }

    public boolean getPneuShoot(){
        return m_shoot.get();
    }
    public boolean getPneuClear(){
        return m_clear.get();
    }

    public double getFrontRPM() {
        double c = m_encoderFront.getRPM();
	return c;

    }
    public double getBackRPM() {
	double c =  m_encoderBack.getRPM();
        return c;
    }

    public void setOnTarget(boolean val){
        onTarget=val;
    }
    public boolean isOnTarget(){
        return onTarget;
    }

    public double isLoaded(){
        return loadSensor.getVoltage();
    }
}