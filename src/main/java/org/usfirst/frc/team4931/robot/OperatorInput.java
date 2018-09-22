package org.usfirst.frc.team4931.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team4931.robot.commands.ChangeGear;
import org.usfirst.frc.team4931.robot.commands.ChangeGrabberState;
import org.usfirst.frc.team4931.robot.enums.Gear;
import org.usfirst.frc.team4931.robot.enums.GrabberState;

public class OperatorInput {

  private Joystick joystick;

  public OperatorInput() {
    joystick = new Joystick(RobotMap.JOYSTICK.getValue());

    /* Grabber */
    Button grabberOpen = new JoystickButton(joystick, RobotMap.GRABBER_OPEN.getValue());
    grabberOpen.whenActive(new ChangeGrabberState(GrabberState.OPENED));

    Button grabberClose = new JoystickButton(joystick, RobotMap.GRABBER_CLOSE.getValue());
    grabberClose.whenActive(new ChangeGrabberState(GrabberState.CLOSED));

    /* Grabber Position*/

    /* Shifting */
    Button shiftToLowGear = new JoystickButton(joystick, RobotMap.SHIFT_TO_LOW_GEAR.getValue());
    shiftToLowGear.whenPressed(new ChangeGear(Gear.LOW));

    Button shiftToHighGear = new JoystickButton(joystick, RobotMap.SHIFT_TO_HIGH_GEAR.getValue());
    shiftToHighGear.whenPressed(new ChangeGear(Gear.HIGH));
  }

  public Joystick getJoystick() {
    return joystick;
  }
}
