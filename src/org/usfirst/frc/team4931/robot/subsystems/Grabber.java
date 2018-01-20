package org.usfirst.frc.team4931.robot.subsystems;

import org.usfirst.frc.team4931.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Grabber extends Subsystem {
  private DoubleSolenoid pneumatic;

  public Grabber() {
    initialization();
  }

  private void initialization() {
    pneumatic = new DoubleSolenoid(RobotMap.grabberPorts[0], RobotMap.grabberPorts[1]);
  }

  @Override
  protected void initDefaultCommand() {
    // TODO Auto-generated method stub

  }

  /**
   * Spins the wheels of the grabber to suck in and take control of a power cube
   */
  public void captureCube() {
    pneumatic.set(Value.kReverse);
  }

  /**
   * Spins the wheels of the grabber to eject the power cube
   */
  public void releaseCube() {
    pneumatic.set(Value.kForward);
  }

}
