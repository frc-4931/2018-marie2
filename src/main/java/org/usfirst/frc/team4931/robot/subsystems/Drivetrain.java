package org.usfirst.frc.team4931.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import java.util.HashMap;
import org.usfirst.frc.team4931.robot.RobotMap;
import org.usfirst.frc.team4931.robot.commands.DriveWithJoyStick;
import org.usfirst.frc.team4931.robot.enums.Gear;

public class Drivetrain extends Subsystem {

  private HashMap<RobotMap, WPI_TalonSRX> motorMap = new HashMap<>();
  private SpeedControllerGroup speedControllerGroupLeft, speedControllerGroupRight;
  private DifferentialDrive differentialDrive;

  private DoubleSolenoid gearBox;

  public Drivetrain() {
    WPI_TalonSRX frontLeft = new WPI_TalonSRX(RobotMap.MOTOR_DT_FRONT_LEFT.getValue());
    WPI_TalonSRX frontRight = new WPI_TalonSRX(RobotMap.MOTOR_DT_FRONT_RIGHT.getValue());
    WPI_TalonSRX backLeft = new WPI_TalonSRX(RobotMap.MOTOR_DT_BACK_LEFT.getValue());
    WPI_TalonSRX backRight = new WPI_TalonSRX(RobotMap.MOTOR_DT_BACK_RIGHT.getValue());

    speedControllerGroupLeft = new SpeedControllerGroup(frontLeft, backLeft);
    speedControllerGroupRight = new SpeedControllerGroup(frontRight, backRight);

    motorMap.put(RobotMap.MOTOR_DT_FRONT_LEFT, frontLeft);
    motorMap.put(RobotMap.MOTOR_DT_FRONT_RIGHT, frontRight);
    motorMap.put(RobotMap.MOTOR_DT_BACK_LEFT, backLeft);
    motorMap.put(RobotMap.MOTOR_DT_BACK_RIGHT, backRight);

    motorMap.values().forEach((motor) -> motor.setNeutralMode(NeutralMode.Brake));

    differentialDrive = new DifferentialDrive(speedControllerGroupLeft, speedControllerGroupRight);

    gearBox =
        new DoubleSolenoid(
            RobotMap.COMPRESSOR.getValue(),
            RobotMap.GEARBOX_1.getValue(),
            RobotMap.GEARBOX_2.getValue());
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new DriveWithJoyStick());
  }

  public void shiftGear(Gear gear) {
    switch (gear) {
      case LOW:
        gearBox.set(Value.kReverse);
        break;
      case HIGH:
        gearBox.set(Value.kForward);
        break;
    }
  }

  public void arcadeDrive(double speed, double turn, double multiplier) {
    differentialDrive.arcadeDrive(
        Math.copySign(speed * speed, speed) * multiplier,
        Math.copySign(turn * turn, turn) * multiplier,
        false);
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    differentialDrive.tankDrive(leftSpeed, rightSpeed);
  }
}
