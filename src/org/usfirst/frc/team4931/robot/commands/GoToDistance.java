package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;

public class GoToDistance extends Command {
  private static final double FEET_TO_ENCODER_COUNTS = 12 / (Math.PI*6) * 480;
  private static final double MIN_SPEED = 0.1;
  private static final double THRESHOLD_DISTANCE = 5 * FEET_TO_ENCODER_COUNTS;
  private double speed;
  private double distance;

  public GoToDistance(double speed, double distance) {
    requires(Robot.drivetrain);

    this.speed = speed;
    this.distance = distance * 12 / (Math.PI*6) * 480;

    System.out.println("Distance: " + distance);
  }

  @Override
  protected void initialize() {
    Robot.drivetrain.gyroReset();
    Robot.drivetrain.resetLeftEncoder();
  }

  @Override
  protected void execute() {
    double correction = Robot.drivetrain.gyroReadYawAngle() / 50;
    double currentDistance = Robot.drivetrain.getLeftEncoder();

    double calcSpeed = ramp(currentDistance, distance, THRESHOLD_DISTANCE, speed, MIN_SPEED);
    calcSpeed = Math.max(0, calcSpeed);

    Robot.drivetrain.driveTank(calcSpeed - correction, calcSpeed + correction);

    System.out.println("Speed: " + calcSpeed + "\n" + "Current Distance: " + currentDistance);
  }

  @Override
  protected boolean isFinished() {
    return Robot.drivetrain.getLeftEncoder() >= distance;
  }

  /**
   * Calculates a speed from minSpeed to maxSpeed, with a ramp up and down based on thresholdDistance. All values assumed non-negative.
   *
   * @param currentDistance The current distance you are at
   * @param targetDistance The target distance you want to move
   * @param thresholdDistance How close to start and end you want to end your ramp up and start your ramp down, respectively.
   * @param maxSpeed The maximum speed you want to output
   * @param minSpeed The minimum speed you want to output
   * @return a speed between minSpeed and maxSpeed
   */
  private double ramp(double currentDistance, double targetDistance, double thresholdDistance, double maxSpeed, double minSpeed) {
    return Math.min(Math.min(currentDistance, targetDistance - currentDistance) / thresholdDistance, 1) * (maxSpeed - minSpeed) + minSpeed;
  }
}
