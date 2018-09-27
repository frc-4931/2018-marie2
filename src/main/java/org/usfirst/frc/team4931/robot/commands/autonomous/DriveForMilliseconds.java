package org.usfirst.frc.team4931.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;
import org.usfirst.frc.team4931.robot.subsystems.Drivetrain;

public class DriveForMilliseconds extends Command {

  private long startTime;
  private long targetTime;

  private Drivetrain drivetrain;

  public DriveForMilliseconds(long targetTime) {
    requires(Robot.getDrivetrain());
    this.targetTime = targetTime;
    drivetrain = Robot.getDrivetrain();
  }

  @Override
  protected void initialize() {
    startTime = System.currentTimeMillis();
  }

  @Override
  protected void execute() {
    drivetrain.tankDrive(1, 1);
  }

  @Override
  protected boolean isFinished() {
    return System.currentTimeMillis() - startTime >= targetTime;
  }

  @Override
  protected void end() {
    drivetrain.tankDrive(0, 0);
  }
}
