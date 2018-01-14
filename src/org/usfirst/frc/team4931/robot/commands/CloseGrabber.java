package org.usfirst.frc.team4931.robot.commands;

import org.usfirst.frc.team4931.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CloseGrabber extends Command {
	
	public CloseGrabber() {
		requires(Robot.grabber);
		// enable this if using claw
		//setInterruptible(false);
	}
	
	@Override
	public void start() {
		Robot.grabber.captureCube();
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
