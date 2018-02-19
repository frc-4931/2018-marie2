package org.usfirst.frc.team4931.robot.commands;

import org.usfirst.frc.team4931.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
/**
 * Makes the robot turn to a specific angle
 *
 */
public class TurnToAngle extends Command {
  private static final double MIN_SPEED = 0.1;
  private static final double THRESHOLD_ANGLE = 45;
  private double speed;
  private double angle;

  public TurnToAngle(double speed, double angle) {
    requires(Robot.drivetrain);

    this.speed = speed / 3;
    this.angle = angle;
    System.out.println("Angle: " + Math.copySign(angle, speed));
  }

  @Override
  protected void initialize() {
    Robot.drivetrain.gyroReset();
  }

  @Override
  protected void execute() {
    double currentAngle = Robot.drivetrain.gyroReadYawAngle();
    double calcSpeed = ramp(speed, MIN_SPEED, currentAngle, angle, THRESHOLD_ANGLE);

    Robot.drivetrain.driveTank(calcSpeed, -calcSpeed);
  }

  @Override
  protected boolean isFinished() {
    if (Math.abs(Robot.drivetrain.gyroReadYawAngle()) >= Math.abs(angle)) {
      System.out.println("Turn finished");
      return true;
    } else {
      return false;
    }
  }

  @Override
  protected void end() {
    Robot.drivetrain.driveTank(0, 0);
  }

  /**
   * Calculates a speed from minSpeed to maxSpeed, with a ramp up and down based on thresholdDistance. All values assumed non-negative.
   *
   * @param currentAngle The current distance you are at
   * @param targetAngle The target distance you want to move
   * @param thresholdAngle How close to start and end you want to end your ramp up and start your ramp down, respectively.
   * @param maxSpeed The maximum speed you want to output
   * @param minSpeed The minimum speed you want to output
   * @return a speed between minSpeed and maxSpeed
   */
  private double ramp(double maxSpeed, double minSpeed, double currentAngle, double targetAngle, double thresholdAngle) {
    return Math.min(Math.min(currentAngle, targetAngle - currentAngle) / thresholdAngle, 1) * (maxSpeed - minSpeed) + minSpeed;
  }

}
