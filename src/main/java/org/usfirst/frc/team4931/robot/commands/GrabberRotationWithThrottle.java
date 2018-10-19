package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;
import org.usfirst.frc.team4931.robot.RobotMap;
import org.usfirst.frc.team4931.robot.subsystems.Grabber;

public class GrabberRotationWithThrottle extends Command {

  private Joystick joystick;
  private Grabber grabber;

  private int lastThrottlePosition;

  public GrabberRotationWithThrottle() {
    requires(Robot.getGrabber());
  }

  @Override
  public void initialize() {
    joystick = Robot.getOperatorInput().getJoystick();
    grabber = Robot.getGrabber();
    lastThrottlePosition = getGrabberPosition();
  }

  @Override
  protected void execute() {
    if (lastThrottlePosition != getGrabberPosition())
      grabber.changeGrabberPosition(lastThrottlePosition = getGrabberPosition());
  }

  private int getGrabberPosition() {
    return (int)
        (6000 * ((joystick.getThrottle() + 1) / 2));
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
