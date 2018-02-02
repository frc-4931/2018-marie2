package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
import org.usfirst.frc.team4931.robot.Robot;

/**
 * Created by jcrane on 1/16/18.
 */
public class DriveByTrajectory extends Command {

  private TankModifier tankModifier;
  private EncoderFollower leftEncoderFollower;
  private EncoderFollower rightEncoderFollower;

  DriveByTrajectory(TankModifier tankModifier) {
    requires(Robot.drivetrain);
    this.tankModifier = tankModifier;
    leftEncoderFollower = new EncoderFollower(tankModifier.getLeftTrajectory());
    rightEncoderFollower = new EncoderFollower(tankModifier.getRightTrajectory());
  }

  @Override
  protected void execute() {
    double leftSpeed = leftEncoderFollower.calculate(Robot.drivetrain.getLeftEncoder());
    double rightSpeed = rightEncoderFollower.calculate(Robot.drivetrain.getRightEncoder());
    Robot.drivetrain.driveTank(leftSpeed, rightSpeed);
  }

  @Override
  protected boolean isFinished() {
    return leftEncoderFollower.isFinished() && rightEncoderFollower.isFinished();
  }
}
