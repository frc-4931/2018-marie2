package org.usfirst.frc.team4931.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.commands.autonomous.DriveForMilliseconds;
import org.usfirst.frc.team4931.robot.enums.Gear;
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
  }

  @Override
  public void teleopInit() {
    compressor.start();
    drivetrain.shiftGear(Gear.HIGH);

    if (autonomousCommand != null) autonomousCommand.cancel();
  }

  @Override
  public void autonomousInit() {
    drivetrain.shiftGear(Gear.HIGH);

    autonomousCommand = new DriveForMilliseconds(5000);
    autonomousCommand.start();
  }

  @Override
  public void disabledInit() {
    compressor.stop();
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
