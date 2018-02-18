package org.usfirst.frc.team4931.robot.field;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4931.robot.commands.TurnToAngle;

public class AutoCommand extends CommandGroup {
  AutoCommand(Waypoint[] points) {
    for (Waypoint point : points) {
      addSequential(new TurnToAngle(1, point.angle));
    }
  }
}
