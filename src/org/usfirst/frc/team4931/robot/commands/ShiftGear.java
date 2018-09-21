package org.usfirst.frc.team4931.robot.commands;

import org.usfirst.frc.team4931.robot.Robot;
import org.usfirst.frc.team4931.robot.enums.Gear;

import edu.wpi.first.wpilibj.command.Command;

public class ShiftGear extends Command {

	private Gear gear;

	public ShiftGear(Gear gear) {
		requires(Robot.getDrivetrain());
		this.gear = gear;
	}

	@Override
	protected void initialize() {
		Robot.getDrivetrain().shiftGear(gear);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
