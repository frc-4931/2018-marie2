package org.usfirst.frc.team4931.robot.commands;

import org.usfirst.frc.team4931.robot.field.Strategy;
import static org.usfirst.frc.team4931.robot.subsystems.FixedLiftHeight.*;
import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.modifiers.TankModifier;
/**
 * Sets the command Autonomous
 */
public class Autonomous extends CommandGroup {

  public Autonomous() {
//    addParallel(new ChangeGrabberPosCommand(/*pos*/));
    //FIXME; We need to have the strategy in here from all of our data.
//    addParallel(new SetLiftSetpoint());
    //FIXME; We need to pass the trajectories.
//    addParallel(new DriveToDistance());
  }

  public void setPickedStrategy(Strategy strategy) {
    switch (strategy) {
      case SWITCH_SAME:
      case SWITCH_OPPOSITE:
        addParallel(new SetLiftSetpoint(SWITCH));
        break;
      case SCALE_OPPOSITE:
      case SCALE_SAME:
        addParallel(new SetLiftSetpoint(SCALE_TOP));
        break;
      case DRIVE_FORWARD:
        addParallel(new SetLiftSetpoint(SWITCH));
    }
  }
  public void setPickedTrajectory(TankModifier tankModifier) {
    
  }
}
