package org.usfirst.frc.team4931.robot.commands;

import org.usfirst.frc.team4931.robot.Robot;
import org.usfirst.frc.team4931.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class DriveWithJoyStick extends Command {

	private Joystick joystick;
	private Drivetrain drivetrain;

	public DriveWithJoyStick() {
		requires(Robot.getDrivetrain());
		joystick = Robot.getOperatorInput().getJoystick();
		drivetrain = Robot.getDrivetrain();
	}

	@Override
	protected void execute() {
		if(joystick.getTriggerPressed())
			drivetrain.tankDrive(joystick.getX(), joystick.getX() * -1);
		else 
			drivetrain.arcadeDrive(joystick.getY(), joystick.getX(), 1 - ((joystick.getThrottle() + 1) / 2));
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		drivetrain.arcadeDrive(0, 0, 0);
	}
}
