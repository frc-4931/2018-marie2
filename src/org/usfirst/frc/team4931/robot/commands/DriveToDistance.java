package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;

/**
 * Created by jcrane on 1/16/18.
 */
public class DriveToDistance extends Command {
  private double targetDistance;

  DriveToDistance(double targetDistance) {
    requires(Robot.drivetrain);
    this.targetDistance = targetDistance;
  }

  @Override
  protected void initialize() {
    Robot.drivetrain.setLeftPIDTaret(targetDistance);
    Robot.drivetrain.setRightPIDTaret(targetDistance);
  }

  @Override
  protected boolean isFinished() {
    if (Robot.drivetrain.leftPIDOnTarget() && Robot.drivetrain.rightPIOnTarget())
      return true;
    else
      return false;
  }
}
