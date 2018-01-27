package org.usfirst.frc.team4931.robot.field;

import static org.usfirst.frc.team4931.robot.field.Strategy.DRIVE_FORWARD;
import static org.usfirst.frc.team4931.robot.field.Strategy.SCALE_OPPOSITE;
import static org.usfirst.frc.team4931.robot.field.Strategy.SCALE_SAME;
import static org.usfirst.frc.team4931.robot.field.Strategy.SWITCH_OPPOSITE;
import static org.usfirst.frc.team4931.robot.field.Strategy.SWITCH_SAME;
import java.util.EnumMap;
import java.util.Map;

public class Waypoints {
  public static Map<StartingPos, Map<Strategy, int[][]>> WAYPOINTS =
      new EnumMap<>(StartingPos.class);



  public static int[][][] POS1 = {{ // SWITCH_SAME
      {0, 24, 0}, {11, 25, -35}, {14, 20, -90}},
      { // SCALE_SAME
          {0, 24, 0}, {20, 25, 0}, {27, 21, -90}},
      { // SWITCH_OPPOSITE
          {0, 24, 0}, {15, 24, 0}, {20, 19, -90}, {20, 12, -90}, {16, 9, 180}},
      { // SCALE_OPPOSITE
          {0, 24, 0}, {17, 23, 0}, {20, 20, -90}, {20, 5, -90}, {24, 2, 0}, {27, 6, 90}},
      { // DRIVE_FORWARD
          {0, 24, 0}, {20, 24, 0}},};

  public static int[][][] POS2 = {{ // SWITCH_SAME
      {0, 14, 0}, {7, 18, 35}, {12, 18, 0}},
      { // SCALE_SAME
          {0, 14, 0}, {7, 20, 90}, {11, 23, 0}, {21, 24, 0}, {27, 21, -90}},
      { // SWITCH_OPPOSITE
          {0, 14, 0}, {7, 9, -35}, {12, 8, 0}},
      { // SCALE_OPPOSITE
          {0, 14, 0}, {6, 9, -90}, {10, 4, 0}, {21, 2, 0}, {27, 6, 90}},
      { // DRIVE_FORWARD
          {0, 14, 0}, {7, 15, 35}, {10, 18, 90}, {10, 20, 90}},};

  public static int[][][] POS3 = {{ // SWITCH_SAME
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
    Map<Strategy, int[][]> points = new EnumMap<>(Strategy.class);
    points.put(SWITCH_SAME, POS1[SWITCH_SAME.ordinal()]);
    points.put(SWITCH_OPPOSITE, POS1[SWITCH_OPPOSITE.ordinal()]);
    points.put(SCALE_SAME, POS1[SCALE_SAME.ordinal()]);
    points.put(SCALE_OPPOSITE, POS1[SCALE_OPPOSITE.ordinal()]);
    points.put(DRIVE_FORWARD, POS1[DRIVE_FORWARD.ordinal()]);
    WAYPOINTS.put(StartingPos.LEFT, points);

    points = new EnumMap<>(Strategy.class);
    points.put(SWITCH_SAME, POS2[SWITCH_SAME.ordinal()]);
    points.put(SWITCH_OPPOSITE, POS2[SWITCH_OPPOSITE.ordinal()]);
    points.put(SCALE_SAME, POS2[SCALE_SAME.ordinal()]);
    points.put(SCALE_OPPOSITE, POS2[SCALE_OPPOSITE.ordinal()]);
    points.put(DRIVE_FORWARD, POS2[DRIVE_FORWARD.ordinal()]);
    WAYPOINTS.put(StartingPos.MIDDLE, points);

    points = new EnumMap<>(Strategy.class);
    points.put(SWITCH_SAME, POS3[SWITCH_SAME.ordinal()]);
    points.put(SWITCH_OPPOSITE, POS3[SWITCH_OPPOSITE.ordinal()]);
    points.put(SCALE_SAME, POS3[SCALE_SAME.ordinal()]);
    points.put(SCALE_OPPOSITE, POS3[SCALE_OPPOSITE.ordinal()]);
    points.put(DRIVE_FORWARD, POS3[DRIVE_FORWARD.ordinal()]);
    WAYPOINTS.put(StartingPos.RIGHT, points);
  }
}


