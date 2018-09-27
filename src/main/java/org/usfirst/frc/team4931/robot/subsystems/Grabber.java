package org.usfirst.frc.team4931.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4931.robot.RobotMap;
import org.usfirst.frc.team4931.robot.commands.GrabberRotationWithThrottle;
import org.usfirst.frc.team4931.robot.enums.GrabberState;

public class Grabber extends Subsystem {

  // DigitalInput forwardLimitSwitch, reverseLimitSwitch;

  private DoubleSolenoid pneumatic;
  private WPI_TalonSRX grabberMotor;

  public Grabber() {
    /* TODO: Add limit switch */
    // DigitalInput forwardLimitSwitch = new DigitalInput(-1);
    // DigitalInput reverseLimitSwitch = new DigitalInput(-1);

    pneumatic =
        new DoubleSolenoid(
            RobotMap.COMPRESSOR.getValue(),
            RobotMap.GRABBER_1.getValue(),
            RobotMap.GRABBER_2.getValue());

    grabberMotor = new WPI_TalonSRX(RobotMap.MOTOR_GRABBER.getValue());
    grabberMotor.setInverted(true);
    grabberMotor.setNeutralMode(NeutralMode.Brake);

    grabberMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    grabberMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 0);
    grabberMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 0);

    grabberMotor.configNominalOutputForward(0, 0);
    grabberMotor.configNominalOutputReverse(0, 0);

    grabberMotor.configPeakOutputForward(1, 0);
    grabberMotor.configPeakOutputReverse(-1, 0);

    /* PID */
    // FIXME: Change PID Stuff
    grabberMotor.selectProfileSlot(0, 0);
    grabberMotor.config_kF(0, 0.2, 0);
    grabberMotor.config_kP(0, 0.2, 0);
    grabberMotor.config_kI(0, 0, 0);
    grabberMotor.config_kD(0, 0, 0);

    grabberMotor.configMotionCruiseVelocity(RobotMap.GRABBER_CONFIG_CRUISE_VELOCITY.getValue(), 0);
    grabberMotor.configMotionAcceleration(RobotMap.GRABBER_CONFIG_ACCELERATION.getValue(), 0);
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new GrabberRotationWithThrottle());
  }

  public void changeGrabberPosition(int position) {
    int maxPosition = RobotMap.GRABBER_CONFIG_POSITION_MAX.getValue();

    if (position > 0 && position <= maxPosition)
      grabberMotor.set(ControlMode.MotionMagic, position);
    else
      System.out.println(
          "Tried to move grabber to position "
              + position
              + ". It is either less than 0 or greater than "
              + maxPosition);
  }

  public void changeGrabberState(GrabberState grabberState) {
    switch (grabberState) {
      case OPENED:
        pneumatic.set(Value.kForward);
        break;
      case CLOSED:
        pneumatic.set(Value.kReverse);
        break;
      case TOGGLE:
        pneumatic.set(pneumatic.get() == Value.kReverse ? Value.kForward : Value.kReverse);
    }
  }

  public void log() {
    SmartDashboard.putBoolean("Grabber Open", pneumatic.get() == Value.kForward);
    SmartDashboard.putNumber("Grabber Position", grabberMotor.getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Grabber Speed", grabberMotor.get());
  }
}
