package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class WaitForMS extends Command {
  private long startTime;
  private double time;

  public WaitForMS(double time) {
    this.time = time;
  }

  @Override
  protected void initialize() {
    startTime = System.currentTimeMillis();
  }

  @Override
  protected boolean isFinished() {
    return (System.currentTimeMillis() - startTime) >= time;
  }
}
