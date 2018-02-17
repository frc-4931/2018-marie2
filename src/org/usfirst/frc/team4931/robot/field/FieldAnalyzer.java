package org.usfirst.frc.team4931.robot.field;

import java.util.EnumMap;
import org.usfirst.frc.team4931.robot.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.FitMethod;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

/**
 * Uses the position of the field, the robot starting position, and the
 * inputed desired autonomous routines to determine the proper autonomous
 * mode strategy. Each strategy has a set of waypoints used as
 * trajectories that is further described in the Waypoints class.
 * @author dj wickman and shawn ely
 */
public class FieldAnalyzer {

  private static final double WHEEL_BASE = 0.635;
  private char fieldPos[]; // position of switches and scale
  private EnumMap<Strategy, TankModifier> strategyOptions = new EnumMap<>(Strategy.class);
  private StartingPos robotStartingPos; // position of the robot before auto
  private char strategyPick[]; // selected strategy by the drive team
  private double dt = 0.05; // TODO Set time between each different straight path on the curve
  private double maxSpeed = 7.1; //TODO Set max speed (700 encoder pulses in high gear)
  private double maxAcceleration = 3.75; //TODO Set max acceleration  (375 encoder pulses in high gear)
  private double maxJerk = 18.75; //TODO Set max jerk
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
    strategyPick = SmartDashboard.getString(RobotMap.STRATEGY_FIELD, "nnnnn").toLowerCase().toCharArray();
    robotStartingPos = StartingPos
        .valueOf(SmartDashboard.getString(RobotMap.POSITION_SELECTION, StartingPos.LEFT.name()));
    System.out.println(strategyPick[0]+strategyPick[1]+strategyPick[2]+strategyPick[3]+strategyPick[4] + "\n" + robotStartingPos + "\n");

    Trajectory.Config config = new Trajectory.Config(FitMethod.HERMITE_CUBIC,
        Trajectory.Config.SAMPLES_FAST, dt, maxSpeed, maxAcceleration, maxJerk);
    for (Strategy s : Strategy.values()) {
      if (strategyPick[s.ordinal()] == 'y') {
        Waypoint[] point = Waypoints.WAYPOINTS.get(robotStartingPos).get(s);
        TankModifier tankModifier =
            new TankModifier(Pathfinder.generate(point, config));
        tankModifier.modify(WHEEL_BASE);
        strategyOptions.put(s, tankModifier);

        System.out.println(s.name());
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
        break;
      case RIGHT:
        pickStrategy('r', 'l');
        break;
      default: //Left siwtch/scale = _SAME. Right switch/scale = OPPOSITE
        pickStrategy('l', 'r');
        break;

        //Commented out because I'm unsure whether we atually wanted this logic that doesn't change based the field setup
        /*if (strategyOptions.containsKey(Strategy.SWITCH_SAME)) {
          pickedStrategy = Strategy.SWITCH_SAME;
        } else if (strategyOptions.containsKey(Strategy.SWITCH_OPPOSITE)) {
          pickedStrategy = Strategy.SWITCH_OPPOSITE;
        } else {
          pickedStrategy = Strategy.DRIVE_FORWARD;
        }*/
    }
    pickedTrajectory = strategyOptions.get(pickedStrategy);
  }
  
  /**
   * Selects the best strategy based on field setup from the pre-rendered strategies.
   */
  private void pickStrategy(char sameChar, char oppositeChar) {
    if (fieldPos[0] == sameChar && strategyOptions.containsKey(Strategy.SWITCH_SAME)){
      pickedStrategy = Strategy.SWITCH_SAME;
    } else if (fieldPos[1] == sameChar && strategyOptions.containsKey(Strategy.SCALE_SAME)) {
      pickedStrategy = Strategy.SCALE_SAME;
    } else if (fieldPos[0] == oppositeChar && strategyOptions.containsKey(Strategy.SWITCH_OPPOSITE)) {
      pickedStrategy = Strategy.SWITCH_OPPOSITE;
    } else if (fieldPos[1] == oppositeChar && strategyOptions.containsKey(Strategy.SCALE_OPPOSITE)) {
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
