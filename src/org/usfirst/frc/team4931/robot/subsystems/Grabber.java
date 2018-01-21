package org.usfirst.frc.team4931.robot.subsystems;

import org.usfirst.frc.team4931.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Grabber extends Subsystem {
  private DoubleSolenoid leftPneumatic;
  private DoubleSolenoid rightPneumatic; {
    
  }
  public Grabber() {
    initialization();
  }

  private void initialization() {
    leftPneumatic = new DoubleSolenoid(RobotMap.leftGrabberPorts[0], RobotMap.leftGrabberPorts[1]);
    rightPneumatic = new DoubleSolenoid(RobotMap.rightGrabberPorts[0], RobotMap.rightGrabberPorts[1]);
  }

  @Override
  protected void initDefaultCommand() {
    // TODO Auto-generated method stub

  }

  /**
   * Spins the wheels of the grabber to suck in and take control of a power cube
   */
  public void captureCube() {
    leftPneumatic.set(Value.kReverse);
    rightPneumatic.set(Value.kReverse);
  }

  /**
   * Spins the wheels of the grabber to eject the power cube
   */
  public void releaseCube() {
    leftPneumatic.set(Value.kForward);
    rightPneumatic.set(Value.kForward);
  }

}
