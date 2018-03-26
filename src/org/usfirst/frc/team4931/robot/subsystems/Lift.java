package org.usfirst.frc.team4931.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4931.robot.RobotMap;
import org.usfirst.frc.team4931.robot.commands.LiftWithJoystick;
/**
 * This defines the major component of the lift subsystem.
 */
public class Lift extends Subsystem {
  private WPI_TalonSRX liftMotor;
  private double setPoint;
  private FixedLiftHeight liftHeight;
  private DigitalInput topLimitSwitch;
  private DigitalInput bottomLimitSwitch;

  /**
   * Creates a new lift. This sets up the motors and potentiometers necessary for lifting.
   */
  public Lift() {
    liftMotor = new WPI_TalonSRX(RobotMap.liftMotorPort);
    liftMotor.setInverted(RobotMap.liftMotorInverted);
    liftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
    liftMotor.setSelectedSensorPosition(0, 0, 0);
    liftMotor.setSensorPhase(RobotMap.liftMotorSensorInverted);
    liftMotor.setNeutralMode(NeutralMode.Brake);
    liftHeight = FixedLiftHeight.FLOOR;
    PIDF(1, 0.000002, 300, 0.025);

    topLimitSwitch = new DigitalInput(RobotMap.liftTopLimitPort);
    bottomLimitSwitch = new DigitalInput(RobotMap.liftBottomLimitPort);
  }

  public double getPosition() {
    return liftMotor.getSelectedSensorPosition(0);
  }

  public boolean getTop() {
    return !topLimitSwitch.get();
  }

  public boolean getBottom() {
    return !bottomLimitSwitch.get();
  }

  public double getSetPoint() {
    return setPoint;
  }

  public void PIDF(double p, double i, double d, double f) {
    liftMotor.config_kP(0, p, 0);
    liftMotor.config_kI(0, i, 0);
    liftMotor.config_kD(0, d, 0);
    liftMotor.config_kF(0, f, 0);
  }

  /**
   * @return whether a and b are close to or equal to each other
   */
  private boolean fuzzyEqual(double a, double b) {
    return Math.abs(a - b) < 125;
  }

  /**
   * @return if the lift has reached it's desired set point yet.
   */
  public boolean isAtTarget() {
    return fuzzyEqual(getPosition(), setPoint);
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new LiftWithJoystick());
  }

  /**
   * Sets the lift height
   * @param liftHeight The desired height to move to as defined by the preset lift heights
   */
  public void setLiftHeight(FixedLiftHeight liftHeight) {
    this.liftHeight = liftHeight;
    setLiftHeight(liftHeight.position());
  }

  /**
   * Sets the lift height
   * @param position The desired height to move to in encoder counts
   */
  public void setLiftHeight(double position) {
    setPoint = position;
    if (!getTop() && !getBottom()) {
      liftMotor.set(ControlMode.Position, setPoint);
    } else if (getTop() && setPoint < getPosition()) {
      liftMotor.set(ControlMode.Position, setPoint);
    } else if (getBottom() && setPoint > getPosition()) {
      liftMotor.set(ControlMode.Position, setPoint);
    }
  }

  public FixedLiftHeight getLiftHeight() {
    return liftHeight;
  }

  /**
   * Sets the speed of the lift motor
   * @param speed the speed in +/- percent
   */
  public void setSpeed(double speed) {
    liftMotor.set(ControlMode.PercentOutput, speed);
  }

  /**
   * Checks the limit switches on the grabber and if one is trigger it will stop the motor from running that direction
   */
  public void checkLimitSwitchs() {
    if (getTop() && liftMotor.get() > 0.05) {
      setLiftHeight(FixedLiftHeight.SCALE_TOP);
    } else if (getBottom()) {
      liftMotor.setSelectedSensorPosition(0, 0, 0);
      if (liftMotor.get() < 0.05) {
        setLiftHeight(FixedLiftHeight.FLOOR);
      }
    }
  }

  public void log() {
    SmartDashboard.putString("Lift Height", liftHeight.name());
    SmartDashboard.putNumber("Lift Position", getPosition());
    SmartDashboard.putNumber("Lift Motor Speed", liftMotor.get());
    SmartDashboard.putBoolean("Lift Top", getTop());
    SmartDashboard.putBoolean("Lift Bottom", getBottom());
  }
  
}
