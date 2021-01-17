package frc.robot;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.Compressor;

public class Robot
{
  public Drivetrain drivetrain;
  public Intake intake;
  public Compressor compressor;
  public Conveyor conveyor;

  public Robot()
  {
    drivetrain = new Drivetrain();
    intake = new Intake();
    compressor = new Compressor();
    conveyor = new Conveyor();

    init();
  }

  private void init()
  {
    drivetrain.stop();
    intake.stop();
    intake.retract();
    conveyor.stop();
    compressor.setClosedLoopControl(false);
  }
}