package org.usfirst.frc.team4931.robot.subsystems;

/**
 * Categorizes the possible positions of the claw
 * @author shawn ely
 */
public enum GrabberPosition {
  LOW(0), EXCHANGE(1024), SHOOT(2048), HIGH(3072); //TODO set physical grabber encoder position

  private int grabberEncPos;

  GrabberPosition(int grabberEncPos) {
    this.grabberEncPos = grabberEncPos;
  }

  public int position() {
    return grabberEncPos;
  }

}
