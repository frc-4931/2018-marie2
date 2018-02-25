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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4931.robot.RobotMap;
import org.usfirst.frc.team4931.robot.commands.DriveWithJoystick;


/**
 * Determines the driving mechanism
 */
public class Drivetrain extends Subsystem {

  public WPI_TalonSRX leftFrontMotor, leftBackMotor, rightFrontMotor, rightBackMotor;
  private static SpeedControllerGroup leftSideMotors, rightSideMotors;
  private static DifferentialDrive drivetrain;
  private static DoubleSolenoid gearBox;
  private static PigeonIMU pigeon;
  private static final double MAX_VELOCITY_LOW_GEAR = 162;
  private static final double MAX_VELOCITY_HIGH_GEAR = 488;

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
    leftBackMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    rightBackMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    leftBackMotor.setSensorPhase(!RobotMap.leftSideEncoderInverted);
    rightBackMotor.setSensorPhase(!RobotMap.rightSideEncoderInverted);

    resetLeftEncoder();
    resetRightEncoder();

    // Configure pneumatics for 2 speed gearboxes
    gearBox = new DoubleSolenoid(RobotMap.compressor, RobotMap.gearBox[0], RobotMap.gearBox[1]);

    // Create drivetrain from left and right side motor groups
    drivetrain = new DifferentialDrive(leftSideMotors, rightSideMotors);

