package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;
/**
 * Closes the grabber
 */
public class CloseGrabber extends Command {

  public CloseGrabber() {
    setInterruptible(true);
  }

  @Override
  protected void initialize() {
    Robot.grabber.captureCube();
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}