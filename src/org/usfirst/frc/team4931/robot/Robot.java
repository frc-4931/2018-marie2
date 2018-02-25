/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4931.robot;

import static org.usfirst.frc.team4931.robot.RobotMap.POSITION_SELECTION;
import static org.usfirst.frc.team4931.robot.RobotMap.SET_GRABBER_CLOSE;
import static org.usfirst.frc.team4931.robot.RobotMap.SET_GRABBER_OPEN;
import static org.usfirst.frc.team4931.robot.RobotMap.SET_GRABBER_POSITION_EXCHANGE;
import static org.usfirst.frc.team4931.robot.RobotMap.SET_GRABBER_POSITION_HIGH;
import static org.usfirst.frc.team4931.robot.RobotMap.SET_GRABBER_POSITION_LOW;
import static org.usfirst.frc.team4931.robot.RobotMap.SET_GRABBER_POSITION_SHOOT;
import static org.usfirst.frc.team4931.robot.RobotMap.SET_LIFT_EXCHANGE;
import static org.usfirst.frc.team4931.robot.RobotMap.SET_LIFT_FLOOR;
import static org.usfirst.frc.team4931.robot.RobotMap.SET_LIFT_SCALE_MID;
import static org.usfirst.frc.team4931.robot.RobotMap.SET_LIFT_SCALE_TOP;
import static org.usfirst.frc.team4931.robot.RobotMap.SET_LIFT_SWITCH;
import static org.usfirst.frc.team4931.robot.RobotMap.STRATEGY_FIELD;
import static org.usfirst.frc.team4931.robot.RobotMap.SUBMIT;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
  public static SendableChooser<String> autoChooserPos = new SendableChooser<>();
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
    compressor.setClosedLoopControl(true);
    compressor.start();

    grabber = new Grabber();
    lift = new Lift();
    climber = new Climber();

    operatorInput = new OperatorInput();

    CameraServer.getInstance().startAutomaticCapture();

    SmartDashboard.putString(STRATEGY_FIELD, "nnnnn");
    SmartDashboard.putBoolean(SUBMIT, false);

    // Create position selector to the SmartDashboard
    for (StartingPos pos : StartingPos.values()) {
        autoChooserPos.addObject(pos.name(), pos.name());
    }
    SmartDashboard.putData(POSITION_SELECTION, autoChooserPos);

    grabber.goToSetPoint(GrabberPosition.HIGH);


    //Create testing commands
    SmartDashboard.putData(SET_GRABBER_OPEN, new OpenGrabber());
    SmartDashboard.putData(SET_GRABBER_CLOSE, new CloseGrabber());
    SmartDashboard.putData(SET_GRABBER_POSITION_LOW, new GrabberGoToPosition(GrabberPosition.LOW));
    SmartDashboard.putData(SET_GRABBER_POSITION_EXCHANGE, new GrabberGoToPosition(GrabberPosition.EXCHANGE));
    SmartDashboard.putData(SET_GRABBER_POSITION_SHOOT, new GrabberGoToPosition(GrabberPosition.SHOOT));
    SmartDashboard.putData(SET_GRABBER_POSITION_HIGH, new GrabberGoToPosition(GrabberPosition.HIGH));
    SmartDashboard.putData(SET_LIFT_FLOOR, new SetLiftSetpoint(FixedLiftHeight.FLOOR));
    SmartDashboard.putData(SET_LIFT_SCALE_MID, new SetLiftSetpoint(FixedLiftHeight.SCALE_MID));
    SmartDashboard.putData(SET_LIFT_SCALE_TOP, new SetLiftSetpoint(FixedLiftHeight.SCALE_TOP));
    SmartDashboard.putData(SET_LIFT_EXCHANGE, new SetLiftSetpoint(FixedLiftHeight.EXCHANGE));
    SmartDashboard.putData(SET_LIFT_SWITCH, new SetLiftSetpoint(FixedLiftHeight.SWITCH));
  }

  /**
   * This function is called once each time the robot enters Disabled mode. You can use it to reset
   * any subsystem information you want to clear when the robot is disabled.
   */
  @Override
  public void disabledInit() {
    compressor.stop();
  }

  @Override
  public void disabledPeriodic() {
    if (SmartDashboard.getBoolean(SUBMIT, false)) {
      fieldAnalyzer.predetermineStrategy();
      SmartDashboard.putBoolean(SUBMIT, false);
    }

    Scheduler.getInstance().run();
    log();
  }

  /**
   * Called once when the robot enters autonomous mode.
   */
  @Override
  public void autonomousInit() {
    compressor.start();

    char[] fieldPos =
        DriverStation.getInstance().getGameSpecificMessage().toLowerCase().toCharArray();
    fieldAnalyzer.setFieldPosition(fieldPos);
    fieldAnalyzer.calculateStrategy();
    fieldAnalyzer.runAuto();

    SmartDashboard.putString("Autonomous Strategy", fieldAnalyzer.getPickedStrategy().name());
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
    compressor.start();
  }


  @Override
  public void robotPeriodic() {
    lift.checkLimitSwitchs();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    //drivetrain.autoShift();
    log();
  }

  /**
   * Test code for displaying values on Smart Dashboard
   */
  @Override
  public void testInit() {
    SmartDashboard.putNumber("Set - Left Speed", 0);
    SmartDashboard.putNumber("Set - Right Speed", 0);
    SmartDashboard.putNumber("Set - Lift Speed", 0);
    SmartDashboard.putNumber("Set - Climber Speed", 0);
    SmartDashboard.putNumber("Set - Grabber Rotation Speed", 0);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    Scheduler.getInstance().run();

    double liftSpeed = SmartDashboard.getNumber("Set - Lift Speed", 0);
    lift.setSpeed(liftSpeed);

    double grabberSpeed = SmartDashboard.getNumber("Set - Grabber Rotation Speed", 0);
    grabber.setSpeed(grabberSpeed);

    double climberSpeed = SmartDashboard.getNumber("Set - Climber Speed", 0);
    if (climberSpeed > 0) {
      climber.climb();
    } else if (climberSpeed < 0) {
      climber.reverse();
    } else {
      climber.stop();
    }

    double leftSide = SmartDashboard.getNumber("Set - Left Speed", 0);
    double rightSide = SmartDashboard.getNumber("Set - Right Speed", 0);
    drivetrain.driveTank(leftSide, rightSide);

    log();
  }
  
  private void log() {
    drivetrain.log();
    grabber.log();
    lift.log();
    
    SmartDashboard.putBoolean("Pressure Switch", compressor.getPressureSwitchValue());
  }
}
