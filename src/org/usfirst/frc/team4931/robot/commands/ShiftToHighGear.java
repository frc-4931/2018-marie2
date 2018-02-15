package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;

public class ShiftToHighGear extends Command {

  @Override
  protected void initialize() {
    Robot.drivetrain.switchHighGear();
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}
