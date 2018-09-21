package org.usfirst.frc.team4931.robot.commands;

import org.usfirst.frc.team4931.robot.Robot;
import org.usfirst.frc.team4931.robot.enums.GrabberState;

import edu.wpi.first.wpilibj.command.Command;

public class ChangeGrabberState extends Command {

	GrabberState grabberState;

	public ChangeGrabberState(GrabberState grabberState) {
		this.grabberState = grabberState;
	}

	@Override
	protected void initialize() {
		Robot.getGrabber().changeGrabberState(grabberState);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
