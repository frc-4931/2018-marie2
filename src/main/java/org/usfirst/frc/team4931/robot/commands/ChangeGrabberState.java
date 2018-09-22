package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;
import org.usfirst.frc.team4931.robot.enums.GrabberState;

public class ChangeGrabberState extends Command {

  private GrabberState grabberState;

  public ChangeGrabberState(GrabberState grabberState) {
    this.grabberState = grabberState;
  }

  @Override
  protected void initialize() {
    Robot.getGrabber().changeGrabberState(grabberState);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}
