package org.usfirst.frc.team4931.robot.field;

import java.util.EnumMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FieldAnalyzer {

  private char fieldPos[]; // position of switches and scale
  private EnumMap<Strategy, String> strategyOptions = new EnumMap<>(Strategy.class);
  private StartingPos robotStartingPos; // position of the robot before auto
  private char strategyPick[]; // selected strategy by the drive team

  public void setFieldPosition(char[] fieldPosition) {
    fieldPos = fieldPosition; // Removing bugs created more bugs
  }

  public void predetermineStrategy() {
    strategyPick = SmartDashboard.getString("Strategy Field", "nnnnn").toLowerCase().toCharArray();
    robotStartingPos = StartingPos
        .valueOf(SmartDashboard.getString("Position Selection", StartingPos.LEFT.name()));
    for (Strategy s : Strategy.values()) {
      if (strategyPick[s.ordinal()] == 'y') {
        int[][] point = Waypoints.WAYPOINTS.get(robotStartingPos).get(s);
        strategyOptions.put(s, "test");
      }
    }
  }

  public void calculateStrategy() {

  }

}
