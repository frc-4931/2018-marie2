package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;

public class ReverseClimb extends Command {

  public ReverseClimb() {
    requires(Robot.climber);
  }
  
  @Override
  public void initialize() {
    Robot.climber.release();
    Robot.climber.reverse();
  }
  
  @Override
  protected boolean isFinished() {
    return true;
  }

}
