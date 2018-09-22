package org.usfirst.frc.team4931.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;

public class Wait extends Command {

  private long startTime;
  private long targetTime;

  public Wait(long targetTime) {
    this.targetTime = targetTime;
  }

  @Override
  protected void initialize() {
    startTime = System.currentTimeMillis();
  }

  @Override
  protected boolean isFinished() {
    return System.currentTimeMillis() - startTime >= targetTime;
  }
}
