package org.usfirst.frc.team4931.robot.commands;

import org.usfirst.frc.team4931.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class StartClimb extends Command {
  
  public StartClimb() {
    requires(Robot.climber);
  }
  
  @Override
  public void start() {
    Robot.climber.climb();
  }

  @Override
  protected boolean isFinished() {
    // TODO Auto-generated method stub
    return true;
  }

}
