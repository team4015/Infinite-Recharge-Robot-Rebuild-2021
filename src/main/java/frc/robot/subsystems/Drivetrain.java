package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drivetrain extends SubsystemBase
{
  private Talon leftMotorFront;
  private Talon leftMotorRear;
  private Talon rightMotorFront;
  private Talon rightMotorRear;

  private SpeedControllerGroup leftMotors;
  private SpeedControllerGroup rightMotors;

  private DifferentialDrive chassis;

  public Drivetrain()
  {
    leftMotorFront = new Talon(Constants.LEFT_MOTOR_FRONT);
    leftMotorRear = new Talon(Constants.LEFT_MOTOR_REAR);
    rightMotorFront = new Talon(Constants.RIGHT_MOTOR_FRONT);
    rightMotorRear = new Talon(Constants.RIGHT_MOTOR_REAR);

    leftMotors = new SpeedControllerGroup(leftMotorFront, leftMotorRear);
    rightMotors = new SpeedControllerGroup(rightMotorFront, rightMotorRear);

    chassis = new DifferentialDrive(leftMotors, rightMotors);
  }

  @Override
  public void periodic()
  {
    // This method will be called once per scheduler run
  }

  public void drive(double speed, double turn)
  {
    chassis.arcadeDrive(speed, turn);
  }
}
