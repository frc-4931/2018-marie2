package org.usfirst.frc.team4931.robot.field;

import java.util.EnumMap;
import java.util.List;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.FitMethod;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

public class FieldAnalyzer {

  private char fieldPos[]; // position of switches and scale
  private EnumMap<Strategy, TankModifier> strategyOptions = new EnumMap<>(Strategy.class);
  private StartingPos robotStartingPos; // position of the robot before auto
  private char strategyPick[]; // selected strategy by the drive team
  private double dt = 0.05; // time between each different straight path on the curve
  private double maxSpeed = 4.0;
  private double maxAcceleration = 3.0;
  private double maxJerk = 60.0;

  public void setFieldPosition(char[] fieldPosition) {
    fieldPos = fieldPosition;
  }

  public void predetermineStrategy() {
    strategyPick = SmartDashboard.getString("Strategy Field", "nnnnn").toLowerCase().toCharArray();
    robotStartingPos = StartingPos
        .valueOf(SmartDashboard.getString("Position Selection", StartingPos.LEFT.name()));
    Trajectory.Config config = new Trajectory.Config(FitMethod.HERMITE_CUBIC,
        Trajectory.Config.SAMPLES_FAST, dt, maxSpeed, maxAcceleration, maxJerk);
    for (Strategy s : Strategy.values()) {
      if (strategyPick[s.ordinal()] == 'y') {
        List<Waypoint> point = Waypoints.WAYPOINTS.get(robotStartingPos).get(s);
        TankModifier tankModifier =
            new TankModifier(Pathfinder.generate(point.toArray(new Waypoint[] {}), config));
        strategyOptions.put(s, tankModifier);
      }
    }
  }

  public TankModifier calculateStrategy() {
    switch (robotStartingPos) {
      case LEFT:
        return pickStrategy('l', 'r');
      case RIGHT:
        return pickStrategy('r', 'l');
      default:
        if (strategyOptions.containsKey(Strategy.SWITCH_SAME)) {
          return strategyOptions.get(Strategy.SWITCH_SAME);
        } else if (strategyOptions.containsKey(Strategy.SWITCH_OPPOSITE)) {
          return strategyOptions.get(Strategy.SWITCH_OPPOSITE);
        }
        return strategyOptions.get(Strategy.DRIVE_FORWARD);
    }
  }

  private TankModifier pickStrategy(char sameChar, char oppositeChar) {
    if (fieldPos[0] == sameChar && strategyOptions.containsKey(Strategy.SWITCH_SAME)) {
      return strategyOptions.get(Strategy.SWITCH_SAME);
    } else if (fieldPos[1] == sameChar && strategyOptions.containsKey(Strategy.SCALE_SAME)) {
      return strategyOptions.get(Strategy.SCALE_SAME);
    } else if (fieldPos[0] == oppositeChar
        && strategyOptions.containsKey(Strategy.SWITCH_OPPOSITE)) {
      return strategyOptions.get(Strategy.SWITCH_OPPOSITE);
    } else if (fieldPos[1] == oppositeChar
        && strategyOptions.containsKey(Strategy.SCALE_OPPOSITE)) {
      return strategyOptions.get(Strategy.SCALE_OPPOSITE);
    } else {
      return strategyOptions.get(Strategy.DRIVE_FORWARD);
    }
  }
}
