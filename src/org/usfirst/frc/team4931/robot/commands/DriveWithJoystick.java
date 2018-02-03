package org.usfirst.frc.team4931.robot.commands;

import org.usfirst.frc.team4931.robot.Robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class DriveWithJoystick extends Command {
  
  public DriveWithJoystick() {
    requires(Robot.drivetrain);
  }
  
  @Override
  protected void execute() {
    Joystick controller = Robot.operatorInput.getDriverController();
    Robot.drivetrain.driveArcade(controller.getY(), controller.getZ(), controller.getThrottle());
  }
  
  @Override
  protected boolean isFinished() {
    return false;
  }
  
  @Override
  protected void end() {
    Robot.drivetrain.driveArcade(0, 0, 0);
  }
  
}
