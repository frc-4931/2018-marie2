package org.usfirst.frc.team4931.robot.commands;

import org.usfirst.frc.team4931.robot.Robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

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
   * Feeds the input of the Joystick into the driveArchade() method in
   * the Drivetrain class.
   */
  @Override
  protected void execute() {
    Joystick controller = Robot.operatorInput.getDriverController();
    Robot.drivetrain.driveArcade(controller.getY(), controller.getZ(), controller.getThrottle());
  }
  
  /**
   * Keeps the command from stopping on its own.
   */
  @Override
  protected boolean isFinished() {
    return false;
  }
  
  /**
   * Ends the manual motion of the drivetrain. Will be used when the input
   * from the joystick ends.
   */
  @Override
  protected void end() {
    Robot.drivetrain.driveArcade(0, 0, 0);
  }
  
}
