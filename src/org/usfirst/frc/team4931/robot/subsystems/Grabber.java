package org.usfirst.frc.team4931.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.RemoteLimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import org.usfirst.frc.team4931.robot.RobotMap;
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

//    limitSwitchLow = new DigitalInput(RobotMap.limitSwitchLowPort);
    limitSwitchMid = new DigitalInput(RobotMap.limitSwitchMidPort);
//    limitSwitchHigh = new DigitalInput(RobotMap.limitSwitchHighPort);

    leftGrabberMotor.configForwardLimitSwitchSource(RemoteLimitSwitchSource.RemoteTalonSRX, LimitSwitchNormal.NormallyOpen, RobotMap.rightGrabberMotorPort, 0);
    leftGrabberMotor.configReverseLimitSwitchSource(RemoteLimitSwitchSource.RemoteTalonSRX, LimitSwitchNormal.NormallyOpen, RobotMap.rightGrabberMotorPort, 0);
    rightGrabberMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
    rightGrabberMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
  }

  /**
   * @return true when the high limit switch is closed
   */
  private boolean getHighLimitSwitch() {
    return leftGrabberMotor.getSensorCollection().isFwdLimitSwitchClosed();
  }

  /**
   * @return true when the middle limit switch is closed
   */
  private boolean getMiddleLimitSwitch() {
    return limitSwitchMid.get();
  }

  /**
   * @return true when the low limit switch is closed
   */
  private boolean getLowLimitSwitch() {
    return leftGrabberMotor.getSensorCollection().isRevLimitSwitchClosed();
  }

  @Override
  protected void initDefaultCommand() {

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
   * Moves the grabber towards the target set point.
   */
  public void goToSetPoint() {
    if (setPoint.ordinal() > currentGrabberPosition.ordinal()) {
      //Move grabber up
    } else if (setPoint.ordinal() < currentGrabberPosition.ordinal()) {
      //Move grabber down
    } else {
      //Stop grabber
    }
  }

  /**
   * Sets the target position of the grabber and starts it moving towards it.
   *
   * @param position the target position.
   */
  public void goToSetPoint(GrabberPosition position) {
    setPoint = position;
    goToSetPoint();
  }

  /**
   * Calculates the current position and saves it.
   */
  public void calculateCurrentPosition() {
    if (getLowLimitSwitch()) {
      currentGrabberPosition = GrabberPosition.LOW;
      lastAbsoluteGrabberPosition = currentGrabberPosition;
    } else if (getMiddleLimitSwitch()) {
      currentGrabberPosition = GrabberPosition.MIDDLE;
      lastAbsoluteGrabberPosition = currentGrabberPosition;
    } else if (getHighLimitSwitch()) {
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

  /**
   * Calculate the current portion and set the motor to move towards the set point.
   */
  public void calculateCurrentPositionAndMove() {
    calculateCurrentPosition();
    goToSetPoint();
  }

  /**
   * @return the current grabber position.
   */
  public GrabberPosition getCurrentGrabberPosition() {
    return currentGrabberPosition;
  }

  /**
   * @return the last physical switch the grabber was at.
   */
  public GrabberPosition getLastAbsoluteGrabberPosition() {
    return lastAbsoluteGrabberPosition;
  }

  /**
   * @return if the grabber is at it's target position.
   */
  public boolean atTargetPosition() {
    return currentGrabberPosition == setPoint;
  }
  
  public void log() {
    SmartDashboard.putBoolean("GrabberOpen", open);
  }
}
