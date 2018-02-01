package org.usfirst.frc.team4931.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4931.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
/**
 * This defines the major component of the grabber subsystem.
 */
public class Grabber extends Subsystem {
  private final double NORMAL_MOVE_SPEED = 0.5;

  private DoubleSolenoid leftPneumatic, rightPneumatic;
  private WPI_TalonSRX leftGrabberMotor, rightGrabberMotor;
  private DigitalInput limitSwitchLow, limitSwitchMid, limitSwitchHigh;
  private FixedGrabberPosition setPoint;

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

    limitSwitchLow = new DigitalInput(RobotMap.limitSwitchLowPort);
    limitSwitchMid = new DigitalInput(RobotMap.limitSwitchMidPort);
    limitSwitchHigh = new DigitalInput(RobotMap.limitSwitchHighPort);
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

  public void goToPosition(FixedGrabberPosition grabberPosition) {
    switch (grabberPosition) { //TODO make code to start motor moving until it reaches the target position
      case LOW:
        break;
      case MIDDLE:
        break;
      case HIGH:
        break;
    }
    setPoint = grabberPosition;
  }

  public FixedGrabberPosition getPosition() {
    if (limitSwitchLow.get())
      return FixedGrabberPosition.LOW;
    else if (limitSwitchMid.get())
      return FixedGrabberPosition.MIDDLE;
    else
      return FixedGrabberPosition.HIGH;
  }

  public boolean isAtSetPoint() {
    return isAtPosition() && (getPosition() == setPoint);
  }

  private boolean isAtPosition() {
    return limitSwitchLow.get() || limitSwitchMid.get() || limitSwitchHigh.get();
  }

}
