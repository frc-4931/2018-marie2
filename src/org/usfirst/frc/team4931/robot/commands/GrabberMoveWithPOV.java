package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;

public class GrabberMoveWithPOV extends Command {

  private final double GRABBER_SPEED = 100;

  public GrabberMoveWithPOV() {
    requires(Robot.grabber);
  }
  
  @Override
  protected void execute() {
    
    Joystick controller = Robot.operatorInput.getLiftController();
    int pov = controller.getPOV();
    if (pov == 0) {
      Robot.grabber.goToSetPoint(Robot.grabber.getSetPoint() - GRABBER_SPEED);
    } else if (pov == 4) {
      Robot.grabber.goToSetPoint(Robot.grabber.getSetPoint() + GRABBER_SPEED);
    }
    
  }
  
  @Override
  protected boolean isFinished() {
    return false;
  }

}
