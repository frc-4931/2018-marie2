package org.usfirst.frc.team4931.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.buttons.Button;
import org.usfirst.frc.team4931.robot.Robot;

/**
 * Created by jcrane on 1/17/18.
 */
public class openClose extends Command {

  Button button;

  public openClose(Button button) {
    this.button = button;
  }

  @Override
  protected void initialize() {
    super.initialize();
    Robot.foo.set(Value.kForward);
  }

  @Override
  protected void end() {
    super.end();
    Robot.foo.set(Value.kOff);
  }

  @Override
  protected boolean isFinished() {
    return !button.get();
  }

}
