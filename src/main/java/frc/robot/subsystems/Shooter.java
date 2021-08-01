package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Victor;

public class Shooter extends SubsystemBase
{
  private Victor motor;

  public Shooter()
  {
    motor = new Victor(Constants.SHOOTER_VICTOR);
  }

  public void spin()
  {
    motor.set(-Constants.SHOOTER_SPEED);
  }

  public void stop()
  {
      motor.set(0);
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
