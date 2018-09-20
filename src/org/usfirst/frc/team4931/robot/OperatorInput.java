package org.usfirst.frc.team4931.robot;

import org.usfirst.frc.team4931.robot.commands.ShiftGear;
import org.usfirst.frc.team4931.robot.enums.Gear;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


public class OperatorInput {

	private Joystick joystick;

	public OperatorInput() {
		joystick = makeJoystick();
	}

	private Joystick makeJoystick() {
		Joystick joystick = new Joystick(RobotMap.JOYSTICK.getValue());

		Button grabberOpen = new JoystickButton(joystick, RobotMap.GRABBER_OPEN.getValue());
		Button grabberClose = new JoystickButton(joystick, RobotMap.GRABBER_CLOSE.getValue());

		/* Shifting */
		Button shiftToLowGear = new JoystickButton(joystick, RobotMap.SHIFT_TO_LOW_GEAR.getValue());
		shiftToLowGear.whenPressed(new ShiftGear(Gear.LOW));

		Button shiftToHighGear = new JoystickButton(joystick, RobotMap.SHIFT_TO_HIGH_GEAR.getValue());
		shiftToHighGear.whenPressed(new ShiftGear(Gear.HIGH));
		
		return joystick;
	}

	public Joystick getJoystick() {
		return joystick;
	}
}
