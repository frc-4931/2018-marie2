package org.usfirst.frc.team4931.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import org.usfirst.frc.team4931.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
/**
 * This defines the major component of the grabber subsystem.
 */
public class Grabber extends Subsystem {
  private DoubleSolenoid leftPneumatic;
  private DoubleSolenoid rightPneumatic;
  private WPI_TalonSRX leftGrabberMotor, rightGrabberMotor;

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
  }

  @Override
  protected void initDefaultCommand() {
    // TODO Auto-generated method stub

  }

  /**
   * Closes the grabber to get the cube
   */
  public void captureCube() {
    leftPneumatic.set(Value.kReverse);
    rightPneumatic.set(Value.kReverse);
  }

  /**
   * Opens the grabber to release the cube
   */
  public void releaseCube() {
    leftPneumatic.set(Value.kForward);
    rightPneumatic.set(Value.kForward);
  }

  /**
   *
   */
  public void goToPosition() {

  }

}
