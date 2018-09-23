package org.usfirst.frc.team4931.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.commands.autonomous.DriveForMilliseconds;
import org.usfirst.frc.team4931.robot.subsystems.Drivetrain;
import org.usfirst.frc.team4931.robot.subsystems.Grabber;

public class Robot extends TimedRobot {

  private static Grabber grabber;
  private static Drivetrain drivetrain;

  private static OperatorInput operatorInput;

  private static Command autonomousCommand;

  @Override
  public void robotInit() {
    operatorInput = new OperatorInput();

    grabber = new Grabber();
    drivetrain = new Drivetrain();
  }

  @Override
  public void autonomousInit() {
    autonomousCommand = new DriveForMilliseconds(5000);
    autonomousCommand.start();
  }

  @Override
  public void teleopInit() {
    if (autonomousCommand != null) autonomousCommand.cancel();
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
