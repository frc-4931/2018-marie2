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

  private WPI_TalonSRX leftFrontMotor, leftBackMotor, rightFrontMotor, rightBackMotor;
  private static SpeedControllerGroup leftSideMotors, rightSideMotors;
  private static DifferentialDrive drivetrain;
  private static DoubleSolenoid gearBox;
  private static PigeonIMU pigeon;
  private static final double MAX_VELOCITY_LOW_GEAR = 10; //TODO set max velocity in Pulses/100ms
  private static final double MAX_VELOCITY_HIGH_GEAR = 10; //TODO set max velocity in Pulses/100ms

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

    // Configure pneumatics for 2 speed gearboxes
    gearBox = new DoubleSolenoid(RobotMap.compressor, RobotMap.gearBox[0], RobotMap.gearBox[1]);

    // Create drivetrain from left and right side motor groups
    drivetrain = new DifferentialDrive(leftSideMotors, rightSideMotors);

    // Create gyro senser
    pigeon = new PigeonIMU(rightFrontMotor);
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
    double trueThrottle = (throttle + 1)/2;
    // Values are switched because wpi is stupid and somehow switches the values
    drivetrain.arcadeDrive((Math.copySign(rotation * rotation, rotation) * trueThrottle), (Math.copySign(speed * speed, speed) * trueThrottle), false);
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
    return leftBackMotor.getSelectedSensorPosition(0);
  }

  /**
   * Returns value of right encoder in revolutions.
   */
  public int getRightEncoder() {
    return rightBackMotor.getSelectedSensorPosition(0);
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
    return rightBackMotor.getSelectedSensorVelocity(0);
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


  public void log() {
    SmartDashboard.putNumber("LFront", leftFrontMotor.get());
    SmartDashboard.putNumber("LRear", leftBackMotor.get());
    SmartDashboard.putNumber("RFront", rightFrontMotor.get());
    SmartDashboard.putNumber("RRear", rightBackMotor.get());
    SmartDashboard.putNumber("Gyro Angle", gyroReadYawAngle());
    SmartDashboard.putNumber("Gyro Rate", gyroReadYawRate());
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
      leftMotorStrain = (Math.abs(leftSpeed) / Math.abs(leftTargetSpeed)) < 0.5;
      rightMotorStrain = (Math.abs(rightSpeed) / Math.abs(rightTargetSpeed)) < 0.5;

      if (leftMotorStrain || rightMotorStrain) {
        switchLowGear();
      }
    } else if (getGearState() == Value.kReverse) { //Low Gear
      leftSpeed = getLeftVelocity() / MAX_VELOCITY_LOW_GEAR;
      rightSpeed = getRightVelocity() / MAX_VELOCITY_LOW_GEAR;
      leftMotorStrain = (Math.abs(leftSpeed) / Math.abs(leftTargetSpeed)) < 0.5;
      rightMotorStrain = (Math.abs(rightSpeed) / Math.abs(rightTargetSpeed)) < 0.5;

      boolean highSpeed = leftSpeed >= 0.9 && rightSpeed >= 0.9;

      if (highSpeed && (!leftMotorStrain && !rightMotorStrain)) {
        switchHighGear();
      }
    }
  }
}
