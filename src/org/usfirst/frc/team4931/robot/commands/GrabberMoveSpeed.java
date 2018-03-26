package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;

public class GrabberMoveSpeed extends Command {

  private double speed;

  public GrabberMoveSpeed(double speed) {
    requires(Robot.grabber);
    this.speed = speed;
  }

  @Override
  protected void initialize() {
    Robot.grabber.setSpeed(speed);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}
