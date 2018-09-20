package org.usfirst.frc.team4931.robot;

import org.usfirst.frc.team4931.robot.subsystems.Drivetrain;
import org.usfirst.frc.team4931.robot.subsystems.Grabber;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	private static Grabber grabber;
	private static Drivetrain drivetrain;

	private static OperatorInput operatorInput;

	@Override
	public void robotInit() {
		grabber = new Grabber();
		drivetrain = new Drivetrain();
		
		operatorInput = new OperatorInput();
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
