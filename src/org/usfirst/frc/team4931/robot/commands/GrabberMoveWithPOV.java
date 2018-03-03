package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;

public class GrabberMoveWithPOV extends Command {

  private final double GRABBER_SPEED = 0.3;
  private boolean firstStop = false;

  public GrabberMoveWithPOV() {
    requires(Robot.grabber);
  }
  
  @Override
  protected void execute() {
    
    Joystick controller = Robot.operatorInput.getLiftController();
    int pov = controller.getPOV();
    if (pov == 0) {
      Robot.grabber.setSpeed(-GRABBER_SPEED);
      firstStop = true;
    } else if (pov == 180) {
      Robot.grabber.setSpeed(GRABBER_SPEED);
      firstStop = true;
    } else if (firstStop) {
      firstStop = false;
      Robot.grabber.setSpeed(0);
      Robot.grabber.goToSetPoint(Robot.grabber.getCurrentPosition());
    }
  }
  
  @Override
  protected boolean isFinished() {
    return false;
  }

}
