package org.usfirst.frc.team4931.robot.commands;

import org.usfirst.frc.team4931.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class TurnToAngle extends Command {
  private double speed;
  private double angle;
  
  public TurnToAngle(double speed, double angle) {
    requires(Robot.drivetrain);
    
    this.speed = speed;
    this.angle = angle;
  }
  
  @Override
  protected void initialize() {
    Robot.drivetrain.gyroReset();
    Robot.drivetrain.driveTank(speed, -speed);
  }
  
  @Override
  protected boolean isFinished() {
    // TODO Auto-generated method stub
    if (Math.abs(Robot.drivetrain.gyroReadYawAngle()) >= Math.abs(angle)) {
      return true;
    } else {
      return false;
    }
  }
  
  @Override
  protected void end() {
    Robot.drivetrain.driveTank(0, -0);
  }

}
