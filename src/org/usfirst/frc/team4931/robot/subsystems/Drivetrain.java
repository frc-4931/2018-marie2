package org.usfirst.frc.team4931.robot.subsystems;

import org.usfirst.frc.team4931.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;;

public class Drivetrain extends Subsystem {
	private static WPI_TalonSRX leftFrontMotor;
	private static WPI_TalonSRX leftBackMotor;
	private static WPI_TalonSRX rightFrontMotor;
	private static WPI_TalonSRX rightBackMotor;
	private static SpeedControllerGroup leftSideMotors;
	private static SpeedControllerGroup rightSideMotors;
	private static PIDController leftSidePID;
	private static PIDController rightSidePID;
	private static Encoder leftEncoder;
	private static Encoder rightEncoder;
	private static DifferentialDrive drivetrain;
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
		leftFrontMotor = new WPI_TalonSRX(RobotMap.leftFrontMotorPort);
		leftBackMotor = new WPI_TalonSRX(RobotMap.leftBackMotorPort);
		rightFrontMotor = new WPI_TalonSRX(RobotMap.rightFrontMotorPort);
		rightBackMotor = new WPI_TalonSRX(RobotMap.rightBackMotorPort);
		
		leftFrontMotor.setInverted(RobotMap.leftFrontMotorInverted);
		leftBackMotor.setInverted(RobotMap.leftBackMotorInverted);
		rightFrontMotor.setInverted(RobotMap.rightFrontMotorInverted);
		rightBackMotor.setInverted(RobotMap.rightBackMotorInverted);

		leftSideMotors = new SpeedControllerGroup(leftFrontMotor, leftBackMotor);
		rightSideMotors = new SpeedControllerGroup(rightFrontMotor,rightBackMotor);
		
		leftEncoder = new Encoder(RobotMap.leftEncoderPorts[0], RobotMap.leftEncoderPorts[1], RobotMap.leftEncoderInverted, EncodingType.k4X);
		rightEncoder = new Encoder(RobotMap.rightEncoderPorts[0], RobotMap.rightEncoderPorts[1], RobotMap.rightEncoderInverted, EncodingType.k4X);
		
		leftSidePID = new PIDController(0, 0, 0, 0, leftEncoder, leftFrontMotor);
		rightSidePID = new PIDController(0, 0, 0, 0, rightEncoder,rightFrontMotor);
		
		drivetrain = new DifferentialDrive(leftSideMotors, rightSideMotors);
	}
	
	public void drive(double speed, double rotation) {
		drivetrain.arcadeDrive(speed, rotation);
	}
	
	public int getLeftEncoder() {
		return leftEncoder.get();
	}
	
	public int getRightEncoder() {
		return rightEncoder.get();
	}
	
	public void resetLeftEncoder() {
		leftEncoder.reset();
	}
	
	public void resetRightEncoder() {
		rightEncoder.reset();
	}
	
	public void freePIDControllers() {
		leftSidePID.free();
		rightSidePID.free();
	}
	
}