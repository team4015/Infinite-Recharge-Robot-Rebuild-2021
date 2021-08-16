package frc.robot;

import frc.robot.subsystems.*;
import frc.robot.controls.Driver;
import edu.wpi.first.wpilibj.Compressor;
import frc.robot.commands.teleop.drivetrain.*;
import frc.robot.commands.teleop.intake.*;
import frc.robot.commands.teleop.conveyor.*;
import frc.robot.commands.teleop.shooter.*;

public class Robot
{
  // SUBSYSTEMS
  public Drivetrain drivetrain;
  public Intake intake;
  public Compressor compressor;
  public Conveyor conveyor;
  public Shooter shooter;
  public Vision vision;

  // CONTROLS
  public Driver driver;

  public Robot()
  {
    drivetrain = new Drivetrain();
    intake = new Intake();
    compressor = new Compressor();
    conveyor = new Conveyor();
    shooter = new Shooter();
    vision = new Vision();

    driver = new Driver(this);

    setDefaultCommands();
    init();
  }

  private void init()
  {
    drivetrain.stop();
    intake.stop();
    intake.retract();
    conveyor.stop();
    shooter.stop();
    compressor.setClosedLoopControl(false);
    vision.stop();
  }

  private void setDefaultCommands()
  {
    drivetrain.setDefaultCommand(new Drive(this));
    intake.setDefaultCommand(new IntakeStop(this));
    conveyor.setDefaultCommand(new ConveyorStandby(this));
    shooter.setDefaultCommand(new ShooterStop(this));
  }
}