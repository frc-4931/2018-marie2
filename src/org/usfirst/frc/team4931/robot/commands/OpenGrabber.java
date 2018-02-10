package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;
/**
 * Opens the grabber
 */
public class OpenGrabber extends Command {

  public OpenGrabber() {
    requires(Robot.grabber);
    setInterruptible(false);
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
