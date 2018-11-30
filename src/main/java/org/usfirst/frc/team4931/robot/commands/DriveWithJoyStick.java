package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4931.robot.Robot;
import org.usfirst.frc.team4931.robot.subsystems.Drivetrain;

public class DriveWithJoyStick extends Command {

  private Joystick joystick;
  private Drivetrain drivetrain;
  private Button holdMax;

  public DriveWithJoyStick() {
    requires(Robot.getDrivetrain());
  }

  @Override
  public void initialize() {
    joystick = Robot.getOperatorInput().getJoystick();
    drivetrain = Robot.getDrivetrain();
    holdMax = new JoystickButton(joystick, 8);
  }

  @Override
  protected void execute() {
    drivetrain.arcadeDrive(-joystick.getX(), holdMax.get() ? joystick.getY() : joystick.getY() / 2, 1);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    drivetrain.arcadeDrive(0, 0, 0);
  }
}
