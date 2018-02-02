package org.usfirst.frc.team4931.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
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
  private GrabberPosition setPoint, lastAbsoluteGrabberPosition, currentGrabberPosition;

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

  public void goToSetPoint() {
    if (setPoint.ordinal() > currentGrabberPosition.ordinal()) {
      //Move grabber up
    } else if (setPoint.ordinal() < currentGrabberPosition.ordinal()) {
      //Move grabber down
    } else {
      //Stop grabber
    }
  }

  public void goToSetPoint(GrabberPosition position) {
    setPoint = position;
    goToSetPoint();
  }

  public void calculateCurrentPosition() {
    if (limitSwitchLow.get()) {
      currentGrabberPosition = GrabberPosition.LOW;
      lastAbsoluteGrabberPosition = currentGrabberPosition;
    } else if (limitSwitchMid.get()) {
      currentGrabberPosition = GrabberPosition.MIDDLE;
      lastAbsoluteGrabberPosition = currentGrabberPosition;
    } else if (limitSwitchHigh.get()) {
      currentGrabberPosition = GrabberPosition.HIGH;
      lastAbsoluteGrabberPosition = currentGrabberPosition;
    } else {
      switch (lastAbsoluteGrabberPosition) {
        case LOW:
          currentGrabberPosition = GrabberPosition.LOW_MIDDLE;
          break;
        case MIDDLE:
          if (setPoint.ordinal() > GrabberPosition.MIDDLE.ordinal())
            currentGrabberPosition = GrabberPosition.MIDDLE_HIGH;
          else
            currentGrabberPosition = GrabberPosition.LOW_MIDDLE;
          break;
        case HIGH:
          currentGrabberPosition = GrabberPosition.MIDDLE_HIGH;
          break;
      }
    }
  }

  public void calculateCurrentPositionAndMove() {
    calculateCurrentPosition();
    goToSetPoint();
  }

  public GrabberPosition getCurrentGrabberPosition() {
    return currentGrabberPosition;
  }

  public GrabberPosition getLastAbsoluteGrabberPosition() {
    return lastAbsoluteGrabberPosition;
  }

}
