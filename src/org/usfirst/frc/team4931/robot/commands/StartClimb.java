package org.usfirst.frc.team4931.robot.commands;

import org.usfirst.frc.team4931.robot.Robot;
import org.usfirst.frc.team4931.robot.subsystems.FixedLiftHeight;
import edu.wpi.first.wpilibj.command.Command;

public class StartClimb extends Command {
  
  public StartClimb() {
    requires(Robot.climber);
    requires(Robot.lift);
  }
  
  @Override
  public void start() {
    Robot.climber.climb();
    Robot.lift.setLiftHeight(FixedLiftHeight.FLOOR);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}
