package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;

public class CloseGrabber extends Command {

  public CloseGrabber() {
    requires(Robot.grabber);
    setInterruptible(false);
  }

  @Override
  public void start() {
    Robot.grabber.captureCube();
  }

  @Override
  protected boolean isFinished() {
    // TODO Auto-generated method stub
    return false;
  }

}
