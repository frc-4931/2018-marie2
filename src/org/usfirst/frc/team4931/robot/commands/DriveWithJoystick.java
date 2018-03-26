package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;

/**
 * Allows manual control of the drivetrain with the joystick. 
 * 
 * @author dj wickman
 */
public class DriveWithJoystick extends Command {
  
  public DriveWithJoystick() {
    requires(Robot.drivetrain);
  }
  
  /**
   * Sets the speed of the motors based on joystick input
   */
  @Override
  protected void execute() {
    Joystick controller = Robot.operatorInput.getDriverController();
    double turn = controller.getZ();
    Robot.drivetrain.driveArcade(controller.getY(), turn, controller.getThrottle());
  }
  
  /**
   * Keeps the command from stopping on its own.
   */
  @Override
  protected boolean isFinished() {
    return false;
  }
  
  /**
   * Will never be called as the command never ends peacefully
   */
  @Override
  protected void end() {
    Robot.drivetrain.driveArcade(0, 0, 0);
  }
  
}
