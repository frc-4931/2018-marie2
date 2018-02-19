package org.usfirst.frc.team4931.robot.field;

public class Waypoint extends jaci.pathfinder.Waypoint {
  private static final double FEET_TO_METERS = 0.3048;

  public Waypoint(double x, double y, double angle) {
    super(x * FEET_TO_METERS, y * FEET_TO_METERS, Math.toRadians(angle));
  }
}
