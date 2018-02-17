package org.usfirst.frc.team4931.robot.field;

import static org.usfirst.frc.team4931.robot.field.Strategy.DRIVE_FORWARD;
import static org.usfirst.frc.team4931.robot.field.Strategy.SCALE_OPPOSITE;
import static org.usfirst.frc.team4931.robot.field.Strategy.SCALE_SAME;
import static org.usfirst.frc.team4931.robot.field.Strategy.SWITCH_OPPOSITE;
import static org.usfirst.frc.team4931.robot.field.Strategy.SWITCH_SAME;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import jaci.pathfinder.Waypoint;

/**
 * This function tells us each waypoint based on positions and strategies.
 *
 */
public class Waypoints {
  private static final double F_TO_M = 0.3048;
  private static final double D_TO_R = Math.PI / 180;
  
  /**
   * Creates a map for our waypoints.
   */
  public static Map<StartingPos, Map<Strategy, Waypoint[]>> WAYPOINTS =
      new EnumMap<>(StartingPos.class);


  /**
   * The waypoints for autonomous.
   */
  static {
    Map<Strategy, Waypoint[]> points = new EnumMap<>(Strategy.class);
    points.put(SWITCH_SAME, new Waypoint[] {new Waypoint(0 * F_TO_M, 24 * F_TO_M, 0 * D_TO_R), new Waypoint(11, 25, -35),
        new Waypoint(14, 20, -90)});
    points.put(SCALE_SAME, new Waypoint[] {new Waypoint(0 * F_TO_M, 24 * F_TO_M, 0 * D_TO_R), new Waypoint(20 * F_TO_M, 25 * F_TO_M, 0 * D_TO_R),
        new Waypoint(27, 21, -90)});
    points.put(SWITCH_OPPOSITE, new Waypoint[] {new Waypoint(0 * F_TO_M, 24 * F_TO_M, 0 * D_TO_R), new Waypoint(15 * F_TO_M, 24 * F_TO_M, 0 * D_TO_R),
        new Waypoint(20, 19, -90), new Waypoint(20, 12, -90), new Waypoint(16 * F_TO_M, 9 * F_TO_M, 180 * D_TO_R)});
    points.put(SCALE_OPPOSITE,
        new Waypoint[] {new Waypoint(0 * F_TO_M, 24 * F_TO_M, 0 * D_TO_R), new Waypoint(17 * F_TO_M, 23 * F_TO_M, 0 * D_TO_R), new Waypoint(20, 20, -90),
            new Waypoint(20, 5, -90), new Waypoint(24 * F_TO_M, 2 * F_TO_M, 0 * D_TO_R), new Waypoint(27 * F_TO_M, 6 * F_TO_M, 90 * D_TO_R)});
    //points.put(DRIVE_FORWARD, new Waypoint[] {new Waypoint(0 * F_TO_M, 24 * F_TO_M, 0 * D_TO_R), new Waypoint(20 * F_TO_M, 24 * F_TO_M, 0 * D_TO_R)});
    points.put(DRIVE_FORWARD, new Waypoint[] {new Waypoint(1 * F_TO_M, 12 * F_TO_M, 45 * D_TO_R), new Waypoint(4, 7, -15), new Waypoint(10 * F_TO_M, 8 * F_TO_M, 90 * D_TO_R), new Waypoint(17 * F_TO_M, 2 * F_TO_M, 45 * D_TO_R)});
    WAYPOINTS.put(StartingPos.LEFT, points);

    points = new EnumMap<>(Strategy.class);
    points.put(SWITCH_SAME,
        new Waypoint[] {new Waypoint(0 * F_TO_M, 14 * F_TO_M, 0 * D_TO_R), new Waypoint(7 * F_TO_M, 18 * F_TO_M, 35 * D_TO_R), new Waypoint(12 * F_TO_M, 18 * F_TO_M, 0 * D_TO_R)});
    points.put(SCALE_SAME, new Waypoint[] {new Waypoint(0 * F_TO_M, 14 * F_TO_M, 0 * D_TO_R), new Waypoint(7 * F_TO_M, 20 * F_TO_M, 90 * D_TO_R),
        new Waypoint(11 * F_TO_M, 23 * F_TO_M, 0 * D_TO_R), new Waypoint(21 * F_TO_M, 24 * F_TO_M, 0 * D_TO_R), new Waypoint(27, 21, -90)});
    points.put(SWITCH_OPPOSITE,
        new Waypoint[] {new Waypoint(0 * F_TO_M, 14 * F_TO_M, 0 * D_TO_R), new Waypoint(7, 9, -35), new Waypoint(12 * F_TO_M, 8 * F_TO_M, 0 * D_TO_R)});
    points.put(SCALE_OPPOSITE, new Waypoint[] {new Waypoint(0 * F_TO_M, 14 * F_TO_M, 0 * D_TO_R), new Waypoint(6, 9, -90),
        new Waypoint(10 * F_TO_M, 4 * F_TO_M, 0 * D_TO_R), new Waypoint(21 * F_TO_M, 2 * F_TO_M, 0 * D_TO_R), new Waypoint(27 * F_TO_M, 6 * F_TO_M, 90 * D_TO_R)});
    points.put(DRIVE_FORWARD, new Waypoint[] {new Waypoint(0 * F_TO_M, 14 * F_TO_M, 0 * D_TO_R), new Waypoint(7 * F_TO_M, 15 * F_TO_M, 35 * D_TO_R),
        new Waypoint(10 * F_TO_M, 18 * F_TO_M, 90 * D_TO_R), new Waypoint(10 * F_TO_M, 20 * F_TO_M, 90 * D_TO_R)});
    WAYPOINTS.put(StartingPos.MIDDLE, points);

    points = new EnumMap<>(Strategy.class);
    points.put(SWITCH_SAME,
        new Waypoint[] {new Waypoint(0 * F_TO_M, 3 * F_TO_M, 0 * D_TO_R), new Waypoint(4 * F_TO_M, 3 * F_TO_M, 0 * D_TO_R), new Waypoint(14 * F_TO_M, 7 * F_TO_M, 90 * D_TO_R)});
    points.put(SCALE_SAME,
        new Waypoint[] {new Waypoint(0 * F_TO_M, 3 * F_TO_M, 0 * D_TO_R), new Waypoint(22 * F_TO_M, 3 * F_TO_M, 0 * D_TO_R), new Waypoint(27 * F_TO_M, 6 * F_TO_M, 90 * D_TO_R)});
    points.put(SWITCH_OPPOSITE, new Waypoint[] {new Waypoint(0 * F_TO_M, 3 * F_TO_M, 0 * D_TO_R), new Waypoint(17 * F_TO_M, 4 * F_TO_M, 35 * D_TO_R),
        new Waypoint(19 * F_TO_M, 7 * F_TO_M, 90 * D_TO_R), new Waypoint(19 * F_TO_M, 60 * F_TO_M, 90 * D_TO_R), new Waypoint(16 * F_TO_M, 18 * F_TO_M, 180 * D_TO_R)});
    points.put(SCALE_OPPOSITE,
        new Waypoint[] {new Waypoint(0 * F_TO_M, 3 * F_TO_M, 0 * D_TO_R), new Waypoint(16 * F_TO_M, 4 * F_TO_M, 0 * D_TO_R), new Waypoint(20 * F_TO_M, 8 * F_TO_M, 90 * D_TO_R),
            new Waypoint(20 * F_TO_M, 21 * F_TO_M, 90 * D_TO_R), new Waypoint(24 * F_TO_M, 24 * F_TO_M, 0 * D_TO_R), new Waypoint(27, 21, -90)});
    points.put(DRIVE_FORWARD, new Waypoint[] {new Waypoint(0 * F_TO_M, 3 * F_TO_M, 0 * D_TO_R), new Waypoint(20 * F_TO_M, 3 * F_TO_M, 0 * D_TO_R)});
    WAYPOINTS.put(StartingPos.RIGHT, points);
  }
}


