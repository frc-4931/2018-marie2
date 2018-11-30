package org.usfirst.frc.team4931.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4931.robot.RobotMap;
import org.usfirst.frc.team4931.robot.commands.GrabberRotationWithThrottle;
import org.usfirst.frc.team4931.robot.enums.GrabberState;

public class Grabber extends Subsystem {

  private DigitalInput forwardLimitSwitch, reverseLimitSwitch;

  private DoubleSolenoid pneumatic;
  private WPI_TalonSRX grabberMotor;

  public Grabber() {
    /* TODO: Add limit switch */
    forwardLimitSwitch = new DigitalInput(RobotMap.GRABBER_LIMIT_FORWARD.getValue());
    reverseLimitSwitch = new DigitalInput(RobotMap.GRABBER_LIMIT_REVERSE.getValue());

    pneumatic =
        new DoubleSolenoid(
            RobotMap.COMPRESSOR.getValue(),
            RobotMap.GRABBER_1.getValue(),
            RobotMap.GRABBER_2.getValue());

    grabberMotor = new WPI_TalonSRX(RobotMap.MOTOR_GRABBER.getValue());
    grabberMotor.setInverted(false);
    grabberMotor.setNeutralMode(NeutralMode.Brake);

    grabberMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    grabberMotor.setSelectedSensorPosition(0, 0, 0);
    grabberMotor.setSensorPhase(true);

    //    grabberMotor.configNominalOutputForward(0, 0);
    //    grabberMotor.configNominalOutputReverse(0, 0);
    //
    //    grabberMotor.configPeakOutputForward(1, 0);
    //    grabberMotor.configPeakOutputReverse(-1, 0);

    /* PID */
    // FIXME: Change PID Stuff
    // grabberMotor.selectProfileSlot(0, 0);
    pidf(0.5, 0.000003, 240, 0.025);

    //
    // grabberMotor.configMotionCruiseVelocity(RobotMap.GRABBER_CONFIG_CRUISE_VELOCITY.getValue(),
    // 0);
    //    grabberMotor.configMotionAcceleration(RobotMap.GRABBER_CONFIG_ACCELERATION.getValue(), 0);
  }

  public void pidf(double p, double i, double d, double f) {
    grabberMotor.config_kP(0, p, 0);
    grabberMotor.config_kI(0, i, 0);
    grabberMotor.config_kD(0, d, 0);
    grabberMotor.config_kF(0, f, 0);
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new GrabberRotationWithThrottle());
  }

  public void changeGrabberPosition(int position) {
    int maxPosition = RobotMap.GRABBER_CONFIG_POSITION_MAX.getValue();

    //    if (position > 0 && position <= maxPosition)
    grabberMotor.set(ControlMode.Position, position);
    //    else
    //      System.out.println(
    //          "Tried to move grabber to position "
    //              + position
    //              + ". It is either less than 0 or greater than "
    //              + maxPosition);
  }

  public void changeGrabberState(GrabberState grabberState) {
    switch (grabberState) {
      case OPENED:
        pneumatic.set(Value.kForward);
        break;
      case CLOSED:
        pneumatic.set(Value.kReverse);
        break;
      case TOGGLE:
        pneumatic.set(pneumatic.get() == Value.kReverse ? Value.kForward : Value.kReverse);
    }
  }

  public void setSpeed(double speed) {
    grabberMotor.set(ControlMode.PercentOutput, speed);
  }

  public void checkLimitSwitches() {
    if (!forwardLimitSwitch.get()) {
      grabberMotor.setSelectedSensorPosition(0, 0, 0);
      if (grabberMotor.get() < 0) grabberMotor.set(ControlMode.PercentOutput, 0);
    }

    if (!reverseLimitSwitch.get()) {
      if (grabberMotor.get() > 0) {
        grabberMotor.set(ControlMode.PercentOutput, 0);
      }
    }
  }

  public boolean getForwardLimitSwitch() {
    return !forwardLimitSwitch.get();
  }

  public boolean getReverseLimitSwitch() {
    return !reverseLimitSwitch.get();
  }

  public void resetZero() {
    grabberMotor.setSelectedSensorPosition(0, 0, 0);
  }

  public void log() {

    SmartDashboard.putBoolean("Grabber Open", pneumatic.get() == Value.kForward);
    SmartDashboard.putNumber("Grabber Position", grabberMotor.getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Grabber Speed", grabberMotor.get());
  }
}
