package frc.robot;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.Compressor;

public class Robot
{
  public Drivetrain drivetrain;
  public Intake intake;
  public Compressor compressor;
  public Conveyor conveyor;
  public Shooter shooter;
  public Vision vision;

  public Robot()
  {
    drivetrain = new Drivetrain();
    intake = new Intake();
    compressor = new Compressor();
    conveyor = new Conveyor();
    shooter = new Shooter();
    vision = new Vision();

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
}