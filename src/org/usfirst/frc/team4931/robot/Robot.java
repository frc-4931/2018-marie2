/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4931.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4931.robot.commands.CloseGrabber;
import org.usfirst.frc.team4931.robot.commands.openClose;
import org.usfirst.frc.team4931.robot.subsystems.Drivetrain;
import org.usfirst.frc.team4931.robot.subsystems.Grabber;
import org.usfirst.frc.team4931.robot.subsystems.Lift;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

  public static OperatorInput operatorInput;
  public static Drivetrain drivetrain;
  public static Grabber grabber;
  public static Lift lift;
  Command autonomousCommand;
  SendableChooser<Command> autoChooser = new SendableChooser<>();
  public static Compressor compressor;
  public static Relay compressorController;
  public static DoubleSolenoid foo;
  public static Button button1;
  

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    operatorInput = new OperatorInput();
    drivetrain = new Drivetrain();
    compressor = new Compressor(0);
    compressor.setClosedLoopControl(false);

    compressorController = new Relay(0);

    foo = new DoubleSolenoid(0, 1);

    button1 = new JoystickButton(operatorInput.stick, 1);
    //button2.whenPressed(new openClose(button2));
    
    SmartDashboard.putBoolean("Pressure Switch", compressor.getPressureSwitchValue());
    SmartDashboard.putBoolean("Bool", false);

    System.out.println("Bool: false");
    
    autoChooser.addObject("Go Forward", new CloseGrabber());
    SmartDashboard.putData("Auto Select", autoChooser);
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
    compressor.stop();
    compressorController.set(Value.kOff);
  }



  @Override
  public void disabledPeriodic() {
//    Scheduler.getInstance().run();
    SmartDashboard.putBoolean("Pressure Switch", true);
//  SmartDashboard.putBoolean("Bool",  operatorInput.stick.getRawButton(1));
  SmartDashboard.putBoolean("Bool",  true);
  //button1.get();
  System.out.println("Bool: True");
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    //Gets command from Smart Dashboard.
    autonomousCommand = autoChooser.getSelected();
    // schedule the autonomous command (example)
    if (autonomousCommand != null) {
      autonomousCommand.start();
    }
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



  }
  
  

  @Override
  public void robotPeriodic() {

  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();

//    if (compressor.getPressureSwitchValue())
//      compressorController.set(Value.kForward);
//    else
//      compressorController.set(Value.kOff);

    SmartDashboard.putBoolean("Pressure Switch", true);
    SmartDashboard.putBoolean("Bool",  operatorInput.stick.getRawButton(1));
//    SmartDashboard.putBoolean("Bool",  true);
    //button1.get();
    System.out.println("Bool: True");
    //drivetrain.driveArcade(0, 0);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
	  SmartDashboard.putBoolean("Bool", true);
	  SmartDashboard.putBoolean("TEST", true);
	  SmartDashboard.updateValues();
  }
}
