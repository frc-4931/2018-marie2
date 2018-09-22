package org.usfirst.frc.team4931.robot;

public enum RobotMap {

	/* Input */
	JOYSTICK(0),

	GRABBER_OPEN(7), GRABBER_CLOSE(10),

	SHIFT_TO_LOW_GEAR(9), SHIFT_TO_HIGH_GEAR(12),

	/* Motors */
	MOTOR_DT_FRONT_LEFT(3), MOTOR_DT_FRONT_RIGHT(1), MOTOR_DT_BACK_LEFT(4), MOTOR_DT_BACK_RIGHT(2),

	MOTOR_GRABBER(7),

	/* Compressor */
	COMPRESSOR(6),

	/* GearBox */
	GEARBOX_1(1), GEARBOX_2(0),

	GRABBER_1(2), GRABBER_2(3);

	private int value;

	RobotMap(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
