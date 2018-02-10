package org.usfirst.frc.team4931.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4931.robot.RobotMap;

public class Climber extends Subsystem {
  private VictorSP climberMotor;

  public Climber() {
    climberMotor = new VictorSP(RobotMap.climberMotorPort); //TODO might not be a victor
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
