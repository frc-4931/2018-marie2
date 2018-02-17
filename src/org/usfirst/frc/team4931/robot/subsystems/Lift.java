package org.usfirst.frc.team4931.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
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
  
/**
 * Creates a new lift. This sets up the motors and potentiometers necessary for lifting.
 */
  public Lift() {
    liftMotor = new WPI_TalonSRX(RobotMap.liftMotorPort);
    liftMotor.setInverted(RobotMap.liftMotorInverted);
    liftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    liftMotor.setSelectedSensorPosition(0, 0, 0);
    liftMotor.setNeutralMode(NeutralMode.Brake);
    liftMotor.setSensorPhase(RobotMap.liftMotorSensorInverted);
    liftHeight = FixedLiftHeight.FLOOR;
  }

  /**
   * @return if the lift has reached it's desired set point yet.
   */
  public boolean isAtTarget() {
    return (liftMotor.getSelectedSensorPosition(0) >= setPoint);
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
    setPoint = liftHeight.position();
    liftMotor.set(ControlMode.MotionMagic, setPoint);
  }

  /**
   * Sets the lift height
   * @param position The desired height to move to in encoder counts
   */
  public void setLiftHeight(double position) {
    setPoint = position;
    liftMotor.set(ControlMode.MotionMagic, setPoint);
  }

  /**
   * Sets the speed of the lift motor
   * @param speed the speed in +/- percent
   */
  public void setSpeed(double speed) {
    liftMotor.set(ControlMode.PercentOutput, speed);
  }

  public void log() {
    SmartDashboard.putString("Lift Height", liftHeight.name());
    SmartDashboard.putNumber("Lift Motor Speed", liftMotor.get());
  }
  
}
