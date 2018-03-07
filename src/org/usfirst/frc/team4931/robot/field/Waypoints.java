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

    Map<Strategy, Waypoint[]> points;

    //LEFT POSITION
    //Starts at (?, ?, ?)
    {
      points = new EnumMap<>(Strategy.class);

      points.put(SWITCH_SAME,
          new Waypoint[]{new Waypoint(12, 0.0, 0.0), new Waypoint(12, 0, -90.0)});

      points.put(SCALE_SAME,
          new Waypoint[]{new Waypoint(25, 0.0, 0.0), new Waypoint(25, -1, -90.0)});

      points.put(SWITCH_OPPOSITE,
          new Waypoint[]{new Waypoint(18.0, 0, 0), new Waypoint(18.0, -15.0, -90.0),
              new Waypoint(16.0, -15.0, -180.0)});

      points.put(SCALE_OPPOSITE,
          new Waypoint[]{new Waypoint(17.0, 0.0, -90.0), new Waypoint(17.0, -21.0, 0),
              new Waypoint(21.0, -18.0, 90.0)});

      points.put(DRIVE_FORWARD,
          new Waypoint[]{new Waypoint(15, 0.0, 0.0)});

      WAYPOINTS.put(StartingPos.LEFT, points);
    }

    //MIDDLE POSITION
    //Starts at (?, ?, ?) 116" from the wall
    {
      points = new EnumMap<>(Strategy.class);

      points.put(SWITCH_SAME,
          new Waypoint[]{new Waypoint(5.75, 4, 0), new Waypoint(9.25, 4, 0.0)});

      points.put(SCALE_SAME,
          new Waypoint[]{new Waypoint(6, 10.65, 0), new Waypoint(25, 10.65, 0.0),
              new Waypoint(25, 9.65, -90.0)});

      points.put(SWITCH_OPPOSITE,
          new Waypoint[]{new Waypoint(5.75, -2, 0), new Waypoint(9.25, -2, 0.0)});

      points.put(SCALE_OPPOSITE,
          new Waypoint[]{new Waypoint(6, -8.65, 0), new Waypoint(25, -8.65, 0.0),
              new Waypoint(25, -7.65, 90.0)});

      points.put(DRIVE_FORWARD,
          new Waypoint[]{new Waypoint(10, 0, 0)});

      WAYPOINTS.put(StartingPos.MIDDLE, points);
    }

    //RIGHT POSITION
    //Starts in the right corner
    {
      points = new EnumMap<>(Strategy.class);

      points.put(SWITCH_SAME,
          new Waypoint[]{new Waypoint(12, 0.0, 0.0), new Waypoint(12, 0, 90.0)});

      points.put(SCALE_SAME,
          new Waypoint[]{new Waypoint(25, -0.5, 0.0), new Waypoint(25, -0.5, 90.0)});

      points.put(SWITCH_OPPOSITE,
          new Waypoint[]{new Waypoint(18.0, 0, 0), new Waypoint(18, 19.0, 90.0),
              new Waypoint(13.0, 19.0, -90), new Waypoint(13.0, 19.0, -90)});

      points.put(SCALE_OPPOSITE,
          new Waypoint[]{new Waypoint(18.0, 0.0, 90.0), new Waypoint(18.0, 21.0, 0),
              new Waypoint(21.0, 18.0, -90.0)});

      points.put(DRIVE_FORWARD,
          new Waypoint[]{new Waypoint(15, 0.0, 0.0)});

      WAYPOINTS.put(StartingPos.RIGHT, points);
    }
  }
}


