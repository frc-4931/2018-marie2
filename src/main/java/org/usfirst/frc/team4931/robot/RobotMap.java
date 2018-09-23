package org.usfirst.frc.team4931.robot;

public enum RobotMap {

  // FIXME: Add real values

  /* Input */
  JOYSTICK(0),

  GRABBER_OPEN(7),
  GRABBER_CLOSE(10),

  SHIFT_TO_LOW_GEAR(9),
  SHIFT_TO_HIGH_GEAR(12),

  /* Motors */
  MOTOR_DT_FRONT_LEFT(3),
  MOTOR_DT_FRONT_RIGHT(1),
  MOTOR_DT_BACK_LEFT(4),
  MOTOR_DT_BACK_RIGHT(2),

  MOTOR_GRABBER(7),

  /* Grabber Config */

  /* The maximum distance the grabber can go to*/
  GRABBER_CONFIG_POSITION_MAX(2276),

  /* The travel speed of the grabber in distance per 100 milliseconds */
  GRABBER_CONFIG_CRUISE_VELOCITY(RobotMap.GRABBER_CONFIG_POSITION_MAX.getValue() / 10),

  /* Figure out this some other time */
  GRABBER_CONFIG_ACCELERATION(RobotMap.GRABBER_CONFIG_POSITION_MAX.getValue() / 3 / 10),

  /* Pneumatic */
  COMPRESSOR(6),

  /* GearBox */
  GEARBOX_1(1),
  GEARBOX_2(0),

  /* Grabber */
  GRABBER_1(2),
  GRABBER_2(3);

  private int value;

  RobotMap(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
