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
  public static final String POSITION_SELECTION = "Position Selection";
  public static final String SET_LIFT_SWITCH = "Set - Lift Switch";
  public static final String SET_LIFT_EXCHANGE = "Set - Lift Exchange";
  public static final String SET_LIFT_SCALE_TOP = "Set - Lift Scale Top";
  public static final String SET_LIFT_SCALE_MID = "Set - Lift Scale Mid";
  public static final String SET_LIFT_FLOOR = "Set - Lift Floor";
  public static final String SET_GRABBER_POSITION_HIGH = "Set - Grabber Position High";
  public static final String SET_GRABBER_POSITION_SHOOT = "Set - Grabber Position Shoot";
  public static final String SET_GRABBER_POSITION_EXCHANGE = "Set - Grabber Position Exchange";
  public static final String SET_GRABBER_POSITION_LOW = "Set - Grabber Position Low";
  public static final String SET_GRABBER_CLOSE = "Set - Grabber Close";
  public static final String SUBMIT = "Submit";
  public static final String STRATEGY_FIELD = "Strategy Field";
  public static final String SET_GRABBER_OPEN = "Set - Grabber Open";
  public static final int leftFrontMotorPort = 1;
  public static final int leftBackMotorPort = 2;
  public static final int rightFrontMotorPort = 3;
  public static final int rightBackMotorPort = 4;
  public static final boolean leftFrontMotorInverted = false;
  public static final boolean leftBackMotorInverted = false;
  public static final boolean rightFrontMotorInverted = false;
  public static final boolean rightBackMotorInverted = false;
  public static final boolean leftSideEncoderInverted = true;
  public static final boolean rightSideEncoderInverted = false;

  public static final int encoderPPR = 120 * 4; //Multiplied for 4x encoding

  /**
   * [0] is forward position port, [1] is backward position port
   */
  public static final int[] gearBox = {1, 0};

  public static final int[] grabberPorts = {3, 2};
  public static final int grabberMotorPort = 7;
  public static final boolean grabberMotorInverted = true;

  public static final int compressor = 6;

  public static final int liftMotorPort = 5;
  public static final boolean liftMotorInverted = true;
  public static final boolean liftMotorSensorInverted = true;

  public static final int climberMotorPort = 9;
  
  public static final int driverControllerPort = 0;
  public static final int liftControllerPort = 1;
}
