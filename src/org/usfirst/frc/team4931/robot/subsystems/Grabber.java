package org.usfirst.frc.team4931.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4931.robot.RobotMap;
import org.usfirst.frc.team4931.robot.commands.GrabberMoveWithPOV;
/**
 * This defines the major component of the grabber subsystem.
 */
public class Grabber extends Subsystem {
  private final double NORMAL_MOVE_SPEED = 0.5;
  private boolean open;
  private DoubleSolenoid pneumatic;
  private WPI_TalonSRX grabberMotor;
  private AnalogInput distance;
  private double setPoint;
  private boolean autoGrab;
  private GrabberPosition grabberPosition;

  /**
   * Creates a new grabber. This sets up the motors and pneumatics neccecary for grabbing.
   */
  public Grabber() {
    pneumatic = new DoubleSolenoid(RobotMap.compressor, RobotMap.grabberPorts[0], RobotMap.grabberPorts[1]);
    grabberMotor = new WPI_TalonSRX(RobotMap.grabberMotorPort);
    grabberMotor.setInverted(RobotMap.grabberMotorInverted);
    grabberMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
    grabberMotor.setSelectedSensorPosition(0, 0, 0);
    grabberMotor.setNeutralMode(NeutralMode.Brake);
    grabberMotor.setSensorPhase(RobotMap.grabberEncoderInverted);
//    grabberMotor.configClosedloopRamp(0.5, 0);

    PIDF(0.6, 0.000003, 240, 0.025);
    grabberMotor.configMaxIntegralAccumulator(0, 0, 0);

    distance = new AnalogInput(1);
    autoGrab = true;
  }

  public void PIDF(double p, double i, double d, double f) {
    grabberMotor.config_kP(0, p, 0);
    grabberMotor.config_kI(0, i, 0);
    grabberMotor.config_kD(0, d, 0);
    grabberMotor.config_kF(0, f, 0);
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new GrabberMoveWithPOV());
  }

  public double getDistance() {
    return distance.getValue();
  }

  public void calculate() {
    if (autoGrab && open && getDistance() < 75) {
      captureCube();
      autoGrab = false;
    } else if (getDistance() > 180) {
      autoGrab = true;
    }
  }

  /**
   * @return whether a and b are close to or equal to each other
   */
  private boolean fuzzyEqual(double a, double b) {
    return Math.abs(a - b) < 250;
  }

  public void reset() {
    grabberMotor.setSelectedSensorPosition(0, 0, 0);
  }

  public double getSetPoint() {
    return setPoint;
  }

  /**
   * Closes the grabber to get the cube
   */
  public void captureCube() {
    pneumatic.set(Value.kReverse);
    open = false;
  }

  /**
   * Opens the grabber to release the cube
   */
  public void releaseCube() {
    pneumatic.set(Value.kForward);
    open = true;
  }

  /**
   * Sets the target position of the grabber and starts it moving towards it.
   *
   * @param position the target position in encoder counts.
   */
  public void goToSetPoint(double position) {
    setPoint = position;
    grabberMotor.set(ControlMode.Position, position);
  }

  /**
   * Sets the speed of the grabber rotation
   * @param speed the speed in of roation in +/- percent
   */
  public void setSpeed(double speed) {
    grabberMotor.set(ControlMode.PercentOutput, speed);
  }

  /**
   * Sets the target position of the grabber and starts it moving towards it.
   * @param position the target position of type GrabberPosition
   */
  public void goToSetPoint(GrabberPosition position) {
    goToSetPoint(position.position());
    grabberPosition = position;
  }

  public GrabberPosition getCurrentGrabberPosition() {
    return grabberPosition;
  }

  /**
   * @return the current grabber position.
   */
  public double getCurrentPosition() {
    return grabberMotor.getSelectedSensorPosition(0);
  }

  /**
   * @return if the grabber is at it's target position.
   */
  public boolean atTargetPosition() {
    return Math.abs(setPoint - getCurrentPosition()) < 200;
  }
  
  public void log() {
    SmartDashboard.putBoolean("GrabberOpen", open);
    SmartDashboard.putNumber("Grabber Position", grabberMotor.getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Grabber Speed", grabberMotor.get());
    SmartDashboard.putNumber("Grabber Distance", getDistance());
  }
}
