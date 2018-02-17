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

  DriveByTrajectory(TankModifier tankModifier) {
    requires(Robot.drivetrain);
    leftEncoderFollower = new EncoderFollower(tankModifier.getLeftTrajectory());
    rightEncoderFollower = new EncoderFollower(tankModifier.getRightTrajectory());

    leftEncoderFollower.configureEncoder(0, RobotMap.encoderPPR, RobotMap.WHEEL_DIAMETER);
    rightEncoderFollower.configureEncoder(0, RobotMap.encoderPPR, RobotMap.WHEEL_DIAMETER);

    leftEncoderFollower.configurePIDVA(RobotMap.TRAJ_PROPORTIONAL, RobotMap.TRAJ_INTEGRAL, RobotMap.TRAJ_DERIVATIVE, RobotMap.TRAJ_VELOCITY, RobotMap.TRAJ_ACCELERATION);
    rightEncoderFollower.configurePIDVA(RobotMap.TRAJ_PROPORTIONAL, RobotMap.TRAJ_INTEGRAL, RobotMap.TRAJ_DERIVATIVE, RobotMap.TRAJ_VELOCITY, RobotMap.TRAJ_ACCELERATION);

    Robot.drivetrain.resetLeftEncoder();
    Robot.drivetrain.resetRightEncoder();

    leftEncoderFollower.reset();
    rightEncoderFollower.reset();

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
    double correction = ((curTrajectoryHeading - startingHeading) - Math.toRadians(Robot.drivetrain.gyroReadYawAngle())) / RobotMap.TRAJ_CORRECTION_AMOUNT;

    double leftCorrection = correction;
    double rightCorrection = correction;

    System.out.println("Left Speed: "+leftSpeed+"     "+"Right Speed: "+rightSpeed);
    System.out.println("Left Correction: "+leftCorrection+"     "+"Right Correction: "+rightCorrection);
    Robot.drivetrain.driveTank((leftSpeed+leftCorrection)/12, (rightSpeed-rightCorrection)/12);
  }

  /**
   * @return true when both encoders are finished
   */
  @Override
  protected boolean isFinished() {
    return leftEncoderFollower.isFinished() && rightEncoderFollower.isFinished();
  }
}