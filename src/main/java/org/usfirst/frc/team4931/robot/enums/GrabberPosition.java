package org.usfirst.frc.team4931.robot.enums;

import org.usfirst.frc.team4931.robot.RobotMap;

public enum GrabberPosition {
  FORWARD_DOWN(0),
  FORWARD_STRAIGHT(1),
  FORWARD_SWITCH(2),
  VERTICAL(3),
  BACKWARD_SWITCH(4),
  BACKWARD_STRAIGHT(5),
  BACKWARD_DOWN(6);

  private int position;

  GrabberPosition(int position) {
    this.position =
        RobotMap.GRABBER_CONFIG_POSITION_MAX.getValue() / (this.values().length - 1) * position;
  }

  public int getPosition() {
    return position;
  }
}
