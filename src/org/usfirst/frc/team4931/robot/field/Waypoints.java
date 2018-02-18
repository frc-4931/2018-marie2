package org.usfirst.frc.team4931.robot.field;

import static org.usfirst.frc.team4931.robot.field.Strategy.DRIVE_FORWARD;
import static org.usfirst.frc.team4931.robot.field.Strategy.SCALE_OPPOSITE;
import static org.usfirst.frc.team4931.robot.field.Strategy.SCALE_SAME;
import static org.usfirst.frc.team4931.robot.field.Strategy.SWITCH_OPPOSITE;
import static org.usfirst.frc.team4931.robot.field.Strategy.SWITCH_SAME;

import java.util.EnumMap;
import java.util.Map;

/**
 * This function tells us each waypoint based on positions and strategies.
 *
 */
public class Waypoints {
  
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
    points.put(SWITCH_SAME, new Waypoint[] {new Waypoint(0, 24, 0), new Waypoint(11, 25, -35),
        new Waypoint(14, 20, -90)});
    points.put(SCALE_SAME, new Waypoint[] {new Waypoint(0, 24, 0), new Waypoint(20, 25, 0),
        new Waypoint(27, 21, -90)});
    points.put(SWITCH_OPPOSITE, new Waypoint[] {new Waypoint(0, 24, 0), new Waypoint(15, 24, 0),
        new Waypoint(20, 19, -90), new Waypoint(20, 12, -90), new Waypoint(16, 9, 180)});
    points.put(SCALE_OPPOSITE,
        new Waypoint[] {new Waypoint(0, 24, 0), new Waypoint(17, 23, 0), new Waypoint(20, 20, -90),
            new Waypoint(20, 5, -90), new Waypoint(24, 2, 0), new Waypoint(27, 6, 90)});
    //points.put(DRIVE_FORWARD, new Waypoint[] {new Waypoint(0, 24, 0), new Waypoint(20, 24, 0)});
    points.put(DRIVE_FORWARD, new Waypoint[] {new Waypoint(1, 12, 45), new Waypoint(4, 7, -15), new Waypoint(10, 8, 90), new Waypoint(17, 2, 45)});
    WAYPOINTS.put(StartingPos.LEFT, points);

    points = new EnumMap<>(Strategy.class);
    points.put(SWITCH_SAME,
        new Waypoint[] {new Waypoint(0, 14, 0), new Waypoint(7, 18, 35), new Waypoint(12, 18, 0)});
    points.put(SCALE_SAME, new Waypoint[] {new Waypoint(0, 14, 0), new Waypoint(7, 20, 90),
        new Waypoint(11, 23, 0), new Waypoint(21, 24, 0), new Waypoint(27, 21, -90)});
    points.put(SWITCH_OPPOSITE,
        new Waypoint[] {new Waypoint(0, 14, 0), new Waypoint(7, 9, -35), new Waypoint(12, 8, 0)});
    points.put(SCALE_OPPOSITE, new Waypoint[] {new Waypoint(0, 14, 0), new Waypoint(6, 9, -90),
        new Waypoint(10, 4, 0), new Waypoint(21, 2, 0), new Waypoint(27, 6, 90)});
    points.put(DRIVE_FORWARD, new Waypoint[] {new Waypoint(0, 14, 0), new Waypoint(7, 15, 35),
        new Waypoint(10, 18, 90), new Waypoint(10, 20, 90)});
    WAYPOINTS.put(StartingPos.MIDDLE, points);

    points = new EnumMap<>(Strategy.class);
    points.put(SWITCH_SAME,
        new Waypoint[] {new Waypoint(0, 3, 0), new Waypoint(4, 3, 0), new Waypoint(14, 7, 90)});
    points.put(SCALE_SAME,
        new Waypoint[] {new Waypoint(0, 3, 0), new Waypoint(22, 3, 0), new Waypoint(27, 6, 90)});
    points.put(SWITCH_OPPOSITE, new Waypoint[] {new Waypoint(0, 3, 0), new Waypoint(17, 4, 35),
        new Waypoint(19, 7, 90), new Waypoint(19, 60, 90), new Waypoint(16, 18, 180)});
    points.put(SCALE_OPPOSITE,
        new Waypoint[] {new Waypoint(0, 3, 0), new Waypoint(16, 4, 0), new Waypoint(20, 8, 90),
            new Waypoint(20, 21, 90), new Waypoint(24, 24, 0), new Waypoint(27, 21, -90)});
    points.put(DRIVE_FORWARD, new Waypoint[] {new Waypoint(0, 3, 0), new Waypoint(20, 3, 0)});
    WAYPOINTS.put(StartingPos.RIGHT, points);
  }
}


