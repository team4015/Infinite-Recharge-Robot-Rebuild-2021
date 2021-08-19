package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Victor;

public class Shooter extends SubsystemBase
{
  private Victor motor;
  private double shooterSpeed;

  public Shooter()
  {
    motor = new Victor(Constants.SHOOTER_VICTOR);
    shooterSpeed = 0;
  }

  public void spin()
  {
    System.out.println(shooterSpeed);
    if (shooterSpeed > 1)
    {
      shooterSpeed = 1;
    }

    motor.set(-shooterSpeed);
  }

  public void stop()
  {
    motor.set(0);
  }

  public void setShooterSpeed(double shooterSpeed)
  {
    this.shooterSpeed = shooterSpeed;
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
