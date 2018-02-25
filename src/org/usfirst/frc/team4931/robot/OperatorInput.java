/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4931.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc.team4931.robot.commands.CloseGrabber;
import org.usfirst.frc.team4931.robot.commands.GrabberGoToPosition;
import org.usfirst.frc.team4931.robot.commands.OpenGrabber;
import org.usfirst.frc.team4931.robot.commands.SetLiftSetpoint;
import org.usfirst.frc.team4931.robot.commands.StartClimb;
import org.usfirst.frc.team4931.robot.commands.StopClimb;
import org.usfirst.frc.team4931.robot.subsystems.FixedLiftHeight;
import org.usfirst.frc.team4931.robot.subsystems.GrabberPosition;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OperatorInput {

  private Joystick driverController;
  private Joystick liftController;


  public OperatorInput() {
    driverController = tankDriveController();
    liftController = liftController();
  }

  private Joystick tankDriveController() {
    Joystick controller = new Joystick (RobotMap.driverControllerPort);
    Button shiftLowGear = new JoystickButton(controller, 1);
    Button shiftHighGear = new JoystickButton(controller, 2);
    Button climber = new JoystickButton(controller, 3);

    shiftHighGear.whenPressed(new InstantCommand() {
      @Override
      protected void initialize() {
        Robot.drivetrain.switchHighGear();
      }
    });
    shiftLowGear.whenPressed(new InstantCommand() {
      @Override
      protected void initialize() {
        Robot.drivetrain.switchLowGear();
      }
    });
    climber.whenPressed(new StartClimb());
    climber.whenReleased(new StopClimb());
    return controller;
  }

  private Joystick liftController() {
    Joystick controller = new Joystick(RobotMap.liftControllerPort);
    // talk to drive team about button arrangement
    Button openGrabber = new JoystickButton(controller, 2); // thumb button
    Button closeGrabber = new JoystickButton(controller, 1); // trigger
    Button grabberExchange = new JoystickButton(controller, 4); // bottom right button 
    Button grabberLow = new JoystickButton(controller, 3); // bottom left button
    Button grabberHigh = new JoystickButton(controller, 6); // top right button
    Button grabberShoot = new JoystickButton(controller, 5); // bottom right button
    Button scaleHigh = new JoystickButton(controller, 11);
    Button scaleMid = new JoystickButton(controller, 10);
    Button floor = new JoystickButton(controller, 7);
    Button switchHeight = new JoystickButton(controller, 9);
    Button exchange = new JoystickButton(controller, 8);

    openGrabber.whenPressed(new OpenGrabber());
    closeGrabber.whenPressed(new CloseGrabber());
    grabberExchange.whenPressed(new GrabberGoToPosition(GrabberPosition.EXCHANGE));
    grabberShoot.whenPressed(new GrabberGoToPosition(GrabberPosition.SHOOT));
    grabberLow.whenPressed(new GrabberGoToPosition(GrabberPosition.LOW));
    grabberHigh.whenPressed(new GrabberGoToPosition(GrabberPosition.HIGH));
    scaleHigh.whenPressed(new SetLiftSetpoint(FixedLiftHeight.SCALE_TOP));
    scaleMid.whenPressed(new SetLiftSetpoint(FixedLiftHeight.SCALE_MID));
    floor.whenPressed(new SetLiftSetpoint(FixedLiftHeight.FLOOR));
    switchHeight.whenPressed(new SetLiftSetpoint(FixedLiftHeight.SWITCH));
    exchange.whenPressed(new SetLiftSetpoint(FixedLiftHeight.EXCHANGE));
    return controller;
  }

  public Joystick getDriverController() {
    return driverController;
  }

  public Joystick getLiftController() {
    return liftController;
  }

}
