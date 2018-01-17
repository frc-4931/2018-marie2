package org.usfirst.frc.team4931.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Lift extends Subsystem {
	private WPI_TalonSRX liftMotor;
	private static final double LIFT_SPEED = 1;

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Lifting mechanism will rise up full speed
	 */
	public void liftUp() {
		liftMotor.set(LIFT_SPEED);
	}
	
	/**
	 * Lifting mechanism will descend full speed
	 */
	public void liftDown() {
		liftMotor.set(-LIFT_SPEED);
	}

}
