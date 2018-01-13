package org.usfirst.frc.team4931.robot.subsystems;

import org.usfirst.frc.team4931.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Grabber extends Subsystem {
	private WPI_TalonSRX armMotorLeft;
	private WPI_TalonSRX armMotorRight;
	private static final double CAPTURE_SPEED = 1;
	private static final double RELEASE_SPEED = -1;
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		armMotorLeft = new WPI_TalonSRX(RobotMap.armMotorLeftPort);
		armMotorRight = new WPI_TalonSRX(RobotMap.armMotorRightPort);
		armMotorLeft.setInverted(RobotMap.armMotorLeftInverted);
		armMotorRight.setInverted(RobotMap.armMotorRightInverted);
	}
	
	/**
	 * Spins the wheels of the grabber to suck in and take control of a power cube
	 */
	public void captureCube() {
		armMotorLeft.set(CAPTURE_SPEED);
		armMotorRight.set(CAPTURE_SPEED);
	}
	
	/**
	 * Spins the wheels of the grabber to eject the power cube
	 */
	public void releaseCube() {
		armMotorLeft.set(RELEASE_SPEED);
		armMotorRight.set(RELEASE_SPEED);
	}

}
