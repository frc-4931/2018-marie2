package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
import org.usfirst.frc.team4931.robot.Robot;
import org.usfirst.frc.team4931.robot.RobotMap;

public class DriveByTrajectory extends Command {
  private EncoderFollower leftEncoderFollower;
  private EncoderFollower rightEncoderFollower;
  private double startingHeading;

  DriveByTrajectory(TankModifier tankModifier) {
    requires(Robot.drivetrain);
    leftEncoderFollower = new EncoderFollower(tankModifier.getLeftTrajectory());
    rightEncoderFollower = new EncoderFollower(tankModifier.getRightTrajectory());
    leftEncoderFollower.configureEncoder(0, RobotMap.encoderPPR, .10); //FIXME Wheel diameter
    rightEncoderFollower.configureEncoder(0, RobotMap.encoderPPR, .10); //FIXME Wheel diameter
    Robot.drivetrain.resetLeftEncoder();
    Robot.drivetrain.resetRightEncoder();
   }

  @Override
  protected void execute() {
    double leftSpeed = leftEncoderFollower.calculate(Robot.drivetrain.getLeftEncoder());
    double rightSpeed = rightEncoderFollower.calculate(Robot.drivetrain.getRightEncoder());

    double corrention = rightEncoderFollower.getHeading() - Robot.drivetrain.gyroReadYawAngle();

    Robot.drivetrain.driveTank(leftSpeed, rightSpeed);
  }

  @Override
  protected boolean isFinished() {
    return leftEncoderFollower.isFinished() && rightEncoderFollower.isFinished();
  }
}