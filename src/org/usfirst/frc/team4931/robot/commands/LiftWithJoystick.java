package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;

/**
 * Allows the manual control of the lifting mechanism with the joystick.
 * @author dj wickman
 */
public class LiftWithJoystick extends Command {
  
  public LiftWithJoystick() {
    requires(Robot.lift);
  }
  
  /**
   * Calculates the speed of the manual lift based on the scale of the
   * joystick and the throttle of the joystick and returns that calculated
   * value.
   */
  private double calculateSpeed() {
    Joystick controller = Robot.operatorInput.getLiftController();
    double speed = controller.getY();
    double throttle = (controller.getThrottle() + 1)/2;
    return speed * throttle;
  }
  
  /**
   * lifts the lifting mechanism at the calculated speed.
   */
  @Override
  protected void execute() {
    double speed = calculateSpeed();
    if (Math.abs(calculateSpeed()) > 0.1) {
      //Robot.lift.setSpeed(calculateSpeed());
      Robot.lift.setLiftHeight(Robot.lift.getSetPoint() + speed * 100);
    }
  }
  /**
   * keeps the command from stopping on its own
   */
  @Override
  protected boolean isFinished() {
    return false;
  }
  
  /**
   * ends the manual motion of the lifting mechanism. Will be used when
   * input from the joystick ends.
   */
  @Override
  protected void end() {

  }
  
}
