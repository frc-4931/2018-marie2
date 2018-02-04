package org.usfirst.frc.team4931.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4931.robot.RobotMap;
import org.usfirst.frc.team4931.robot.commands.LiftWithJoystick;
/**
 * This defines the major component of the lift subsystem.
 */
public class Lift extends Subsystem {
  private WPI_TalonSRX liftMotor;
  private double setPoint;

/**
 * Creates a new lift. This sets up the motors and potentiometers necessary for lifting.
 */
  public Lift() {
    liftMotor = new WPI_TalonSRX(RobotMap.liftMotorPort);
    liftMotor.setInverted(RobotMap.liftMotorInverted);
    liftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    liftMotor.setSelectedSensorPosition(0, 0, 0);
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
   * @param liftHeight The desired height to move to.
   */
  public void setLiftHeight(FixedLiftHeight liftHeight) {
    switch (liftHeight) {
      case SCALE_TOP:
        setPoint = 480; //TODO set proper height value
        break;
      case SCALE_MID:
        setPoint = 360; //TODO set proper height value
        break;
      case SWITCH:
        setPoint = 240; //TODO set proper height value
        break;
      case EXCHANGE:
        setPoint = 120; //TODO set proper height value
        break;
      case FLOOR:
        setPoint = 0; //TODO set proper height value
        break;
    }
    liftMotor.set(ControlMode.MotionMagic, setPoint);
  }
  
  public void lift(double speed) {
    liftMotor.set(ControlMode.PercentOutput, speed);
  }
  
  public void log() {
  }
  
}
