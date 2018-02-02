package org.usfirst.frc.team4931.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.usfirst.frc.team4931.robot.RobotMap;

/**
 * Determines the driving mechanism
 */
public class Drivetrain extends Subsystem {

  private WPI_TalonSRX leftFrontMotor, leftBackMotor, rightFrontMotor, rightBackMotor;
  private static SpeedControllerGroup leftSideMotors, rightSideMotors;
  private static DifferentialDrive drivetrain;
  private static DoubleSolenoid gearBox;
  private static PigeonIMU pigeon;

  public Drivetrain() {
    initialization();
  }

  /**
   * Initializes each motors.
   */
  public void initialization() {
    // Configure drive motors
    leftFrontMotor = new WPI_TalonSRX(RobotMap.leftFrontMotorPort);
    leftBackMotor = new WPI_TalonSRX(RobotMap.leftBackMotorPort);
    rightFrontMotor = new WPI_TalonSRX(RobotMap.rightFrontMotorPort);
    rightBackMotor = new WPI_TalonSRX(RobotMap.rightBackMotorPort);
    leftFrontMotor.setInverted(RobotMap.leftFrontMotorInverted);
    leftBackMotor.setInverted(RobotMap.leftBackMotorInverted);
    rightFrontMotor.setInverted(RobotMap.rightFrontMotorInverted);
    rightBackMotor.setInverted(RobotMap.rightBackMotorInverted);
    leftFrontMotor.setNeutralMode(NeutralMode.Brake);
    leftBackMotor.setNeutralMode(NeutralMode.Brake);
    rightFrontMotor.setNeutralMode(NeutralMode.Brake);
    rightBackMotor.setNeutralMode(NeutralMode.Brake);
    leftSideMotors = new SpeedControllerGroup(leftFrontMotor, leftBackMotor);
    rightSideMotors = new SpeedControllerGroup(rightFrontMotor, rightBackMotor);

    // Configure encoders
    leftFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 50);
    rightFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 50);

    // Configure pneumatics for 2 speed gearboxes
    gearBox = new DoubleSolenoid(RobotMap.gearBox[0], RobotMap.gearBox[1]);

    // Create drivetrain from left and right side motor groups
    drivetrain = new DifferentialDrive(leftSideMotors, rightSideMotors);

    // Create gyro senser
    pigeon = new PigeonIMU(rightBackMotor);
  }

  @Override
  protected void initDefaultCommand() {}

  /**
   * Sets speed of Drivetrain.
   *
   * @param speed - speed of motors
   * @param rotation - difference between left and right
   */
  public void driveArcade(double speed, double rotation) {
    drivetrain.arcadeDrive(speed, rotation);
  }

  /**
   * Sets left and right speed of Drivetrain.
   *
   * @param leftSpeed - speed for left side
   * @param rightSpeed - speed for right side
   */
  public void driveTank(double leftSpeed, double rightSpeed) {
    drivetrain.tankDrive(leftSpeed, rightSpeed);
  }

  /**
   * Returns value of left encoder in revolutions.
   */
  public int getLeftEncoder() {
    return leftFrontMotor.getSelectedSensorPosition(0);
  }

  /**
   * Returns value of right encoder in revolutions.
   */
  public int getRightEncoder() {
    return rightFrontMotor.getSelectedSensorPosition(0);
  }

  /**
   * Resets value of left encoder.
   */
  public void resetLeftEncoder() {
    leftFrontMotor.setSelectedSensorPosition(0, 0, 50);
  }

  /**
   * Resets value of right encoder.
   */
  public void resetRightEncoder() {
    rightFrontMotor.setSelectedSensorPosition(0, 0, 50);
  }

  /**
   * Sets the target for the left side motors in revaluations
   *
   * @param target - target in revaluations
   */
  public void setLeftSetPoint(double target) {
    leftFrontMotor.set(ControlMode.MotionMagic, target * RobotMap.encoderPPR);
    leftBackMotor.follow(leftFrontMotor);
  }

  /**
   * Sets the target for the right side motors in revaluations
   *
   * @param target - target in revaluations
   */
  public void setRightSetPoint(double target) {
    rightFrontMotor.set(ControlMode.MotionMagic, target * RobotMap.encoderPPR);
    rightBackMotor.follow(rightFrontMotor);
  }

  /**
   * Switches the Drivetrain to the high speed gear.
   */
  public void switchHighGear() {
    gearBox.set(Value.kForward);
  }

  /**
   * Switches the Drivetrain to the low speed gear.
   */
  public void switchLowGear() {
    gearBox.set(Value.kReverse);
  }

  /**
   * Reads the angle of the gyro.
   */
  public double gyroReadYawAngle() {
    double[] values = new double[3];
    pigeon.getYawPitchRoll(values);
    return values[0];
  }

  /**
   * Reads the gyro rate.
   *
   * @return The rate of the gyro
   */
  public double gyroReadYawRate() {
    double[] values = new double[3];
    pigeon.getRawGyro(values);
    return values[0];
  }

  /**
   * Resets the gyro.
   */
  public void gyroReset() {
    pigeon.setYaw(0, 50);
  }

  /**
   * Disables Drivetrain.
   */
  public void disable() {
    leftSideMotors.disable();
    rightSideMotors.disable();
    gearBox.set(Value.kOff);
  }

}
