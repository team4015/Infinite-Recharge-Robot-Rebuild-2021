package frc.robot;

import frc.robot.subsystems.*;
import frc.robot.controls.Driver;
import edu.wpi.first.wpilibj.Compressor;

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
    vision.stop(false);
  }
}