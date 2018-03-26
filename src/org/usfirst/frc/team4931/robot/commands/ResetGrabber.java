package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;

public class ResetGrabber extends Command {

  @Override
  protected void initialize() {
    Robot.grabber.reset();
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}
