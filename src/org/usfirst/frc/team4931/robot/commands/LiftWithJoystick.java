package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;

/**
 * Allows the manual control of the lifting mechanism with the joystick.
 * @author dj wickman
 */
public class LiftWithJoystick extends Command {

  private boolean lockToPos = false;

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
    double controllerY = controller.getY();
    double speed = controllerY * Math.abs(controllerY);
    double throttle = 1 - (controller.getThrottle() + 1) / 2;
    return -speed * throttle;
  }
  
  /**
   * lifts the lifting mechanism at the calculated speed.
   */
  @Override
  protected void execute() {
    double speed = calculateSpeed();
    if (Math.abs(speed) > 0.05) {

      if(Robot.lift.getTop() && speed > 0) {
        speed = 0;
      } else if (Robot.lift.getBottom() && speed < 0) {
        speed = 0;
      }

      Robot.lift.setSpeed(speed);
//      Robot.lift.setLiftHeight(Robot.lift.getSetPoint() + speed * 100);
      lockToPos = true;
    } else if (lockToPos) {
      Robot.lift.setLiftHeight(Robot.lift.getPosition());
      lockToPos = false;
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