    // Create gyro senser
    pigeon = new PigeonIMU(rightFrontMotor);
    gyroReset();
  }

  /**
   * Sets the default command to manual control. When manual input is
   * detected, all other automated commands will be shut off
   * automatically.
   */
  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new DriveWithJoystick());
  }

  /**
   * Calculates and sets speed of Drivetrain.
   *
   * @param speed - speed of motors
   * @param rotation - difference between left and right
   */
  public void driveArcade(double speed, double rotation, double throttle) {
    double trueThrottle = 1 - ((throttle/2) + 0.5); //
    // Values are switched because wpi is stupid and somehow switches the values
    drivetrain.arcadeDrive(-(Math.copySign(speed * speed, speed) * trueThrottle), (Math.copySign(rotation * rotation, rotation) * trueThrottle), false);
  }

  /**
   * Sets left and right speed of Drivetrain.
   *
   * @param leftSpeed - speed for left side
   * @param rightSpeed - speed for right side
   */
  public void driveTank(double leftSpeed, double rightSpeed) {
    drivetrain.tankDrive(leftSpeed, rightSpeed, false);
  }

  /**
   * Returns value of left encoder in revolutions.
   */
  public int getLeftEncoder() {
    return rightBackMotor.getSelectedSensorPosition(0);
  }

  /**
   * Returns value of right encoder in revolutions.
   */
  public int getRightEncoder() {
    return leftBackMotor.getSelectedSensorPosition(0);
  }

  /**
   * @return the velocity of the left side in Pulses/100ms
   */
  public double getLeftVelocity() {
    return rightBackMotor.getSelectedSensorVelocity(0);
  }

  /**
   * @return the velocity of the right in Pulses/100ms
   */
  public double getRightVelocity() {
    return leftBackMotor.getSelectedSensorVelocity(0);
  }

  /**
   * Resets value of left encoder.
   */
  public void resetLeftEncoder() {
    rightBackMotor.setSelectedSensorPosition(0, 0, 0);
  }

  /**
   * Resets value of right encoder.
   */
  public void resetRightEncoder() {
    leftBackMotor.setSelectedSensorPosition(0, 0, 0);
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
   * @return the gearbox position
   */
  public Value getGearState() {
    return gearBox.get();
  }

  /**
   * Reads the angle of the gyro.
   */
  public double gyroReadYawAngle() {
    double[] values = new double[3];
    pigeon.getYawPitchRoll(values);
    return -values[0];
  }

  /**
   * Reads the gyro rate.
   *
   * @return The rate of the gyro
   */
  public double gyroReadYawRate() {
    double[] values = new double[3];
    pigeon.getRawGyro(values);
    return -values[0];
  }

  /**
   * Resets the gyro.
   */
  public void gyroReset() {
    pigeon.setYaw(0, 0);
  }

  /**
   * Disables Drivetrain.
   */
  public void disable() {
    leftSideMotors.disable();
    rightSideMotors.disable();
    gearBox.set(Value.kOff);
  }

  //TODO remove code to calculate max acceleration and jerk
  //To get M/S: divide by 480, multiply by 6(pi) * 10 * 0.0254.
  long lastTime;
  double lastLeft, lastRight, maxLeftAcc, maxRightAcc, maxLeftJerk, maxRightJerk, maxLeftSpeed, maxRightSpeed;
  public void log() {

    long curTime = System.currentTimeMillis();
    double leftEnc = getLeftVelocity();
    double rightEnc = getRightVelocity();
    double deltaLeft = leftEnc - lastLeft;
    double deltaRight = rightEnc - lastRight;
    long deltaTime = curTime - lastTime;
    double leftAcc = deltaLeft * 100 / (deltaTime);
    double rightAcc = deltaRight * 100 / (deltaTime);
    lastTime = curTime;
    lastLeft = leftEnc;
    lastRight = rightEnc;

    double leftJerk = leftAcc * 100 / deltaTime;
    double rightJerk = rightAcc * 100 / deltaTime;

    maxLeftAcc = (leftAcc > maxLeftAcc) ? leftAcc : maxLeftAcc;
    maxRightAcc = (rightAcc > maxRightAcc) ? rightAcc : maxRightAcc;
    maxLeftJerk = (leftJerk > maxLeftJerk) ? leftJerk : maxRightJerk;
    maxRightJerk = (rightJerk > maxRightJerk) ? rightJerk : maxRightJerk;
    maxLeftSpeed = (leftEnc > maxLeftSpeed) ? leftEnc : maxLeftSpeed;
    maxRightSpeed = (rightEnc > maxRightSpeed) ? rightEnc : maxRightSpeed;

    SmartDashboard.putNumber("Left Acc", leftAcc);
    SmartDashboard.putNumber("Right Acc", rightAcc);
    SmartDashboard.putNumber("Left Jerk", leftJerk);
    SmartDashboard.putNumber("Right Jerk", rightJerk);
    SmartDashboard.putNumber("Left Max Acc", maxLeftAcc);
    SmartDashboard.putNumber("Right Max Acc", maxRightAcc);
    SmartDashboard.putNumber("Left Max Jerk", maxLeftJerk);
    SmartDashboard.putNumber("Right Max Jerk", maxRightJerk);
    SmartDashboard.putNumber("Max Left Speed", maxLeftSpeed);
    SmartDashboard.putNumber("Max Right Speed", maxRightSpeed);


    SmartDashboard.putNumber("Left Side Speed", leftSideMotors.get());
    SmartDashboard.putNumber("Right Side Speed", rightSideMotors.get());

    SmartDashboard.putNumber("Left Encoder", getLeftEncoder());
    SmartDashboard.putNumber("Right Encoder", getRightEncoder());
    SmartDashboard.putNumber("Left Velocity", getLeftVelocity());
    SmartDashboard.putNumber("Right Velocity", getRightVelocity());

    SmartDashboard.putNumber("Gyro Angle", gyroReadYawAngle());
    SmartDashboard.putNumber("Gyro Rate", gyroReadYawRate());

    SmartDashboard.putBoolean("Low Gear", getGearState() == Value.kReverse);
  }

  /**
   * Reads the velocity of the motors and compares it to our target velocity, and shifts up or down based on the information.
   */
  public void autoShift() {
    double leftSpeed, rightSpeed;
    boolean leftMotorStrain, rightMotorStrain;
    double leftTargetSpeed = leftSideMotors.get();
    double rightTargetSpeed = rightSideMotors.get();

    if (getGearState() == Value.kForward) { //High Gear
      leftSpeed = getLeftVelocity() / MAX_VELOCITY_HIGH_GEAR;
      rightSpeed = getRightVelocity() / MAX_VELOCITY_HIGH_GEAR;
      leftMotorStrain = (Math.abs(leftSpeed) / Math.abs(leftTargetSpeed)) < 0.3;
      rightMotorStrain = (Math.abs(rightSpeed) / Math.abs(rightTargetSpeed)) < 0.3;

      if ((Math.abs(leftTargetSpeed) > 0.5 || Math.abs(rightTargetSpeed) > 0.5) && (leftMotorStrain || rightMotorStrain)) {
        switchLowGear();
      }
    } else if (getGearState() == Value.kReverse) { //Low Gear
      leftSpeed = getLeftVelocity() / MAX_VELOCITY_LOW_GEAR;
      rightSpeed = getRightVelocity() / MAX_VELOCITY_LOW_GEAR;
      boolean highSpeed = leftSpeed >= 0.9 && rightSpeed >= 0.9;

      if (highSpeed) {
        switchHighGear();
      }
    }
  }
}
