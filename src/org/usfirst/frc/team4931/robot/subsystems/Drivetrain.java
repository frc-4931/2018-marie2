package org.usfirst.frc.team4931.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.usfirst.frc.team4931.robot.RobotMap;

public class Drivetrain extends Subsystem {

  private static WPI_TalonSRX leftFrontMotor;
  private static WPI_TalonSRX leftBackMotor;
  private static WPI_TalonSRX rightFrontMotor;
  private static WPI_TalonSRX rightBackMotor;
  private static SpeedControllerGroup leftSideMotors;
  private static SpeedControllerGroup rightSideMotors;
  private static DifferentialDrive drivetrain;
  private static DoubleSolenoid gearBox;
  private static ADXRS450_Gyro gyro;

  public Drivetrain() {
    initialization();
  }

  public void initialization() {
    //Configure drive motors
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

    //Configure encoders
    leftFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 50);
    rightFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 50);

    //Configure pneumatics for 2 speed gearboxes
    gearBox = new DoubleSolenoid(RobotMap.gearBox[0], RobotMap.gearBox[1]);

    //Create drivetrain from left and right side motor groups
    drivetrain = new DifferentialDrive(leftSideMotors, rightSideMotors);

    //Create gyro senser
    gyro = new ADXRS450_Gyro(RobotMap.gyroPort);
  }

  @Override
  protected void initDefaultCommand() {
  }

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
  public double getLeftEncoder() {
    return leftFrontMotor.getSelectedSensorPosition(0);
  }

  /**
   * Returns value of right encoder in revolutions.
   */
  public double getRightEncoder() {
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
  public void swithLowGear() {
    gearBox.set(Value.kReverse);
  }

  /**
   * Reads the angle of the gyro.
   */
  public double gyroReadAngle() {
    return gyro.getAngle();
  }

  /**
   * Reads the gyro rate.
   *
   * @return The rate of the gyro
   */
  public double gyroReadRate() {
    return gyro.getRate();
  }

  /**
   * Resets the gyro.
   */
  public void gyroReset() {
    gyro.reset();
  }

  /**
   * CalibRATES the gyro.
   */
  public void gyroCalibrate() {
    gyro.calibrate();
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