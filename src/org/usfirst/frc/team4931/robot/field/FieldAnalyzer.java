package org.usfirst.frc.team4931.robot.field;

import java.util.EnumMap;
import java.util.List;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.FitMethod;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

/**
 * @author dj wickman and shawn ely
 * 
 * Uses the position of the field, the robot starting position, and the
 * inputed desired autonomous routines to determine the proper autonomous
 * mode strategy. Each strategy has a set of waypoints used as
 * trajectories that is further described in the Waypoints class.
 */
public class FieldAnalyzer {

  private char fieldPos[]; // position of switches and scale
  private EnumMap<Strategy, TankModifier> strategyOptions = new EnumMap<>(Strategy.class);
  private StartingPos robotStartingPos; // position of the robot before auto
  private char strategyPick[]; // selected strategy by the drive team
  private double dt = 0.05; // TODO Set time between each different straight path on the curve
  private double maxSpeed = 4.0; //TODO Set max speed
  private double maxAcceleration = 3.0; //TODO Set max acceleration
  private double maxJerk = 60.0; //TODO Set max jerk
  private Strategy pickedStrategy;
  private TankModifier pickedTrajectory;

  /**
   * Gets the position of the field for strategy selection.
   */
  public void setFieldPosition(char[] fieldPosition) {
    fieldPos = fieldPosition;
  }

  /**
   * On the smart dashboard, there should be an already existing field
   * of 5 chars that correspond with the 5 possible strategies for each
   * position. When the 5 char line of 'y' or 'n' is inputed by the drive
   * team, predetermineStrategy gets that line, and compares them to the
   * strategy they correspond with. If the char corresponding to a
   * strategy is 'y', then it will render that strategy and hold it as a
   * possible strategy to calculate. If the char corresponding to a
   * strategy is 'n', then it will not render that strategy and will never
   * consider that strategy as a possible strategy to run.
   */
  public void predetermineStrategy() {
    strategyPick = SmartDashboard.getString("Strategy Field", "nnnnn").toLowerCase().toCharArray();
    robotStartingPos = StartingPos
        .valueOf(SmartDashboard.getString("Position Selection", StartingPos.LEFT.name()));
    Trajectory.Config config = new Trajectory.Config(FitMethod.HERMITE_CUBIC,
        Trajectory.Config.SAMPLES_FAST, dt, maxSpeed, maxAcceleration, maxJerk);
    for (Strategy s : Strategy.values()) {
      if (strategyPick[s.ordinal()] == 'y') {
        Waypoint[] point = Waypoints.WAYPOINTS.get(robotStartingPos).get(s);
        TankModifier tankModifier =
            new TankModifier(Pathfinder.generate(point, config));
        strategyOptions.put(s, tankModifier);
      }
    }
  }
/**
 * This calculates the strategy for autonomous based on field position,
 * robot position, and which autonomous routines have been rendered.
 */
  public void calculateStrategy() {
    switch (robotStartingPos) {
      case LEFT:
        pickStrategy('l', 'r');
      case RIGHT:
        pickStrategy('r', 'l');
      default:
        if (strategyOptions.containsKey(Strategy.SWITCH_SAME)) {
          pickedStrategy = Strategy.SWITCH_SAME;
        } else if (strategyOptions.containsKey(Strategy.SWITCH_OPPOSITE)) {
          pickedStrategy = Strategy.SWITCH_OPPOSITE;
        } else {
          pickedStrategy = Strategy.DRIVE_FORWARD;
        }
    }
    pickedTrajectory = strategyOptions.get(pickedStrategy);
  }
  
  /**
   * Used by calculateStrategy() to calculate the strategy for the left
   * and right positions
   */
  private void pickStrategy(char sameChar, char oppositeChar) {
    if (fieldPos[0] == sameChar && strategyOptions.containsKey(Strategy.SWITCH_SAME)){
      pickedStrategy = Strategy.SWITCH_SAME;
    } else if (fieldPos[1] == sameChar && strategyOptions.containsKey(Strategy.SCALE_SAME)) {
      pickedStrategy = Strategy.SCALE_SAME;
    } else if (fieldPos[0] == oppositeChar
        && strategyOptions.containsKey(Strategy.SWITCH_OPPOSITE)) {
      pickedStrategy = Strategy.SWITCH_OPPOSITE;
    } else if (fieldPos[1] == oppositeChar
        && strategyOptions.containsKey(Strategy.SCALE_OPPOSITE)) {
      pickedStrategy = Strategy.SCALE_OPPOSITE;
    } else {
      pickedStrategy = Strategy.DRIVE_FORWARD;
    }
  }
  
  /**
   * Returns the strategy that was picked by calculateStrategy()
   */
  public Strategy getPickedStrategy() {
    return pickedStrategy;
  }
  
  /**
   * Returns the calculated trajectory based on the picked strategy
   */
  public TankModifier getPickedTrajectory() {
    return pickedTrajectory;
  }
}
