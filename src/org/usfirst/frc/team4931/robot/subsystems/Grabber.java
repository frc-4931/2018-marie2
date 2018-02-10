package org.usfirst.frc.team4931.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.RemoteLimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import org.usfirst.frc.team4931.robot.RobotMap;
import org.usfirst.frc.team4931.robot.commands.ChangeGrabberPosition;
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
  private DoubleSolenoid leftPneumatic, rightPneumatic;
  private WPI_TalonSRX leftGrabberMotor, rightGrabberMotor;
  private double setPoint;

  /**
   * Creates a new grabber. This sets up the motors and pneumatics neccecary for grabbing.
   */
  public Grabber() {
    leftPneumatic = new DoubleSolenoid(RobotMap.leftGrabberPorts[0], RobotMap.leftGrabberPorts[1]);
    rightPneumatic = new DoubleSolenoid(RobotMap.rightGrabberPorts[0], RobotMap.rightGrabberPorts[1]);
    leftGrabberMotor = new WPI_TalonSRX(RobotMap.leftGrabberMotorPort);
    rightGrabberMotor = new WPI_TalonSRX(RobotMap.rightGrabberMotorPort);
    leftGrabberMotor.setInverted(RobotMap.leftGrabberMotorInverted);
    rightGrabberMotor.setInverted(RobotMap.rightGrabberMotorInverted);
    leftGrabberMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
    rightGrabberMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
    leftGrabberMotor.setSensorPhase(RobotMap.leftGrabberMotorInverted);
    rightGrabberMotor.setSensorPhase(RobotMap.rightGrabberMotorInverted);
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new ChangeGrabberPosition());
  }

  /**
   * @return whether a and b are almost equal
   */
  private boolean fuzzyEqual(double a, double b) {
    return Math.abs(a-b) < 5;
  }

  /**
   * Closes the grabber to get the cube
   */
  public void captureCube() {
    leftPneumatic.set(Value.kReverse);
    rightPneumatic.set(Value.kReverse);
    open = false;
  }

  /**
   * Opens the grabber to release the cube
   */
  public void releaseCube() {
    leftPneumatic.set(Value.kForward);
    rightPneumatic.set(Value.kForward);
    open = true;
  }

  /**
   * Sets the target position of the grabber and starts it moving towards it.
   *
   * @param position the target position in encoder counts.
   */
  public void goToSetPoint(double position) {
    setPoint = position;
    leftGrabberMotor.set(ControlMode.Position, position);
    rightGrabberMotor.set(ControlMode.Position, position);
  }
  
  public void changePosition(double position) {
    leftGrabberMotor.set(ControlMode.PercentOutput, position);
    rightGrabberMotor.set(ControlMode.PercentOutput, position);
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
    return leftGrabberMotor.getSelectedSensorPosition(0);
  }

  /**
   * @return if the grabber is at it's target position.
   */
  public boolean atTargetPosition() {
    return fuzzyEqual(leftGrabberMotor.getSelectedSensorPosition(0), setPoint) && fuzzyEqual(rightGrabberMotor.getSelectedSensorPosition(0), setPoint);
  }
  
  public void log() {
    SmartDashboard.putBoolean("GrabberOpen", open);
    SmartDashboard.putNumber("Grabber Position Left", leftGrabberMotor.getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Grabber Position Right", rightGrabberMotor.getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Grabber Speed Left", leftGrabberMotor.get());
    SmartDashboard.putNumber("Grabber Speed Right", rightGrabberMotor.get());
  }
}
