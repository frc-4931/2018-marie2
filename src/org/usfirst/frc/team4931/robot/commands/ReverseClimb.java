package org.usfirst.frc.team4931.robot.commands;

import org.usfirst.frc.team4931.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class ReverseClimb extends Command {

  public ReverseClimb() {
    requires(Robot.climber);
  }
  
  @Override
  public void initialize() {
    Robot.climber.reverse();
  }
  
  @Override
  protected boolean isFinished() {
    return true;
  }

}
