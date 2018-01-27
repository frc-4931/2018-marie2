package org.usfirst.frc.team4931.robot.field;

import java.util.EnumMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FieldAnalyzer {

  private char fieldPos[]; // position of switches and scale
  private EnumMap<Strategy, String> strategyOptions = new EnumMap<>(Strategy.class);
  private int robotStartingPos; // position of the robot before auto
  private char strategyPick[]; // selected strategy by the drive team

  public FieldAnalyzer() {
    strategyPick = SmartDashboard.getString("Strategy Field", "nnnnn").toLowerCase().toCharArray();
    robotStartingPos = Integer.parseInt(SmartDashboard.getString("Position Selection", "1"));
  }

  public void setFieldPosition(char[] fieldPosition) {
    fieldPos = fieldPosition;
  }

  public void predetermineStrategy() {
    for (Strategy s : Strategy.values()) {
      if (strategyPick[s.ordinal()] == 'y') {
        strategyOptions.put(s, "test");
      }
    }
  }

  public void calculateStrategy() {

  }

}
