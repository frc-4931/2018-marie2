package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4931.robot.subsystems.GrabberPosition;

public class ZeroGrabber extends CommandGroup {

  public ZeroGrabber() {
    addSequential(new GrabberMoveSpeed(-0.5));
    addSequential(new Wait(1000));
    addSequential(new ResetGrabber());
    addSequential(new GrabberGoToPosition(GrabberPosition.HIGH));
  }
}
