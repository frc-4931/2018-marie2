package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;

public class ShiftToLowGear extends Command {

  @Override
  protected void initialize() {
    Robot.drivetrain.switchLowGear();
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}
