package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;

public class GoToDistance extends Command {
  private static final double FEET_TO_ENCODER_COUNTS = 12 / (Math.PI*6) * 480;
  private static final double RAMP_UP_THRESHOLD_DISTANCE = 5 * FEET_TO_ENCODER_COUNTS;
  private static final double RAMP_DOWN_THRESHOLD_DISTANCE = 5 * FEET_TO_ENCODER_COUNTS;
  private static final double START_SPEED = 0.35;
  private static final double END_SPEED = 0.2;
  private double speed;
  private double distance;
  private double leftStart;
  private double rightStart;
  private long startTime;

  public GoToDistance(double speed, double distance) {
    requires(Robot.drivetrain);

    this.speed = speed;
    this.distance = distance * 12 / (Math.PI*6) * 480;

    System.out.println("Distance: " + distance);
  }

  @Override
  protected void initialize() {
    Robot.drivetrain.gyroReset();
//    Robot.drivetrain.resetLeftEncoder();
//    Robot.drivetrain.resetRightEncoder();
    leftStart = Robot.drivetrain.getLeftEncoder();
    rightStart = Robot.drivetrain.getRightEncoder();
    startTime = System.currentTimeMillis();
  }

  @Override
  protected void execute() {
    double correction = Robot.drivetrain.gyroReadYawAngle() / 50;
    double currentDistance = Robot.drivetrain.getLeftEncoder() - leftStart;

    double calcSpeed = ramp(Math.abs(currentDistance));
    calcSpeed = Math.max(0, calcSpeed);

    Robot.drivetrain.driveTank(calcSpeed - correction, calcSpeed + correction);

    if (Math.abs(Robot.drivetrain.getLeftVelocity()) > 20) {
      startTime = System.currentTimeMillis();
    }

    System.out.println("Speed: " + calcSpeed + "\n" + "Current Distance: " + currentDistance);
  }

  @Override
  protected boolean isFinished() {
    return (Math.abs(Robot.drivetrain.getLeftEncoder() - leftStart) >= distance) || (
        System.currentTimeMillis() - startTime > 1000);
  }

  /**
   * Calculates a speed from minSpeed to maxSpeed, with a ramp up and down based on thresholdDistance. All values assumed non-negative.
   */
  private double ramp(double currentDistance) {
    return calculateSpeed(currentDistance, distance, RAMP_UP_THRESHOLD_DISTANCE,
        RAMP_DOWN_THRESHOLD_DISTANCE, speed, START_SPEED, END_SPEED);

    //return Math.min(Math.min(currentDistance, targetDistance - currentDistance) / thresholdDistance, 1) * (maxSpeed - minSpeed) + minSpeed;
  }

  private double calculateSpeed(double current, double target, double rampUpThreshold,
      double rampDownThreshold, double maxSpeed, double startSpeed, double endSpeed) {
    return Math.min(Math.min(Math.pow(current / rampUpThreshold, 0.75), 1) * (maxSpeed - startSpeed)
            + startSpeed,
        Math.min(Math.pow(Math.max((target - current) / rampDownThreshold, 0), 1.5), 1) * (maxSpeed
            - endSpeed)
            + endSpeed);
  }
}
