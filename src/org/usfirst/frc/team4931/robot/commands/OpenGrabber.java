package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;
/**
 * Opens the grabber
 * @author shawn
 *
 */
public class OpenGrabber extends Command {

  public OpenGrabber() {
    requires(Robot.grabber);
    setInterruptible(false);
  }

  @Override
  public void start() {
    Robot.grabber.captureCube();
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}
