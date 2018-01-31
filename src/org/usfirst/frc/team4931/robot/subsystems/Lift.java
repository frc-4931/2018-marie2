package org.usfirst.frc.team4931.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4931.robot.RobotMap;

public class Lift extends Subsystem {

  private WPI_TalonSRX liftMotor;

  public Lift() {
    liftMotor = new WPI_TalonSRX(RobotMap.liftMotorPort);
    liftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    liftMotor.setSelectedSensorPosition(0, 0, 0);
  }

  @Override
  protected void initDefaultCommand() {
    // TODO Auto-generated method stub
  }

  public void setLiftHeight(FixedLiftHeight liftHeight) {
    switch (liftHeight) {
      case SCALE_TOP:
        liftMotor.set(ControlMode.MotionMagic, 480); //TODO set proper height value
        break;
      case SCALE_MID:
        liftMotor.set(ControlMode.MotionMagic, 360); //TODO set proper height value
        break;
      case SWITCH:
        liftMotor.set(ControlMode.MotionMagic, 240); //TODO set proper height value
        break;
      case EXCHANGE:
        liftMotor.set(ControlMode.MotionMagic, 120); //TODO set proper height value
        break;
      case FLOOR:
        liftMotor.set(ControlMode.MotionMagic, 0); //TODO set proper height value
        break;
    }
  }
}
