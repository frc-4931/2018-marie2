package org.usfirst.frc.team4931.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.usfirst.frc.team4931.robot.Robot;
import org.usfirst.frc.team4931.robot.RobotMap;

public class Drivetrain extends Subsystem {

  private static WPI_TalonSRX leftFrontMotor;
  private static WPI_TalonSRX leftBackMotor;
  private static WPI_TalonSRX rightFrontMotor;
  private static WPI_TalonSRX rightBackMotor;
  private static SpeedControllerGroup leftSideMotors;
  private static SpeedControllerGroup rightSideMotors;
  private static PIDController leftSidePID;
  private static PIDController rightSidePID;
  private static Encoder leftEncoder;
  private static Encoder rightEncoder;
  private static DifferentialDrive drivetrain;
  private static DoubleSolenoid leftGearBox;
  private static DoubleSolenoid rightGearBox;
  private static ADXRS450_Gyro gyro;

  @Override
  protected void initDefaultCommand() {
    //Configure drive motors
    leftFrontMotor = new WPI_TalonSRX(RobotMap.leftFrontMotorPort);
    leftBackMotor = new WPI_TalonSRX(RobotMap.leftBackMotorPort);
    rightFrontMotor = new WPI_TalonSRX(RobotMap.rightFrontMotorPort);
    rightBackMotor = new WPI_TalonSRX(RobotMap.rightBackMotorPort);
    leftFrontMotor.setInverted(RobotMap.leftFrontMotorInverted);
    leftBackMotor.setInverted(RobotMap.leftBackMotorInverted);
    rightFrontMotor.setInverted(RobotMap.rightFrontMotorInverted);
    rightBackMotor.setInverted(RobotMap.rightBackMotorInverted);
    leftSideMotors = new SpeedControllerGroup(leftFrontMotor, leftBackMotor);
    rightSideMotors = new SpeedControllerGroup(rightFrontMotor, rightBackMotor);

    //Configure encoders
    leftEncoder = new Encoder(RobotMap.leftEncoderPorts[0], RobotMap.leftEncoderPorts[1],
        RobotMap.leftEncoderInverted, EncodingType.k4X);
    rightEncoder = new Encoder(RobotMap.rightEncoderPorts[0], RobotMap.rightEncoderPorts[1],
        RobotMap.rightEncoderInverted, EncodingType.k4X);
    leftEncoder.setDistancePerPulse(1.0 / RobotMap.encoderPPR);
    rightEncoder.setDistancePerPulse(1.0 / RobotMap.encoderPPR);

    //Configure PID controllers
    leftSidePID = new PIDController(0, 0, 0, 0, leftEncoder, leftFrontMotor);
    rightSidePID = new PIDController(0, 0, 0, 0, rightEncoder, rightFrontMotor);

    //Configure pneumatics for 2 speed gearboxes
    leftGearBox = new DoubleSolenoid(RobotMap.leftGearBox[0], RobotMap.leftGearBox[1]);
    rightGearBox = new DoubleSolenoid(RobotMap.rightGearBox[0], RobotMap.rightGearBox[1]);

    //Create drivetrain from left and right side motor groups
    drivetrain = new DifferentialDrive(leftSideMotors, rightSideMotors);

    //Create gyro senser
    gyro = new ADXRS450_Gyro(RobotMap.gyroPort);
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
    return leftEncoder.getDistance();
  }

  /**
   * Returns value of right encoder in revolutions.
   */
  public double getRightEncoder() {
    return rightEncoder.getDistance();
  }

  /**
   * Resets value of left encoder.
   */
  public void resetLeftEncoder() {
    leftEncoder.reset();
  }

  /**
   * Resets value of right encoder.
   */
  public void resetRightEncoder() {
    rightEncoder.reset();
  }

  /**
   * Sets the target for the left side PID loop in revaluations
   *
   * @param target - target in revaluations
   */
  public void setLeftPIDTaret(double target) {
    leftSidePID.setSetpoint(target * RobotMap.encoderPPR);
  }

  /**
   * Sets the target for the right side PID loop in revaluations
   *
   * @param target - target in revaluations
   */
  public void setRightPIDTaret(double target) {
    rightSidePID.setSetpoint(target * RobotMap.encoderPPR);
  }

  public boolean leftPIDOnTarget() {
    return leftSidePID.onTarget();
  }

  public boolean rightPIOnTarget() {
    return rightSidePID.onTarget();
  }

  /**
   * Frees PID controllers.
   */
  public void freePIDControllers() {
    leftSidePID.free();
    rightSidePID.free();
  }

  /**
   * Switches the Drivetrain to the high speed gear.
   */
  public void switchHighGear() {
    leftGearBox.set(Value.kForward);
    rightGearBox.set(Value.kForward);
  }

  /**
   * Switches the Drivetrain to the low speed gear.
   */
  public void swithLowGear() {
    leftGearBox.set(Value.kReverse);
    rightGearBox.set(Value.kReverse);
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
    leftGearBox.set(Value.kOff);
    rightGearBox.set(Value.kOff);
  }

}