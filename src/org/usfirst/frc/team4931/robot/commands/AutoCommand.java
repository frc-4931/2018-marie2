package org.usfirst.frc.team4931.robot.commands;

import static org.usfirst.frc.team4931.robot.subsystems.FixedLiftHeight.FLOOR;
import static org.usfirst.frc.team4931.robot.subsystems.FixedLiftHeight.SCALE_TOP;
import static org.usfirst.frc.team4931.robot.subsystems.FixedLiftHeight.SWITCH;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4931.robot.Robot;
import org.usfirst.frc.team4931.robot.field.Strategy;
import org.usfirst.frc.team4931.robot.field.Waypoint;
import org.usfirst.frc.team4931.robot.subsystems.FixedLiftHeight;
import org.usfirst.frc.team4931.robot.subsystems.GrabberPosition;

public class AutoCommand extends CommandGroup {

  private static final double METERS_TO_FEET = 3.28084;
  private static final double MOVE_SPEED = 1;
  private static final double TURN_SPEED = 1;
  double curAbsoluteAngle = 0;
  double curAbsoluteX = 1;
  double curAbsoluteY = 0;
  boolean isFirst = false;

  public AutoCommand(Waypoint[] points, Strategy strategy) {

    Robot.drivetrain.switchLowGear();
    addSequential(new Wait(350));
    addSequential(new GoToDistance(1, 1));
    Waypoint lastPoint = points[points.length-1];
    for (Waypoint point : points) {
      double x =
          ((isFirst) ? point.x * METERS_TO_FEET - 1 : point.x * METERS_TO_FEET) - curAbsoluteX;
      double y = curAbsoluteY - point.y * METERS_TO_FEET;
      double distance = Math.sqrt(x*x + y*y);
      isFirst = false;

      if (x == 0 && y > 0) {
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
      curAbsoluteY -= y;
      addSequential(new Wait(200));
      System.out.println("Adding distance: " + distance);
      addSequential(new GoToDistance(MOVE_SPEED, distance));
      addSequential(new Wait(200));
    }
    turn(Math.toDegrees(-lastPoint.angle));

    addSequential(new SetLiftSetpoint(calcLiftHeight(strategy)));
    addSequential(new GoToDistance(1, 1));
    addSequential(new GrabberGoToPosition(GrabberPosition.SHOOT));
    addSequential(calcOpenGrabber(strategy) ? new OpenGrabber() : new CloseGrabber());
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
        return FLOOR; // by default, if nothing can be determined, then raise the lift to switch height
    }
  }

  private GrabberPosition calcGrabberPosition(Strategy strategy) {
    switch (strategy) {
      case SWITCH_SAME:
      case SWITCH_OPPOSITE:
        return GrabberPosition.EXCHANGE;
      case SCALE_SAME:
      case SCALE_OPPOSITE:
        return GrabberPosition.SHOOT;
      default:
        return GrabberPosition.HIGH;
    }
  }

  private boolean calcOpenGrabber(Strategy strategy) {
    switch (strategy) {
      case DRIVE_FORWARD:
        return false;
      default:
        return true;
    }
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
    angle = turnFromAbsolute(angle);
    addAngle(angle);
    addSequential(new TurnToAngle(Math.copySign(TURN_SPEED, angle), Math.abs(angle)));
  }

}
