package org.usfirst.frc.team4931.robot.subsystems;

/**
 * These are the presets of the heights of the lift.
 */
public enum FixedLiftHeight {
  FLOOR(0), SWITCH(120), EXCHANGE(240), SCALE_MID(360), SCALE_TOP(480); //TODO set physical lift heights

  private int encoderPosition;

  FixedLiftHeight(int encoderPosition) {
    this.encoderPosition = encoderPosition;
  }

  public int position() {
    return encoderPosition;
  }

}
