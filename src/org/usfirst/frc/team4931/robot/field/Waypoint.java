package org.usfirst.frc.team4931.robot.field;

/**
 * Created by jcrane on 2/18/18.
 */
public class Waypoint {

  private static final double FEET_TO_METERS = 0.3048;
  private static final double DEGREES_TO_RADS = Math.PI / 180;
  public double x;
  public double y;
  public double angle;

  public Waypoint(double x, double y, double angle) {
    this.x = x;
    this.y = y;
    this.angle = angle;
  }
}
