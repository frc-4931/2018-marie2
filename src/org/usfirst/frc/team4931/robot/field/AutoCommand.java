package org.usfirst.frc.team4931.robot.field;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4931.robot.commands.GoToDistance;
import org.usfirst.frc.team4931.robot.commands.TurnToAngle;

public class AutoCommand extends CommandGroup {
  double curAbsoluteAngle = 0;
  double curAbsoluteX = 1;
  double curAbsoluteY = 0;

  AutoCommand(Waypoint[] points) {
    addSequential(new GoToDistance(1, 1));

    Waypoint lastPoint = points[points.length-1];
    for (Waypoint point : points) {
      double x = point.x - curAbsoluteX;
      double y = point.y - curAbsoluteY;
      double distance = Math.sqrt(x*x + y*y);

      if (x == 0 && y > 0) {;
        turn(90);
      } else if (x == 0 && y < 0) {
        turn(-90);
      } else if (x > 0 && y == 0) {
        turn(0);
      } else if (x < 0 && y == 0) {
        turn(180);
      } else if (x > 0 && y > 0) {
        turn(Math.toDegrees(Math.atan(y/x)));
      } else if (x > 0 && y < 0) {
        turn(-Math.toDegrees(Math.atan(-y/x)));
      } else if (x < 0 && y > 0) {
        turn(180 - Math.toDegrees(Math.atan(y/-x)));
      } else if (x < 0 && y < 0) {
        turn(180 + Math.toDegrees(Math.atan(-y/-x)));
      }

      curAbsoluteX += x;
      curAbsoluteY += y;
      addSequential(new GoToDistance(1, distance));
    }
    turn(lastPoint.angle);
  }

  private void addAngle(double angle) {
     curAbsoluteAngle += angle;
  }

  private double getTurnAngle(double angle) {
    angle = angle % 360;
    return (angle > 180) ? angle - 360 : angle;
  }

  private double turnFromAbsolute(double angle) {
    return getTurnAngle(angle - curAbsoluteAngle);
  }

  private void turn(double angle) {
    addAngle(angle);
    angle = turnFromAbsolute(angle);
    addSequential(new TurnToAngle(Math.copySign(1, angle), Math.abs(angle)));
  }

}
