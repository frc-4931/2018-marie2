package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;

public class ReleaseClimber extends Command {

  @Override
  protected void initialize() {
    Robot.climber.release();
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}
