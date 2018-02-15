package org.usfirst.frc.team4931.robot.commands;

import org.usfirst.frc.team4931.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class StopClimb extends Command{

  public StopClimb() {
   requires(Robot.climber);
  }
  
  @Override
  protected void initialize() {
    Robot.climber.stop();
  }
  
  @Override
  protected boolean isFinished() {
    return true;
  }

}
