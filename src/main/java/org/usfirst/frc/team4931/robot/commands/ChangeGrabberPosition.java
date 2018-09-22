package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;
import org.usfirst.frc.team4931.robot.enums.GrabberPosition;

public class ChangeGrabberPosition extends Command {

  private int grabberPosition;

  public ChangeGrabberPosition(GrabberPosition grabberPosition) {
    requires(Robot.getGrabber());
    this.grabberPosition = grabberPosition.getPosition();
  }

  public ChangeGrabberPosition(int grabberPosition) {
    requires(Robot.getGrabber());
    this.grabberPosition = grabberPosition;
  }

  @Override
  protected void initialize() {
    Robot.getGrabber().changeGrabberPosition(grabberPosition);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}
