/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4931.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

  public static final String POSITION_SELECTION = "Position Selection";
  public static final String RESET_SUBSYSTEMS_IN_TELEOP = "TeleOp Reset";
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

  public static final int leftFrontMotorPort = 3;
  public static final int leftBackMotorPort = 4;
  public static final int rightFrontMotorPort = 1;
  public static final int rightBackMotorPort = 2;
  public static final boolean leftFrontMotorInverted = false;
  public static final boolean leftBackMotorInverted = false;
  public static final boolean rightFrontMotorInverted = false;
  public static final boolean rightBackMotorInverted = false;
  public static final boolean leftSideEncoderInverted = false;
  public static final boolean rightSideEncoderInverted = true;

  public static final int encoderPPR = 120 * 4; //Multiplied for 4x encoding

  /**
   * [0] is forward position port, [1] is backward position port
   */
  public static final int[] gearBox = {1, 0};
  /**
   * [0] is forward position port, [1] is backward position port
   */
  public static final int[] grabberPorts = {2, 3};
  public static final int grabberMotorPort = 7;
  public static final boolean grabberMotorInverted = true;
  public static final boolean grabberEncoderInverted = false;
  public static final int liftTopLimitPort = 0;
  public static final int liftBottomLimitPort = 1;

  public static final int compressor = 6;

  public static final int liftMotorPort = 5;
  public static final boolean liftMotorInverted = false;
  public static final boolean liftMotorSensorInverted = true;

  public static final int climberMotorPort = 9;
  public static final boolean climberMotorInverted = true;
  /**
   * [0] is the lock port, [1] is the free port
   */
  public static final int[] climberBrake = {7, 6};
  
  public static final int driverControllerPort = 0;
  public static final int liftControllerPort = 1;

  public static final double PPS_TO_MPS = 6 * Math.PI * 10 * 0.0254 / 480;
  public static final double DELTA_TIME = 0.02;
  public static final double MAX_VELOCITY = 218 * PPS_TO_MPS; //TODO Set max speed
  public static final double MAX_ACCELERATION = 80 * PPS_TO_MPS; //TODO Set max acceleration
  public static final double MAX_JERK = 500 * PPS_TO_MPS; //TODO Set max jerk
  public static final double WHEEL_BASE = 0.635;
  public static final double WHEEL_DIAMETER = 0.1524;
  public static final double TRAJ_PROPORTIONAL = 1;
  public static final double TRAJ_INTEGRAL = 0;
  public static final double TRAJ_DERIVATIVE = 0;
  public static final double TRAJ_VELOCITY = 1/MAX_VELOCITY;
  public static final double TRAJ_ACCELERATION = 0;
  public static final double TRAJ_GYRO_CORRECTION = 0.8;

}
