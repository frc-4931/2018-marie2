package org.usfirst.frc.team4931.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.commands.autonomous.DriveForMilliseconds;
import org.usfirst.frc.team4931.robot.subsystems.Drivetrain;
import org.usfirst.frc.team4931.robot.subsystems.Grabber;

public class Robot extends TimedRobot {

  private static Grabber grabber;
  private static Drivetrain drivetrain;
  private static Compressor compressor;

  private static OperatorInput operatorInput;

  private static Command autonomousCommand;

  @Override
  public void robotInit() {
    operatorInput = new OperatorInput();

    compressor = new Compressor(RobotMap.COMPRESSOR.getValue());
    compressor.setClosedLoopControl(true);
    compressor.start();

    grabber = new Grabber();
    drivetrain = new Drivetrain();

    drivetrain.shiftGear(RobotMap.defaultGear);
    grabber.changeGrabberState(RobotMap.defaultGrabberState);
    // FIXME: Zero out motor
  }

  @Override
  public void teleopInit() {
    compressor.start();

    if (autonomousCommand != null) autonomousCommand.cancel();
  }

  @Override
  public void autonomousInit() {
    compressor.start();

    autonomousCommand = new DriveForMilliseconds(5000);
    autonomousCommand.start();
  }

  @Override
  public void disabledInit() {
    compressor.stop();
  }

  @Override
  public void robotPeriodic() {
    log();
  }

  private void log() {
    drivetrain.log();
    grabber.log();
  }

  public static Grabber getGrabber() {
    return grabber;
  }

  public static Drivetrain getDrivetrain() {
    return drivetrain;
  }

  public static OperatorInput getOperatorInput() {
    return operatorInput;
  }
}
