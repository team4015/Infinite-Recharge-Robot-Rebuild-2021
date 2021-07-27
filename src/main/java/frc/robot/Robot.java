package frc.robot;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

public class Robot
{
  public Drivetrain drivetrain;
  public Intake intake;
  public Compressor compressor;
  public Conveyor conveyor;
  public Shooter shooter;
  public Solenoid ringlight;

  public Robot()
  {
    drivetrain = new Drivetrain();
    intake = new Intake();
    compressor = new Compressor();
    conveyor = new Conveyor();
    shooter = new Shooter();
    ringlight = new Solenoid(Constants.RING_LIGHT);

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
    ringlight.set(true);;
  }
}