package org.usfirst.frc.team4931.robot.enums;

import org.usfirst.frc.team4931.robot.RobotMap;

public enum GrabberPosition {
  FORWARD_DOWN(0),
  FORWARD_STRAIGHT(1),
  FORWARD_SWITCH(2),
  VERTICAL(3),
  BACK_SWITCH(4),
  BACK_STRAIGHT(5),
  BACK_DOWN(6);

  private int position;

  GrabberPosition(int position) {
    this.position =
        RobotMap.GRABBER_MAX_POSITION.getValue() / (GrabberPosition.values().length - 1) * position;
  }

  public int getPosition() {
    return position;
  }
}
