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
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OperatorInput {

  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.   
  // You create one by telling it which joystick it's on and which button
  // number it is.
  Joystick stick = new Joystick(0);
  Button button = new JoystickButton(stick, 1);
  Button shiftHighGear = new JoystickButton(stick, 2);
  Button shiftLowGear = new JoystickButton(stick, 3);
  {
    shiftHighGear.whenPressed(new Command() {
      @Override
      protected void initialize() {
        Robot.drivetrain.switchHighGear();
      }

      @Override
      protected boolean isFinished() {
        return true;
      }
    });
    shiftLowGear.whenPressed(new Command() {
      @Override
      protected void initialize() {
        Robot.drivetrain.switchLowGear();
      }

      @Override
      protected boolean isFinished() {
        return true;
      }
    });
  }

}
