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
  /**
   * Creates a map for our waypoints.
   */
  public static Map<StartingPos, Map<Strategy, List<Waypoint>>> WAYPOINTS =
      new EnumMap<>(StartingPos.class);


/**
 * The waypoints for autonomous.
 */
  public static double[][][] POS1 = {{ // SWITCH_SAME
      {0, 24, 0}, {11, 25, -35}, {14, 20, -90}},
      { // SCALE_SAME
          {0, 24, 0}, {20, 25, 0}, {27, 21, -90}},
      { // SWITCH_OPPOSITE
          {0, 24, 0}, {15, 24, 0}, {20, 19, -90}, {20, 12, -90}, {16, 9, 180}},
      { // SCALE_OPPOSITE
          {0, 24, 0}, {17, 23, 0}, {20, 20, -90}, {20, 5, -90}, {24, 2, 0}, {27, 6, 90}},
      { // DRIVE_FORWARD
          {0, 24, 0}, {20, 24, 0}},};

  public static double[][][] POS2 = {{ // SWITCH_SAME
      {0, 14, 0}, {7, 18, 35}, {12, 18, 0}},
      { // SCALE_SAME
          {0, 14, 0}, {7, 20, 90}, {11, 23, 0}, {21, 24, 0}, {27, 21, -90}},
      { // SWITCH_OPPOSITE
          {0, 14, 0}, {7, 9, -35}, {12, 8, 0}},
      { // SCALE_OPPOSITE
          {0, 14, 0}, {6, 9, -90}, {10, 4, 0}, {21, 2, 0}, {27, 6, 90}},
      { // DRIVE_FORWARD
          {0, 14, 0}, {7, 15, 35}, {10, 18, 90}, {10, 20, 90}},};

  public static double[][][] POS3 = {{ // SWITCH_SAME
      {0, 3, 0}, {4, 3, 0}, {14, 7, 90}},
      { // SCALE_SAME
          {0, 3, 0}, {22, 3, 0}, {27, 6, 90}},
      { // SWITCH_OPPOSITE
          {0, 3, 0}, {17, 4, 35}, {19, 7, 90}, {19, 60, 90}, {16, 18, 180}},
      { // SCALE_OPPOSITE
          {0, 3, 0}, {16, 4, 0}, {20, 8, 90}, {20, 21, 90}, {24, 24, 0}, {27, 21, -90}},
      { // DRIVE_FORWARD
          {0, 3, 0}, {20, 3, 0}},};

  static {
    Map<Strategy, List<Waypoint>> points = new EnumMap<>(Strategy.class);

    points.put(SWITCH_SAME, Arrays.stream(POS1[SWITCH_SAME.ordinal()])
        .map(Waypoints::waypointBuilder).collect(Collectors.toList()));
    points.put(SWITCH_OPPOSITE, Arrays.stream(POS1[SWITCH_OPPOSITE.ordinal()])
        .map(Waypoints::waypointBuilder).collect(Collectors.toList()));
    points.put(SCALE_SAME, Arrays.stream(POS1[SCALE_SAME.ordinal()]).map(Waypoints::waypointBuilder)
        .collect(Collectors.toList()));
    points.put(SCALE_OPPOSITE, Arrays.stream(POS1[SCALE_OPPOSITE.ordinal()])
        .map(Waypoints::waypointBuilder).collect(Collectors.toList()));
    points.put(DRIVE_FORWARD, Arrays.stream(POS1[DRIVE_FORWARD.ordinal()])
        .map(Waypoints::waypointBuilder).collect(Collectors.toList()));
    WAYPOINTS.put(StartingPos.LEFT, points);

    points = new EnumMap<>(Strategy.class);
    points.put(SWITCH_SAME, Arrays.stream(POS2[SWITCH_SAME.ordinal()])
        .map(Waypoints::waypointBuilder).collect(Collectors.toList()));
    points.put(SWITCH_OPPOSITE, Arrays.stream(POS2[SWITCH_OPPOSITE.ordinal()])
        .map(Waypoints::waypointBuilder).collect(Collectors.toList()));
    points.put(SCALE_SAME, Arrays.stream(POS2[SCALE_SAME.ordinal()]).map(Waypoints::waypointBuilder)
        .collect(Collectors.toList()));
    points.put(SCALE_OPPOSITE, Arrays.stream(POS2[SCALE_OPPOSITE.ordinal()])
        .map(Waypoints::waypointBuilder).collect(Collectors.toList()));
    points.put(DRIVE_FORWARD, Arrays.stream(POS2[DRIVE_FORWARD.ordinal()])
        .map(Waypoints::waypointBuilder).collect(Collectors.toList()));
    WAYPOINTS.put(StartingPos.MIDDLE, points);

    points = new EnumMap<>(Strategy.class);
    points.put(SWITCH_SAME, Arrays.stream(POS3[SWITCH_SAME.ordinal()])
        .map(Waypoints::waypointBuilder).collect(Collectors.toList()));
    points.put(SWITCH_OPPOSITE, Arrays.stream(POS3[SWITCH_OPPOSITE.ordinal()])
        .map(Waypoints::waypointBuilder).collect(Collectors.toList()));
    points.put(SCALE_SAME, Arrays.stream(POS3[SCALE_SAME.ordinal()]).map(Waypoints::waypointBuilder)
        .collect(Collectors.toList()));
    points.put(SCALE_OPPOSITE, Arrays.stream(POS3[SCALE_OPPOSITE.ordinal()])
        .map(Waypoints::waypointBuilder).collect(Collectors.toList()));
    points.put(DRIVE_FORWARD, Arrays.stream(POS3[DRIVE_FORWARD.ordinal()])
        .map(Waypoints::waypointBuilder).collect(Collectors.toList()));
    WAYPOINTS.put(StartingPos.RIGHT, points);
  }

  private static Waypoint waypointBuilder(double[] points) {
    return new Waypoint(points[0], points[1], points[2]);
  }
}


