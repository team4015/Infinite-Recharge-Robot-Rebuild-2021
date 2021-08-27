/* ==================================================
Authour: Shane Pinto
Description: Drivetrain.java contains the functions
and objects needed to interact with the drivetrain's
CIM motors in order to controller robot movement.
================================================== */

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Talon;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase
{
  private Talon leftFrontMotor;
  private Talon leftRearMotor;
  private Talon rightFrontMotor;
  private Talon rightRearMotor;

  public Drivetrain()
  {
    // The drivetrain is controlled by 4 CIM motors set up in groups of 2 per gearbox
    leftFrontMotor = new Talon(Constants.LEFT_MOTOR_FRONT);
    leftRearMotor = new Talon(Constants.LEFT_MOTOR_REAR);
    rightFrontMotor = new Talon(Constants.RIGHT_MOTOR_FRONT);
    rightRearMotor = new Talon(Constants.RIGHT_MOTOR_REAR);
  }

  /* =====================================
  move() takes a speed value and a turn
  value as parameters to write to motors
  on both sides of the drive train. This
  allows for the robot to be controlles
  using arcade drive.
  ===================================== */
  public void move(double speed, double turn)
  {
    setLeftMotors(speed + turn);
    setRightMotors(speed - turn);
    // The + and - turn adds the steer value to the speed of each side of the drivetrain allowing the robot to turn by slowing down one side while speeding up the other
  }

  /* =====================================
  stop() sets all CIM motor speeds to 0
  to stop all movement.
  ===================================== */
  public void stop()
  {
    move(0, 0);
  }

  /* =====================================
  setLeftMotors() sets both of the lefts
  CIM motors the same speed and direction
  in order to prevent them from spinning
  in a manner which could cause damage
  to the gearbox.
  ===================================== */
  private void setLeftMotors(double speed)
  {
    leftFrontMotor.set(speed);
    leftRearMotor.set(speed);
  }

  /* =====================================
  setRightMotors() sets both of the right
  CIM motors the same speed and direction
  in order to prevent them from spinning
  in a manner which could cause damage
  to the gearbox.
  ===================================== */
  private void setRightMotors(double speed)
  {
    rightFrontMotor.set(-speed);
    rightRearMotor.set(-speed);
  }
}
