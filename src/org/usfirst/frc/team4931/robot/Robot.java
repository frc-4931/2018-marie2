/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4931.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.usfirst.frc.team4931.robot.commands.Autonomous;
import org.usfirst.frc.team4931.robot.commands.CloseGrabber;
import org.usfirst.frc.team4931.robot.commands.GrabberGoToPosition;
import org.usfirst.frc.team4931.robot.commands.OpenGrabber;
import org.usfirst.frc.team4931.robot.commands.SetLiftSetpoint;
import org.usfirst.frc.team4931.robot.field.FieldAnalyzer;
import org.usfirst.frc.team4931.robot.field.StartingPos;
import org.usfirst.frc.team4931.robot.subsystems.Climber;
import org.usfirst.frc.team4931.robot.subsystems.Drivetrain;
import org.usfirst.frc.team4931.robot.subsystems.FixedLiftHeight;
import org.usfirst.frc.team4931.robot.subsystems.Grabber;
import org.usfirst.frc.team4931.robot.subsystems.GrabberPosition;
import org.usfirst.frc.team4931.robot.subsystems.Lift;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

  public static OperatorInput operatorInput;
  public static Drivetrain drivetrain;
  public static Grabber grabber;
  public static Lift lift;
  public static Climber climber;
  private FieldAnalyzer fieldAnalyzer;
  public static Compressor compressor;
  public static Relay compressorController;
  public static boolean runCompressor ;
  SendableChooser<String> autoChooserPos = new SendableChooser<>();
  private Autonomous autonomousCommand;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    drivetrain = new Drivetrain();
    fieldAnalyzer = new FieldAnalyzer();

    compressor = new Compressor(RobotMap.compressor);
    compressor.setClosedLoopControl(true); //TODO set this to true for PCM control when a new spark get's added change to false
    compressor.start();
    runCompressor = true;
    compressorController = new Relay(0);

    grabber = new Grabber();
    lift = new Lift();
    climber = new Climber();

    operatorInput = new OperatorInput();

    CameraServer.getInstance().startAutomaticCapture();

    SmartDashboard.putString("Strategy Field", "nnnnn");
    SmartDashboard.putBoolean("Submit", false);

    // Create position selector to the SmartDashboard
    for (StartingPos pos : StartingPos.values()) {
        autoChooserPos.addObject("Position " + (pos.ordinal() + 1), pos.name());
    }
    SmartDashboard.putData("Position Selection", autoChooserPos);

    grabber.goToSetPoint(GrabberPosition.HIGH);


    //Create testing commands
    SmartDashboard.putData("Set - Grabber Open", new OpenGrabber());
    SmartDashboard.putData("Set - Grabber Close", new CloseGrabber());
    SmartDashboard.putData("Set - Grabber Position Low", new GrabberGoToPosition(GrabberPosition.LOW));
    SmartDashboard.putData("Set - Grabber Position Exchange", new GrabberGoToPosition(GrabberPosition.EXCHANGE));
    SmartDashboard.putData("Set - Grabber Position Shoot", new GrabberGoToPosition(GrabberPosition.SHOOT));
    SmartDashboard.putData("Set - Grabber Position High", new GrabberGoToPosition(GrabberPosition.HIGH));
    SmartDashboard.putData("Set - Lift Floor", new SetLiftSetpoint(FixedLiftHeight.FLOOR));
    SmartDashboard.putData("Set - Lift Scale Mid", new SetLiftSetpoint(FixedLiftHeight.SCALE_MID));
    SmartDashboard.putData("Set - Lift Scale Top", new SetLiftSetpoint(FixedLiftHeight.SCALE_TOP));
    SmartDashboard.putData("Set - Lift Exchange", new SetLiftSetpoint(FixedLiftHeight.EXCHANGE));
    SmartDashboard.putData("Set - Lift Switch", new SetLiftSetpoint(FixedLiftHeight.SWITCH));
  }

  /**
   * This function is called once each time the robot enters Disabled mode. You can use it to reset
   * any subsystem information you want to clear when the robot is disabled.
   */
  @Override
  public void disabledInit() {
    runCompressor = false;
    compressorController.set(Value.kOff);
  }

  @Override
  public void disabledPeriodic() {
    SmartDashboard.updateValues();
    if (SmartDashboard.getBoolean("Submit", false)) {
      fieldAnalyzer.predetermineStrategy();
      SmartDashboard.putBoolean("Submit", false);
    }

    Scheduler.getInstance().run();
    log();
  }

  /**
   * Called once when the robot enters autonomous mode.
   */
  @Override
  public void autonomousInit() {
    runCompressor = true;

    char[] fieldPos =
        DriverStation.getInstance().getGameSpecificMessage().toLowerCase().toCharArray();
    fieldAnalyzer.setFieldPosition(fieldPos);
    fieldAnalyzer.calculateStrategy();
    autonomousCommand = new Autonomous(fieldAnalyzer.getPickedStrategy(),
        fieldAnalyzer.getPickedTrajectory());
    autonomousCommand.start();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
    log();
  }

  @Override
  public void teleopInit() {
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }

    drivetrain.switchHighGear();
    runCompressor = true;
    compressor.start();
  }


  @Override
  public void robotPeriodic() {
    if (runCompressor) {
      if (!compressor.getPressureSwitchValue()) {
        compressorController.set(Value.kForward);
      } else {
        compressorController.set(Value.kOff);
      }
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    drivetrain.autoShift();
    log();
  }

  /**
   * Test code for displaying values on Smart Dashboard
   */
  @Override
  public void testInit() {
    runCompressor = false;

    SmartDashboard.putNumber("Set - Left Speed", 0);
    SmartDashboard.putNumber("Set - Right Speed", 0);
    SmartDashboard.putNumber("Set - Lift Speed", 0);
    SmartDashboard.putNumber("Set - Grabber Rotation Speed", 0);

    SmartDashboard.putNumber("Set - Compressor State", 0);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    Scheduler.getInstance().run();

    double liftSpeed = SmartDashboard.getNumber("Set - Lift Speed", 0);
    if (liftSpeed != 0) {
      lift.setSpeed(liftSpeed);
    }

    double grabberSpeed = SmartDashboard.getNumber("Set - Grabber Rotation Speed", 0);
    if (grabberSpeed != 0) {
      grabber.setSpeed(grabberSpeed);
    }

    double leftSide = SmartDashboard.getNumber("Set - Left Speed", 0);
    double rightSide = SmartDashboard.getNumber("Set - Right Speed", 0);
    drivetrain.driveTank(leftSide, rightSide);

    int compressorState = (int)SmartDashboard.getNumber("Set - Compressor State", 0);
    if (compressorState == 1) { //Set compressor to on
      runCompressor = false;
      compressorController.set(Value.kForward);
    } else if (compressorState == -1) { //Set compressor to auto
      runCompressor = true;
    } else { //Set compressor to off
      runCompressor = false;
      compressorController.set(Value.kOff);
    }

    log();
  }
  
  private void log() {
    drivetrain.log();
    grabber.log();
    lift.log();
    
    SmartDashboard.putBoolean("Pressure Switch", compressor.getPressureSwitchValue());
    SmartDashboard.putBoolean("Compressor Enabled", compressorController.get() == Value.kForward);
  }
}
