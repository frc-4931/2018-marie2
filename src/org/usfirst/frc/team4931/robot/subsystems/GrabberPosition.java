package org.usfirst.frc.team4931.robot.subsystems;

/**
 * Categorizes the possible positions of the claw
 * @author shawn ely
 */
public enum GrabberPosition {
  LOW(2000), EXCHANGE(1200), SHOOT(800), HIGH(-50); //TODO set physical grabber encoder position

  private int grabberEncPos;

  GrabberPosition(int grabberEncPos) {
    this.grabberEncPos = grabberEncPos;
  }

  public int position() {
    return grabberEncPos;
  }

}
