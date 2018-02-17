package org.usfirst.frc.team4931.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import org.usfirst.frc.team4931.robot.Robot;
import org.usfirst.frc.team4931.robot.RobotMap;
import org.usfirst.frc.team4931.robot.commands.GrabberMoveWithPOV;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
/**
 * This defines the major component of the grabber subsystem.
 */
public class Grabber extends Subsystem {
  private final double NORMAL_MOVE_SPEED = 0.5;
  private boolean open;
  private DoubleSolenoid pneumatic;
  private WPI_TalonSRX grabberMotor;
  private double setPoint;
  private DigitalInput topLimitSwitch, bottomLimitSwitch;

  /**
   * Creates a new grabber. This sets up the motors and pneumatics neccecary for grabbing.
   */
  public Grabber() {
    pneumatic = new DoubleSolenoid(RobotMap.compressor, RobotMap.grabberPorts[0], RobotMap.grabberPorts[1]);
    grabberMotor = new WPI_TalonSRX(RobotMap.grabberMotorPort);
    grabberMotor.setInverted(RobotMap.grabberMotorInverted);
    grabberMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    grabberMotor.setNeutralMode(NeutralMode.Brake);
    grabberMotor.setSelectedSensorPosition(0, 0, 0);
    grabberMotor.setSensorPhase(RobotMap.grabberMotorInverted);

    topLimitSwitch = new DigitalInput(RobotMap.grabberTopLimitPort);
    bottomLimitSwitch = new DigitalInput(RobotMap.grabberBottomLimitPort);
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new GrabberMoveWithPOV());
  }

  /**
   * @return whether a and b are close to or equal to each other
   */
  private boolean fuzzyEqual(double a, double b) {
    return Math.abs(a-b) < 5;
  }

  /**
   * Closes the grabber to get the cube
   */
  public void captureCube() {
    pneumatic.set(Value.kReverse);
    open = false;
  }

  /**
   * Opens the grabber to release the cube
   */
  public void releaseCube() {
    pneumatic.set(Value.kForward);
    open = true;
  }

  /**
   * Sets the target position of the grabber and starts it moving towards it.
   *
   * @param position the target position in encoder counts.
   */
  public void goToSetPoint(double position) {
    setPoint = position;

    if (!topLimitSwitch.get() && !bottomLimitSwitch.get()) {
      grabberMotor.set(ControlMode.Position, position);
    } else if (topLimitSwitch.get() && position < grabberMotor.getSelectedSensorPosition(0)) {
      grabberMotor.set(ControlMode.Position, position);
    } else if (bottomLimitSwitch.get() && position > grabberMotor.getSelectedSensorPosition(0)) {
      grabberMotor.set(ControlMode.Position, position);
    }
  }

  /**
   * Sets the speed of the grabber rotation
   * @param speed the speed in of roation in +/- percent
   */
  public void setSpeed(double speed) {
    grabberMotor.set(ControlMode.PercentOutput, speed);
  }

  /**
   * Sets the target position of the grabber and starts it moving towards it.
   * @param position the target position of type GrabberPosition
   */
  public void goToSetPoint(GrabberPosition position) {
    goToSetPoint(position.position());
  }

  /**
   * @return the current grabber position.
   */
  public double getCurrentPosition() {
    return grabberMotor.getSelectedSensorPosition(0);
  }

  /**
   * @return if the grabber is at it's target position.
   */
  public boolean atTargetPosition() {
    return fuzzyEqual(getCurrentPosition(), setPoint);
  }

  /**
   * Checks the limit switches on the grabber and if one is trigger it will stop the motor from running that direction
   */
  public void checkLimitSwitchs() {
    if (topLimitSwitch.get() && grabberMotor.get() > 0) {
      setSpeed(0);
    } else if (bottomLimitSwitch.get()) {
      grabberMotor.setSelectedSensorPosition(0, 0, 0);
      if (grabberMotor.get() < 0) {
        setSpeed(0);
      }
    }
  }
  
  public void log() {
    SmartDashboard.putBoolean("GrabberOpen", open);
    SmartDashboard.putNumber("Grabber Position", grabberMotor.getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Grabber Speed", grabberMotor.get());
  }
}
