package org.usfirst.frc.team4931.robot.commands;

import org.usfirst.frc.team4931.robot.Robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class LiftWithJoystick extends Command {
  
  public LiftWithJoystick() {
    requires(Robot.lift);
  }
  
  private double calculateSpeed() {
    Joystick controller = Robot.operatorInput.getDriverController();
    double speed = controller.getY();
    double throttle = (controller.getThrottle() + 1)/2;
    return speed * throttle;
  }
  
  @Override
  protected void execute() {
    Robot.lift.lift(calculateSpeed());
  }
  
  @Override
  protected boolean isFinished() {
    return false;
  }
  
  @Override
  protected void end() {
    Robot.lift.lift(0);
  }
  
}
