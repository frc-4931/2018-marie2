package org.usfirst.frc.team4931.robot.subsystems;

import org.usfirst.frc.team4931.robot.RobotMap;
import org.usfirst.frc.team4931.robot.commands.GrabberRotationWithStick;
import org.usfirst.frc.team4931.robot.enums.GrabberState;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Grabber extends Subsystem {

	private DoubleSolenoid pneumatic;
	private WPI_TalonSRX grabberMotor;

	public Grabber() {
		pneumatic = new DoubleSolenoid(RobotMap.COMPRESSOR.getValue(), RobotMap.GRABBER_1.getValue(),
				RobotMap.GRABBER_2.getValue());

		grabberMotor = new WPI_TalonSRX(RobotMap.MOTOR_GRABBER.getValue());
		grabberMotor.setInverted(true);
		grabberMotor.setNeutralMode(NeutralMode.Brake);
	}


	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new GrabberRotationWithStick());
	}

	public void changeGrabberState(GrabberState grabberState) {
		switch (grabberState) {
		case OPENED:
			pneumatic.set(Value.kForward);
			break;
		case CLOSED:
			pneumatic.set(Value.kReverse);
			break;
		}
	}

	public DoubleSolenoid getPneumatic() {
		return pneumatic;
	}

	public WPI_TalonSRX getGrabberMotor() {
		return grabberMotor;
	}
}
