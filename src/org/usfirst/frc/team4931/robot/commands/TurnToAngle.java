package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;
/**
 * Makes the robot turn to a specific angle
 *
 */
public class TurnToAngle extends Command {

  private static final double RAMP_UP_THRESHOLD_DISTANCE = 20;
  private static final double RAMP_DOWN_THRESHOLD_DISTANCE = 45;
  private static final double START_SPEED = 0.4;
  private static final double END_SPEED = 0.3;
  private static final double MAX_SPEED = 0.6;
  private double speed;
  private double angle;
  private double startAngle;

  public TurnToAngle(double speed, double angle) {
    requires(Robot.drivetrain);

    this.speed = Math.min(speed, MAX_SPEED);
    this.angle = angle;
    System.out.println("Starting Speed: " + speed);
  }

  @Override
  protected void initialize() {
    startAngle = Robot.drivetrain.gyroReadYawAngle();
  }

  @Override
  protected void execute() {
    double currentAngle = Robot.drivetrain.gyroReadYawAngle() - startAngle;
    double calcSpeed = ramp(Math.abs(currentAngle));

    if (speed > 0) {
      Robot.drivetrain.driveTank(calcSpeed, -calcSpeed);
    } else {
      Robot.drivetrain.driveTank(-calcSpeed, calcSpeed);
    }

    System.out.println("Angle: " + currentAngle + "\n" + "Speed: " + calcSpeed);
  }

  @Override
  protected boolean isFinished() {
    return Math.abs(Robot.drivetrain.gyroReadYawAngle() - startAngle) >= Math.abs(angle);
  }

  @Override
  protected void end() {
    Robot.drivetrain.driveTank(0, 0);
  }

  /**
   * Calculates a speed from minSpeed to maxSpeed, with a ramp up and down based on thresholdDistance. All values assumed non-negative.
   */
  private double ramp(double currentDistance) {
    return calculateSpeed(currentDistance, angle, RAMP_UP_THRESHOLD_DISTANCE,
        RAMP_DOWN_THRESHOLD_DISTANCE, Math.abs(speed), START_SPEED, END_SPEED);

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
