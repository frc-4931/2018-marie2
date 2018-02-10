/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4931.robot;

import edu.wpi.first.wpilibj.SPI;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.

  public static final int leftFrontMotorPort = 1;
  public static final int leftBackMotorPort = 2;
  public static final int rightFrontMotorPort = 3;
  public static final int rightBackMotorPort = 4;
  public static final boolean leftFrontMotorInverted = false;
  public static final boolean leftBackMotorInverted = false;
  public static final boolean rightFrontMotorInverted = false;
  public static final boolean rightBackMotorInverted = false;
  public static final boolean rightSideEncoderInverted = false;
  public static final boolean leftSideEncoderInverted = false;

  public static final int encoderPPR = 120 * 4; //Multiplied for 4x encoding

  /**
   * [0] is forward position port, [1] is backward position port
   */
  public static final int[] gearBox = {0, 1};

  public static final int[] leftGrabberPorts = {2, 3};
  public static final int[] rightGrabberPorts = {4, 5};
  public static final int leftGrabberMotorPort = 7;
  public static final int rightGrabberMotorPort = 8;
  public static final boolean leftGrabberMotorInverted = true;
  public static final boolean rightGrabberMotorInverted = false;

  public static final int compressor = 6;

  public static final int liftMotorPort = 5;
  public static final boolean liftMotorInverted = false;

  public static final int climberMotorPort = 9;
  
  public static final int driverControllerPort = 0;
  public static final int liftControllerPort = 1;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
}
