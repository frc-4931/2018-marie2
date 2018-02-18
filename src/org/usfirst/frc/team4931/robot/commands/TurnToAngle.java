package org.usfirst.frc.team4931.robot.commands;

import org.usfirst.frc.team4931.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
/**
 * Makes the robot turn to a specific angle
 *
 */
public class TurnToAngle extends Command {
  private double speed;
  private double angle;

  public TurnToAngle(double speed, double angle) {
    requires(Robot.drivetrain);

    this.speed = speed;
    this.angle = angle;
    System.out.println("Angle: " + Math.copySign(angle, speed));
  }

  @Override
  protected void initialize() {
    Robot.drivetrain.gyroReset();
    Robot.drivetrain.driveTank(speed, -speed);
  }

  @Override
  protected boolean isFinished() {
    if (Math.abs(Robot.drivetrain.gyroReadYawAngle()) >= Math.abs(angle)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  protected void end() {
    Robot.drivetrain.driveTank(0, -0);
  }

}
