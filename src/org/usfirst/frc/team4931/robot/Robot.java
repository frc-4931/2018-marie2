package org.usfirst.frc.team4931.robot;

import org.usfirst.frc.team4931.robot.subsystems.Drivetrain;
import org.usfirst.frc.team4931.robot.subsystems.Grabber;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

	private static Grabber grabber;
	private static Drivetrain drivetrain;

	private static OperatorInput operatorInput;

	@Override
	public void robotInit() {
		operatorInput = new OperatorInput();
		
		grabber = new Grabber();
		drivetrain = new Drivetrain();
	}

	public static Grabber getGrabber() {
		return grabber;
	}

	public static Drivetrain getDrivetrain() {
		return drivetrain;
	}

	public static OperatorInput getOperatorInput() {
		return operatorInput;
	}
}
