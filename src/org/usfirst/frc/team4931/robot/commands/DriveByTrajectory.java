package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
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

  public DriveByTrajectory(TankModifier tankModifier) {
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
   }

  /**
   * Sets the motor speed based on the encoder readings along the trajectory
   */
  @Override
  protected void execute() {
    double leftSpeed = leftEncoderFollower.calculate(Robot.drivetrain.getLeftEncoder());
    double rightSpeed = rightEncoderFollower.calculate(Robot.drivetrain.getRightEncoder());
    double curTrajectoryHeading = Math.toDegrees(leftEncoderFollower.getHeading());
    double correction = Pathfinder
        .boundHalfDegrees(curTrajectoryHeading + Robot.drivetrain.gyroReadYawAngle());
    double turn = 0.8 * (-1.0 / 80.0) * correction;

    System.out.println("Left Speed: " + leftSpeed + "\n" + "Right Speed: " + rightSpeed);
    System.out.println("Correction: " + correction);
    System.out
        .println("Left Cur: " + (leftSpeed + turn) + "\n" + "Right Cur: " + (rightSpeed - turn));
    leftSpeed += turn;
    rightSpeed -= turn;

    Robot.drivetrain.driveTank(leftSpeed, rightSpeed);
  }

  /**
   * @return true when both encoders are finishedb
   */
  @Override
  protected boolean isFinished() {
    return leftEncoderFollower.isFinished() && rightEncoderFollower.isFinished();
  }
}