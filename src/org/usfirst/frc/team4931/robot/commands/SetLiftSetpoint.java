package org.usfirst.frc.team4931.robot.commands;

import org.usfirst.frc.team4931.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetLiftSetpoint extends Command {
	private double setpoint;
	
	public SetLiftSetpoint(double setpoint) {
		this.setpoint = setpoint;
		requires(Robot.lift);
	}

	@Override
	protected void initialize() {
		Robot.lift.enable();
		Robot.lift.setSetpoint(setpoint);
	}
	@Override
	protected boolean isFinished() {
		return Robot.lift.onTarget();
	}
}
