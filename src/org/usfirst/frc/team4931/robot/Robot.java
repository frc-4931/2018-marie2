/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4931.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.usfirst.frc.team4931.robot.commands.Autonomous;
import org.usfirst.frc.team4931.robot.commands.CloseGrabber;
import org.usfirst.frc.team4931.robot.commands.GrabberChangePosition;
import org.usfirst.frc.team4931.robot.commands.OpenGrabber;
import org.usfirst.frc.team4931.robot.commands.SetLiftSetpoint;
import org.usfirst.frc.team4931.robot.field.FieldAnalyzer;
import org.usfirst.frc.team4931.robot.field.StartingPos;
import org.usfirst.frc.team4931.robot.subsystems.Drivetrain;
import org.usfirst.frc.team4931.robot.subsystems.FixedLiftHeight;
import org.usfirst.frc.team4931.robot.subsystems.Grabber;
import org.usfirst.frc.team4931.robot.subsystems.GrabberPosition;
import org.usfirst.frc.team4931.robot.subsystems.Lift;
import edu.wpi.first.wpilibj.CameraServer;
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
    compressor.setClosedLoopControl(false);
    runCompressor = true;
    compressorController = new Relay(0);

    grabber = new Grabber();
    lift = new Lift();

    operatorInput = new OperatorInput();

    CameraServer.getInstance().startAutomaticCapture();

    SmartDashboard.putBoolean("Pressure Switch", compressor.getPressureSwitchValue());
    SmartDashboard.putString("Strategy Field", "nnnnn");
    SmartDashboard.putData("Submit", new InstantCommand() {
      @Override
      protected void initialize() {
        fieldAnalyzer.predetermineStrategy();
      }
    });

    // Create position selector to the SmartDashboard
    for (StartingPos pos : StartingPos.values()) {
        autoChooserPos.addObject("Position " + (pos.ordinal() + 1), pos.name());
    }
    SmartDashboard.putData("Position Selection", autoChooserPos);

    grabber.calculateCurrentPosition();
    grabber.goToSetPoint(GrabberPosition.HIGH);
  }

  /**
   * This function is called once each time the robot enters Disabled mode. You can use it to reset
   * any subsystem information you want to clear when the robot is disabled.
   */
  @Override
  public void disabledInit() {
    compressorController.set(Value.kOff);
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString code to get the auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional commands to the chooser code above (like
   * the commented example) or additional comparisons to the switch structure below with additional
   * strings & commands.
   */
  @Override
  public void autonomousInit() {
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
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
    drivetrain.switchHighGear();
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
    grabber.calculateCurrentPositionAndMove();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();

    drivetrain.autoShift();

    SmartDashboard.putBoolean("Pressure Switch", true);
    SmartDashboard.putBoolean("Bool", operatorInput.getDriverController().getRawButton(1));
    SmartDashboard.putNumber("Encoder", drivetrain.getLeftEncoder());
    SmartDashboard.putNumber("Joy y", operatorInput.getDriverController().getY());
    SmartDashboard.putNumber("Joy z", operatorInput.getDriverController().getZ());
  }

  @Override
  public void testInit() {
    runCompressor = false;
    SmartDashboard.putData("Open Grabber", new OpenGrabber());
    SmartDashboard.putData("Close Grabber", new CloseGrabber());
    SmartDashboard.putData("Change Grabber Position Low", new GrabberChangePosition(GrabberPosition.LOW));
    SmartDashboard.putData("Change Grabber Position Mid", new GrabberChangePosition(GrabberPosition.MIDDLE));
    SmartDashboard.putData("Change Grabber Position High", new GrabberChangePosition(GrabberPosition.HIGH));
    SmartDashboard.putData("Lift Floor", new SetLiftSetpoint(FixedLiftHeight.FLOOR));
    SmartDashboard.putData("Lift Scale Mid", new SetLiftSetpoint(FixedLiftHeight.SCALE_MID));
    SmartDashboard.putData("Lift Scale Top", new SetLiftSetpoint(FixedLiftHeight.SCALE_TOP));
    SmartDashboard.putData("Lift Exchange", new SetLiftSetpoint(FixedLiftHeight.EXCHANGE));
    SmartDashboard.putData("Lift Switch", new SetLiftSetpoint(FixedLiftHeight.SWITCH));
    SmartDashboard.putNumber("Left Speed", 0);
    SmartDashboard.putNumber("Right Speed", 0);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    Scheduler.getInstance().run();

    double leftSide = SmartDashboard.getNumber("Left Speed", 0);
    double rightSide = SmartDashboard.getNumber("Right Speed", 0);
    drivetrain.driveTank(leftSide, rightSide);
    drivetrain.printSpeed();

    SmartDashboard.putNumber("Gyro Angle", drivetrain.gyroReadYawAngle());
    SmartDashboard.putNumber("Gyro Rate", drivetrain.gyroReadYawRate());
  }
  
  private void log() {
    drivetrain.log();
    grabber.log();
    lift.log();
  }
}
