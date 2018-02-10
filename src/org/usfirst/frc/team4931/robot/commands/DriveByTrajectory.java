package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
import org.usfirst.frc.team4931.robot.Robot;
import org.usfirst.frc.team4931.robot.RobotMap;

/**
 * Drives the drivetrain in the trajectory that was determined.
 *
 * @author shawn ely
 */
public class DriveByTrajectory extends Command {
  private EncoderFollower leftEncoderFollower;
  private EncoderFollower rightEncoderFollower;
  private double startingHeading;
  private final double CORRECTION_AMOUNT = 100; //Lower number means more correction

  DriveByTrajectory(TankModifier tankModifier) {
    requires(Robot.drivetrain);
    leftEncoderFollower = new EncoderFollower(tankModifier.getLeftTrajectory());
    rightEncoderFollower = new EncoderFollower(tankModifier.getRightTrajectory());
    leftEncoderFollower.configureEncoder(0, RobotMap.encoderPPR, .10); //FIXME Wheel diameter
    rightEncoderFollower.configureEncoder(0, RobotMap.encoderPPR, .10); //FIXME Wheel diameter
    Robot.drivetrain.resetLeftEncoder();
    Robot.drivetrain.resetRightEncoder();
    this.startingHeading = leftEncoderFollower.getHeading();
   }

  /**
   * Sets the motor speed based on the encoder readings along the trajectory
   */
  @Override
  protected void execute() {
    double leftSpeed = leftEncoderFollower.calculate(Robot.drivetrain.getLeftEncoder());
    double rightSpeed = rightEncoderFollower.calculate(Robot.drivetrain.getRightEncoder());
    double curTrajectoryHeading = leftEncoderFollower.getHeading();
    double correction = ((curTrajectoryHeading - startingHeading) - Robot.drivetrain.gyroReadYawAngle()) / CORRECTION_AMOUNT;

    double leftCorrection = correction;
    double rightCorrection = correction;

    if (Math.abs(leftSpeed + correction) > 1.0) {
      rightCorrection += (leftSpeed > 0) ? (leftSpeed + correction) - 1 : (leftSpeed + correction) + 1;
    }
    if (Math.abs(rightSpeed - correction) > 1.0) {
      leftCorrection += (rightSpeed > 0) ? (rightSpeed + correction) - 1 : (rightSpeed + correction) + 1;
    }

    Robot.drivetrain.driveTank(leftSpeed+leftCorrection, rightSpeed-rightCorrection);
  }

  /**
   * @return true when both encoders are finished
   */
  @Override
  protected boolean isFinished() {
    return leftEncoderFollower.isFinished() && rightEncoderFollower.isFinished();
  }
}