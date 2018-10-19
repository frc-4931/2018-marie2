package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;
import org.usfirst.frc.team4931.robot.subsystems.Grabber;

public class ResetGrabber extends Command {
  private Grabber grabber;
  private double startTime;


  public ResetGrabber() {
    requires(Robot.getGrabber());
  }

  @Override
  protected void initialize() {
    grabber = Robot.getGrabber();

    grabber.setSpeed(-0.15);
    startTime = System.currentTimeMillis();
  }

  @Override
  protected boolean isFinished() {
    return grabber.getForwardLimitSwitch() || (System.currentTimeMillis() - startTime) > 2000;
  }

  @Override
  protected void end() {
    grabber.setSpeed(0);
    grabber.resetZero();
  }
}
