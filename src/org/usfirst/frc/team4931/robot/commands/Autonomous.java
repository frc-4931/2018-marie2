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

  /**
   * Runs the autonomous
   */
  public Autonomous(Strategy strategy, TankModifier tankModifier) {
    addParallel(new GrabberGoToPosition(GrabberPosition.EXCHANGE)); // sets claw position to middle position
    addParallel(new SetLiftSetpoint(calcLiftHeight(strategy))); // raises lift based on the calculated strategy
    addParallel(new DriveByTrajectory(tankModifier)); // drives the calculated trajectory
    
    if (strategy != DRIVE_FORWARD) {
      addSequential(new OpenGrabber()); // if not driving forward, open the claw at the end of autonomous
    }
  }

  /**
   * Determines the height where the lift needs to raise based on the
   * determined strategy.
   */
  private FixedLiftHeight calcLiftHeight(Strategy strategy) {
    switch (strategy) {
      case SWITCH_SAME:
      case SWITCH_OPPOSITE:
        return SWITCH; // if going to the switch, then raise the lift to switch height
      case SCALE_OPPOSITE:
      case SCALE_SAME:
        return SCALE_TOP; // if going to the scale, then raise the lift to the highest scale height
      default:
        return SWITCH; // by default, if nothing can be determined, then raise the lift to switch height
    }
  }
}
