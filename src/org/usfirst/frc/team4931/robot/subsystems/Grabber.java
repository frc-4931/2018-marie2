package org.usfirst.frc.team4931.robot.subsystems;

import org.usfirst.frc.team4931.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
/**
 * Includes everything the grabber is with
 *
 */
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
   * Closes the grabber to get the cube
   */
  public void captureCube() {
    leftPneumatic.set(Value.kReverse);
    rightPneumatic.set(Value.kReverse);
  }

  /**
   * Opens the grabber to release the cube
   */
  public void releaseCube() {
    leftPneumatic.set(Value.kForward);
    rightPneumatic.set(Value.kForward);
  }

}
