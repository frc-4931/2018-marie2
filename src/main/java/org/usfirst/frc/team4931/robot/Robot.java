package org.usfirst.frc.team4931.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4931.robot.commands.ResetGrabber;
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
    grabber = new Grabber();
    drivetrain = new Drivetrain();

    operatorInput = new OperatorInput();

    compressor = new Compressor(RobotMap.COMPRESSOR.getValue());
    compressor.setClosedLoopControl(true);
    compressor.start();

    drivetrain.shiftGear(RobotMap.defaultGear);
    grabber.changeGrabberState(RobotMap.defaultGrabberState);
    // FIXME: Zero out motor

    SmartDashboard.putNumber("Grabber Pro", 0.5);
    SmartDashboard.putNumber("Grabber I", 0.000003);
    SmartDashboard.putNumber("Grabber D", 240);
    SmartDashboard.putNumber("Grabber F", 0.025);

    SmartDashboard.putNumber("Grabber Pos", 0);
  }

  @Override
  public void teleopInit() {
    compressor.start();

    if (autonomousCommand != null) autonomousCommand.cancel();

    //new ResetGrabber().start();
  }

  @Override
  public void autonomousInit() {
    compressor.start();

    autonomousCommand = new DriveForMilliseconds(1500);
    autonomousCommand.start(); //FIXME ENABLE THIS
    new ResetGrabber().start();
  }

  @Override
  public void disabledInit() {
    compressor.stop();
  }

  @Override
  public void robotPeriodic() {
    Scheduler.getInstance().run();
    grabber.checkLimitSwitches();

    double p, i, d, f;
    p = SmartDashboard.getNumber("Grabber Pro", 0);
    i = SmartDashboard.getNumber("Grabber I", 0);
    d = SmartDashboard.getNumber("Grabber D", 0);
    f = SmartDashboard.getNumber("Grabber F", 0);
    grabber.pidf(p, i, d, f);

    log();
  }

  private void log() {
    drivetrain.log();
    grabber.log();
  }

  @Override
  public void testPeriodic() {
    grabber.changeGrabberPosition((int) SmartDashboard.getNumber("Grabber Pos", 0));
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
