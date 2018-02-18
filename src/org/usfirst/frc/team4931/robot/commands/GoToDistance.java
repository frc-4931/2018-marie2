package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;

public class GoToDistance extends Command {
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
    Robot.drivetrain.driveTank(speed, speed);
  }

  @Override
  protected void execute() {
    double correction = Robot.drivetrain.gyroReadYawAngle() / 50;
    Robot.drivetrain.driveTank(speed - correction, speed + correction);
  }

  @Override
  protected boolean isFinished() {
    return Robot.drivetrain.getLeftEncoder() >= distance;
  }
}
