package org.usfirst.frc.team4931.robot.commands;

import org.usfirst.frc.team4931.robot.Robot;
import org.usfirst.frc.team4931.robot.subsystems.Grabber;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class GrabberRotationWithStick extends Command {

	private Joystick joystick;
	private Grabber grabber;

	public GrabberRotationWithStick() {
		requires(Robot.getGrabber());
		joystick = Robot.getOperatorInput().getJoystick();
		grabber = Robot.getGrabber();
	}

	@Override
	protected void execute() {
		grabber.getGrabberMotor().set(joystick.getZ());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		grabber.getGrabberMotor().set(0);
	}
}
