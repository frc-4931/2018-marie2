package org.usfirst.frc.team4931.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4931.robot.RobotMap;
/**
 * This defines the major component of the lift subsystem.
 */
public class Lift extends Subsystem {

  private static final double kP_real = 4;
  private static final double kI_real = 0.07;
  private WPI_TalonSRX liftMotor;
  private AnalogPotentiometer potentiometer;

/**
 * Creates a new lift. This sets up the motors and potentiometers necessary for lifting
 */
  public Lift() {
//    super(kP_real, kI_real, 0);
//    setAbsoluteTolerance(0.005);
    liftMotor = new WPI_TalonSRX(RobotMap.liftMotorPort);
    potentiometer = new AnalogPotentiometer(2, -2.0 / 5);
    addChild("Motor", liftMotor);
    addChild("Potentiometer", potentiometer);
  }
/**
 * Used to determine if the lift has reached its height
 * @return true when the lift is at its height
 */
  public boolean isFinished() {
    return true;
  }
  
  @Override
  protected void initDefaultCommand() {
    // TODO Auto-generated method stub

  }

  public void log() {
    SmartDashboard.putData("Lift Potentiometer", (AnalogPotentiometer) potentiometer);
  }

//  @Override
//  protected double returnPIDInput() {
//    return potentiometer.get();
//  }

//  @Override
//  protected void usePIDOutput(double power) {
//    liftMotor.set(power);
//  }
  /**
   * Sets the lift height
   * @param liftHeight
   */
  public void setLiftHeight(FixedLiftHeight liftHeight) {
    switch (liftHeight) {
      case EXCHANGE:
        //TODO:
        break;
      case SCALE_MID:
        //TODO
        break;
      case FLOOR:
        //TODO
        break;
      case SCALE_TOP:
        //TODO
        break;
      case SWITCH:
        //TODO
        break;
    }
  }
}
