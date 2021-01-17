package frc.robot;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.Compressor;

public class Robot
{
  public Drivetrain drivetrain;
  public Intake intake;
  public Compressor compressor;

  public Robot()
  {
    drivetrain = new Drivetrain();
    intake = new Intake();
    compressor = new Compressor();

    init();
  }

  private void init()
  {
    drivetrain.stop();
    intake.stop();
    compressor.setClosedLoopControl(false);
  }
}