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

    //FIXME uncomment correct waypoints
    Map<Strategy, Waypoint[]> points = new EnumMap<>(Strategy.class);
    // starting at: 0.0, 24.0, 0.0
    points.put(SWITCH_SAME,
        new Waypoint[]{new Waypoint(11.0, 1.0, -35.0), new Waypoint(3.0, -5.0, -90.0)});
    // starting at: 0.0, 24.0, 0.0
    points.put(SCALE_SAME,
        new Waypoint[]{new Waypoint(20.0, 1.0, 0.0), new Waypoint(7.0, -4.0, -90.0)});
    // starting at: 0.0, 24.0, 0.0
    points.put(SWITCH_OPPOSITE,
        new Waypoint[]{new Waypoint(15.0, 0.0, 0.0), new Waypoint(5.0, -5.0, -90.0),
            new Waypoint(0.0, -7.0, -90.0), new Waypoint(-4.0, -3.0, 180.0)});
    // starting at: 0.0, 24.0, 0.0
    points.put(SCALE_OPPOSITE,
        new Waypoint[]{new Waypoint(17.0, -1.0, 0.0), new Waypoint(3.0, -3.0, -90.0),
            new Waypoint(0.0, -15.0, -90.0), new Waypoint(4.0, -3.0, 0.0),
            new Waypoint(3.0, 4.0, 90.0)});
    // points.put(DRIVE_FORWARD, new Waypoint[] {new Waypoint(0, 24, 0), new
    // Waypoint(20, 24, 0)});
    // starting at: 1.0, 12.0, 45.0
    points.put(DRIVE_FORWARD,
        new Waypoint[]{new Waypoint(3.0, -5.0, -15.0), new Waypoint(6.0, 1.0, 90.0),
            new Waypoint(7.0, -6.0, 45.0)});
    WAYPOINTS.put(StartingPos.LEFT, points);

    points = new EnumMap<>(Strategy.class);
    // starting at: 0.0, 14.0, 0.0
    points.put(SWITCH_SAME,
        new Waypoint[]{new Waypoint(7.0, 4.0, 35.0), new Waypoint(5.0, 0.0, 0.0)});
    // starting at: 0.0, 14.0, 0.0
    points.put(SCALE_SAME, new Waypoint[]{new Waypoint(7.0, 6.0, 90.0), new Waypoint(4.0, 3.0, 0.0),
        new Waypoint(10.0, 1.0, 0.0), new Waypoint(6.0, -3.0, -90.0)});
    // starting at: 0.0, 14.0, 0.0
    points.put(SWITCH_OPPOSITE,
        new Waypoint[]{new Waypoint(7.0, -5.0, -35.0), new Waypoint(5.0, -1.0, 0.0)});
    // starting at: 0.0, 14.0, 0.0
    points.put(SCALE_OPPOSITE,
        new Waypoint[]{new Waypoint(6.0, -5.0, -90.0), new Waypoint(4.0, -5.0, 0.0),
            new Waypoint(11.0, -2.0, 0.0), new Waypoint(6.0, 4.0, 90.0)});
    // starting at: 0.0, 14.0, 0.0
    points.put(DRIVE_FORWARD,
        new Waypoint[]{new Waypoint(7.0, 1.0, 35.0), new Waypoint(3.0, 3.0, 90.0),
            new Waypoint(0.0, 2.0, 90.0)});
    WAYPOINTS.put(StartingPos.MIDDLE, points);

    points = new EnumMap<>(Strategy.class);

    // starting at: 0.0, 3.0, 0.0
    points.put(SWITCH_SAME,
        new Waypoint[]{new Waypoint(4.0, 0.0, 0.0), new Waypoint(10.0, 4.0, 90.0)});

    // starting at: 0.0, 3.0, 0.0
    points.put(SCALE_SAME,
        new Waypoint[]{new Waypoint(22.0, 0.0, 0.0), new Waypoint(5.0, 3.0, 90.0)});
    
    // starting at: 0.0, 3.0, 0.0
    points.put(SWITCH_OPPOSITE,
        new Waypoint[]{new Waypoint(17.0, 1.0, 35.0), new Waypoint(2.0, 3.0, 90.0),
            new Waypoint(0.0, 53.0, 90.0), new Waypoint(-3.0, -42.0, 180.0)});

    // starting at: 0.0, 3.0, 0.0
    points.put(SCALE_OPPOSITE,
        new Waypoint[]{new Waypoint(16.0, 1.0, 0.0), new Waypoint(4.0, 4.0, 90.0),
            new Waypoint(0.0, 13.0, 90.0), new Waypoint(4.0, 3.0, 0.0),
            new Waypoint(3.0, -3.0, -90.0)});

    // starting at: 0.0, 3.0, 0.0
    points.put(DRIVE_FORWARD,
        new Waypoint[]{new Waypoint(20.0, 0.0, 0.0)}); //TODO uncomment real waypoint list
    WAYPOINTS.put(StartingPos.RIGHT, points);
  }
}


