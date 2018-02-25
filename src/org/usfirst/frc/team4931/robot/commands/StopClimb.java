package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;

public class StopClimb extends Command{

  public StopClimb() {
   requires(Robot.climber);
  }
  
  @Override
  protected void initialize() {
    Robot.climber.stop();
    Robot.climber.lock();
  }
  
  @Override
  protected boolean isFinished() {
    return true;
  }

}
