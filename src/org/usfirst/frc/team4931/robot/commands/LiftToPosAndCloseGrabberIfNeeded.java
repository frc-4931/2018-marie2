package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4931.robot.Robot;
import org.usfirst.frc.team4931.robot.subsystems.FixedLiftHeight;
import org.usfirst.frc.team4931.robot.subsystems.GrabberPosition;

public class LiftToPosAndCloseGrabberIfNeeded extends CommandGroup {

  public LiftToPosAndCloseGrabberIfNeeded(FixedLiftHeight liftTarget) {
    GrabberPosition curGrabberPos = Robot.grabber.getCurrentGrabberPosition();
    FixedLiftHeight curLiftPos = Robot.lift.getLiftHeight();

    if (curGrabberPos == GrabberPosition.HIGH) {
      addParallel(new GrabberGoToPosition(GrabberPosition.SHOOT));
    }

    if ((curLiftPos == FixedLiftHeight.SCALE_TOP || curLiftPos == FixedLiftHeight.SCALE_MID)
        && liftTarget.ordinal() < FixedLiftHeight.SCALE_MID.ordinal()) {
      addParallel(new CloseGrabber());
    } else if ((liftTarget == FixedLiftHeight.SCALE_MID || liftTarget == FixedLiftHeight.SCALE_TOP)
        && curLiftPos.ordinal() < FixedLiftHeight.SCALE_MID.ordinal()) {
      addParallel(new CloseGrabber());
    }

    addParallel(new SetLiftSetpoint(liftTarget));
  }
}
