package org.usfirst.frc.team4931.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team4931.robot.commands.ChangeGear;
import org.usfirst.frc.team4931.robot.commands.ChangeGrabberPosition;
import org.usfirst.frc.team4931.robot.commands.ChangeGrabberState;
import org.usfirst.frc.team4931.robot.enums.Gear;
import org.usfirst.frc.team4931.robot.enums.GrabberPosition;
import org.usfirst.frc.team4931.robot.enums.GrabberState;

public class OperatorInput {

  private Joystick joystick;

  public OperatorInput() {
    joystick = new Joystick(RobotMap.JOYSTICK.getValue());

    /* Grabber */
    Button grabberToggle = new JoystickButton(joystick, RobotMap.GRABBER_TOGGLE.getValue());
    grabberToggle.whenPressed(new ChangeGrabberState(GrabberState.TOGGLE));

    /* Grabber Position*/
    RobotMap[] grabberPositions = {
      RobotMap.GRABBER_POSITION_FORWARD_DOWN,
      RobotMap.GRABBER_POSITION_FORWARD_STRAIGHT,
      RobotMap.GRABBER_POSITION_FORWARD_SWITCH,
      RobotMap.GRABBER_POSITION_VERTICAL,
      RobotMap.GRABBER_POSITION_BACK_SWITCH,
      RobotMap.GRABBER_POSITION_BACKWARD_STRAIGHT,
      RobotMap.GRABBER_POSITION_BACKWARD_DOWN
    };
    for (RobotMap grabberPosition : grabberPositions) {
      Button button = new JoystickButton(joystick, grabberPosition.getValue());
      button.whenPressed(
          new ChangeGrabberPosition(GrabberPosition.valueOf(grabberPosition.name().substring(17))));
    }

    /* Shifting */
    Button drivetrainShiftToggle =
        new JoystickButton(joystick, RobotMap.DRIVETRAIN_SHIFT_GEAR_TOGGLE.getValue());
    drivetrainShiftToggle.whenPressed(new ChangeGear(Gear.TOGGLE));
  }

  public Joystick getJoystick() {
    return joystick;
  }
}
