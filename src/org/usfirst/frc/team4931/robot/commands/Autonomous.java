package org.usfirst.frc.team4931.robot.commands;

import static org.usfirst.frc.team4931.robot.field.Strategy.*;
import org.usfirst.frc.team4931.robot.field.Strategy;
import org.usfirst.frc.team4931.robot.subsystems.FixedLiftHeight;
import org.usfirst.frc.team4931.robot.subsystems.GrabberPosition;
import static org.usfirst.frc.team4931.robot.subsystems.FixedLiftHeight.*;
import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.modifiers.TankModifier;
/**
 * Sets the command Autonomous
 */
public class Autonomous extends CommandGroup {

  public Autonomous(Strategy strategy, TankModifier tankModifier) {
    addParallel(new GrabberChangePosition(GrabberPosition.MIDDLE));
    addParallel(new SetLiftSetpoint(calcLiftHeight(strategy)));
    addParallel(new DriveByTrajectory(tankModifier));
    
    if (strategy != DRIVE_FORWARD) {
      addSequential(new OpenGrabber());
    }
  }

  private FixedLiftHeight calcLiftHeight(Strategy strategy) {
    switch (strategy) {
      case SWITCH_SAME:
      case SWITCH_OPPOSITE:
        return SWITCH;
      case SCALE_OPPOSITE:
      case SCALE_SAME:
        return SCALE_TOP;
      default:
        return SWITCH;
    }
  }
}
