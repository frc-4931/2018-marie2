package org.usfirst.frc.team4931.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4931.robot.RobotMap;

public class Climber extends Subsystem {
  private WPI_TalonSRX climberMotor;

  public Climber() {
    climberMotor = new WPI_TalonSRX(RobotMap.climberMotorPort);
    climberMotor.setNeutralMode(NeutralMode.Brake);
    climberMotor.setInverted(RobotMap.climberMotorInverted);
  }

  @Override
  protected void initDefaultCommand() {
    //None
  }

  /**
   * Turns the climber motor on to climb
   */
  public void climb() {
    climberMotor.set(1);
  }

  /**
   * Reverses the climber motor to "un-climb"
   */
  public void reverse() {
    climberMotor.set(-1);
  }

  /**
   * Stops the climber motor
   */
  public void stop() {
    climberMotor.set(0);
  }
}
