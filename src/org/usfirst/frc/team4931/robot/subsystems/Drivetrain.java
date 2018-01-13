package org.usfirst.frc.team4931.robot.subsystems;

import org.usfirst.frc.team4931.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;;

public class Drivetrain extends Subsystem {
	private static WPI_TalonSRX leftFrontMotor;
	private static WPI_TalonSRX leftBackMotor;
	private static WPI_TalonSRX rightFrontMotor;
	private static WPI_TalonSRX rightBackMotor;
	private static SpeedControllerGroup leftSide;
	private static SpeedControllerGroup rightSide;
	private static DifferentialDrive drivetrain;
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
		leftFrontMotor = new WPI_TalonSRX(RobotMap.leftFrontMotorPort);
		leftBackMotor = new WPI_TalonSRX(RobotMap.leftBackMotorPort);
		leftSide = new SpeedControllerGroup(leftFrontMotor, leftBackMotor);
		rightFrontMotor = new WPI_TalonSRX(RobotMap.rightFrontMotorPort);
		rightBackMotor = new WPI_TalonSRX(RobotMap.rightBackMotorPort);
		rightSide = new SpeedControllerGroup(rightFrontMotor,rightBackMotor);
		drivetrain = new DifferentialDrive(leftSide, rightSide);
	}
	
	public void drive(double speed, double rotation) {
		drivetrain.arcadeDrive(speed, rotation);
	}
	
}