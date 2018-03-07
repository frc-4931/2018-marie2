package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;
import org.usfirst.frc.team4931.robot.subsystems.FixedLiftHeight;
import org.usfirst.frc.team4931.robot.subsystems.GrabberPosition;

public class StartClimb extends Command {
  
  public StartClimb() {
    requires(Robot.climber);
    requires(Robot.lift);
  }
  
  @Override
  protected void initialize() {
    Robot.climber.release();
    Robot.climber.climb();
    Robot.lift.setLiftHeight(FixedLiftHeight.EXCHANGE);
    Robot.grabber.goToSetPoint(GrabberPosition.SHOOT);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}
