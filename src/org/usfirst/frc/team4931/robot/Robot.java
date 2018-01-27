/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4931.robot;

import org.usfirst.frc.team4931.robot.subsystems.Drivetrain;
import org.usfirst.frc.team4931.robot.subsystems.Grabber;
import org.usfirst.frc.team4931.robot.subsystems.Lift;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
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
  public static Compressor compressor;
  public static Relay compressorController;
  public static boolean runCompressor;
  SendableChooser<String> autoChooserPos = new SendableChooser<>();
  SendableChooser<String> autoChooserTarget = new SendableChooser<>();
  Command autonomousCommand;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    operatorInput = new OperatorInput();
    drivetrain = new Drivetrain();
    compressor = new Compressor(RobotMap.compressor);
    compressor.setClosedLoopControl(false);
    runCompressor = true;

    compressorController = new Relay(0);
    
    CameraServer.getInstance().startAutomaticCapture();

    SmartDashboard.putBoolean("Pressure Switch", compressor.getPressureSwitchValue());

    // Create position selector to the SmartDashboard
    autoChooserPos.addDefault("Position 1", "pos1");
    autoChooserPos.addObject("Position 2", "pos2");
    autoChooserPos.addObject("Position 3", "pos3");
    SmartDashboard.putData("Position Selection", autoChooserPos);

    // Create strategy selector
    autoChooserTarget.addDefault("Default Strategy", "def");
  }

  /**
   * This function is called once each time the robot enters Disabled mode. You can use it to reset
   * any subsystem information you want to clear when the robot is disabled.EXCHANGE: //TODO: break;
   * case SCALE_MID: //TODO break; case FLOOR: //TODO break; case SCALE_TOP: //TODO break; case
   * SWITCH:
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
    char[] fieldPos = DriverStation.getInstance().getGameSpecificMessage().toCharArray();
    String autoPos = autoChooserPos.getSelected();
    String autoTarget = autoChooserTarget.getSelected();
    String targetCommand = autoPos + "-" + autoTarget + "-" + fieldPos[0] + fieldPos[1];
    SmartDashboard.putString("Auto String", targetCommand);


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
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();

    SmartDashboard.putBoolean("Pressure Switch", true);
    SmartDashboard.putBoolean("Bool", operatorInput.stick.getRawButton(1));
    SmartDashboard.putNumber("Encoder", drivetrain.getLeftEncoder());

    drivetrain.driveArcade(operatorInput.stick.getZ(), operatorInput.stick.getY());
    SmartDashboard.putNumber("Joy y", operatorInput.stick.getY());
    SmartDashboard.putNumber("Joy z", operatorInput.stick.getZ());
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    SmartDashboard.putNumber("Gyro Angle", drivetrain.gyroReadYawAngle());
    SmartDashboard.putNumber("Gyro Rate", drivetrain.gyroReadYawRate());
  }
}
