// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase
{
  private Talon leftFrontMotor;
  private Talon leftRearMotor;
  private Talon rightFrontMotor;
  private Talon rightRearMotor;

  private SpeedControllerGroup leftMotors;
  private SpeedControllerGroup rightMotors;

  private DifferentialDrive chassis;

  public Drivetrain()
  {
    leftFrontMotor = new Talon(Constants.LEFT_MOTOR_FRONT);
    leftRearMotor = new Talon(Constants.LEFT_MOTOR_REAR);
    rightFrontMotor = new Talon(Constants.RIGHT_MOTOR_FRONT);
    rightRearMotor = new Talon(Constants.RIGHT_MOTOR_REAR);

    leftMotors = new SpeedControllerGroup(leftFrontMotor, leftRearMotor);
    rightMotors = new SpeedControllerGroup(rightFrontMotor, rightRearMotor);

    chassis = new DifferentialDrive(leftMotors, rightMotors);
  }

  public void move(double speed, double turn)
  {
    chassis.arcadeDrive(speed, turn);
  }

  public void stop()
  {
    move(0, 0);
  }

  @Override
  public void periodic()
  {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
