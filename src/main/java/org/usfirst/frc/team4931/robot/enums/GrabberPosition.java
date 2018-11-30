package org.usfirst.frc.team4931.robot.enums;

public enum GrabberPosition {
  FORWARD_DOWN(0),
  FORWARD_STRAIGHT(1),
  FORWARD_SWITCH(2),
  VERTICAL(3),
  BACKWARD_SWITCH(4),
  BACKWARD_STRAIGHT(5),
  BACKWARD_DOWN(6);

  private int position;

  GrabberPosition(int percentage) {
    position = 6000 / (6 - 1) * percentage;
  }

  public int getPosition() {
    return position;
  }
}
