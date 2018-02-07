package org.usfirst.frc.team4931.robot.commands;

import org.usfirst.frc.team4931.robot.Robot;
import org.usfirst.frc.team4931.robot.subsystems.GrabberPosition;
import edu.wpi.first.wpilibj.command.Command;

/**
 * @author dj wickman
 *
 * Rotates the grabber to the set position
 */
public class GrabberChangePosition extends Command {
  private GrabberPosition position;
  
  public GrabberChangePosition(GrabberPosition position) {
    this.position = position;
    requires(Robot.grabber);
    setInterruptible(true);
  }
  
  @Override
  public void initialize() {
    Robot.grabber.goToSetPoint(position);
  }
  
  @Override
  public boolean isFinished() {
    return Robot.grabber.atTargetPosition();
  }

}
