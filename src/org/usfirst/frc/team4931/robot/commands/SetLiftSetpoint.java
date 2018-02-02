package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;
import org.usfirst.frc.team4931.robot.subsystems.FixedLiftHeight;

public class SetLiftSetpoint extends Command {

  private FixedLiftHeight liftHeight;
/**
 * Sets the lift setpoint
 * @param liftHeight The desired height to move to
 */
  public SetLiftSetpoint(FixedLiftHeight liftHeight) {
    this.liftHeight = liftHeight;
    requires(Robot.lift);
  }

  @Override
  protected void initialize() {
//    Robot.lift.enable();
    Robot.lift.setLiftHeight(liftHeight);
  }

  @Override
  protected boolean isFinished() {
    return Robot.lift.isAtTarget();
  }
}
