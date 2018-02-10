package org.usfirst.frc.team4931.robot.commands;

import org.usfirst.frc.team4931.robot.Robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ChangeGrabberPosition extends Command {
  private final double GRABBER_PERCENT = .5;

  public ChangeGrabberPosition() {
    requires(Robot.grabber);
  }
  
  @Override
  protected void execute() {
    
    Joystick controller = Robot.operatorInput.getLiftController();
    int pov = controller.getPOV();
    if (pov == 1) {
      Robot.grabber.changePosition(GRABBER_PERCENT);
    } else if (pov == 5) {
      Robot.grabber.changePosition(-GRABBER_PERCENT);
    }
    
  }
  
  @Override
  protected boolean isFinished() {
    return false;
  }

}
