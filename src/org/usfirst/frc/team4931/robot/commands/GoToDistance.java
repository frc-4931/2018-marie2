package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class GoToDistance extends Command {

  //FIXME
  public GoToDistance(double speed, double distance) {
    System.out.println("Distance: " + distance);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
